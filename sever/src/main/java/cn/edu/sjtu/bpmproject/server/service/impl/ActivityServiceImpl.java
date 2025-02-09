package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.ActivityDao;
import cn.edu.sjtu.bpmproject.server.dao.ContentDao;
import cn.edu.sjtu.bpmproject.server.dao.PhotoDao;
import cn.edu.sjtu.bpmproject.server.dao.PositionDao;
import cn.edu.sjtu.bpmproject.server.dao.RegisteractivityDao;
import cn.edu.sjtu.bpmproject.server.dao.TagDao;
import cn.edu.sjtu.bpmproject.server.dao.UserDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Position;
import cn.edu.sjtu.bpmproject.server.entity.Registeractivity;
import cn.edu.sjtu.bpmproject.server.entity.Tag;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.ActivityStatus;
import cn.edu.sjtu.bpmproject.server.enums.RegisteractivityStatus;
import cn.edu.sjtu.bpmproject.server.exception.NotAllowedException;
import cn.edu.sjtu.bpmproject.server.service.ActivityService;
import cn.edu.sjtu.bpmproject.server.service.FriendshipService;
import cn.edu.sjtu.bpmproject.server.util.CFUtil;
import cn.edu.sjtu.bpmproject.server.util.CalculateUtil;
import cn.edu.sjtu.bpmproject.server.util.LocationUtil;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import cn.edu.sjtu.bpmproject.server.util.UserUtil;
import cn.edu.sjtu.bpmproject.server.vo.ActivityAddVO;
import cn.edu.sjtu.bpmproject.server.vo.ActivityDetailVO;
import cn.edu.sjtu.bpmproject.server.vo.PositionVO;
import cn.edu.sjtu.bpmproject.server.vo.RecommendConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private RegisteractivityDao registeractivityDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private CFUtil cfUtil;

    @Override
    public Activity addActivity(ActivityAddVO activityAddVO, File photoFile, File contentFile) throws IOException {
        //上传照片和富文本内容
        String photoUrl=photoDao.addPhoto(photoFile);
        String contentUrl=contentDao.addContent(contentFile);
        Activity activity=createActivity(activityAddVO,photoUrl,contentUrl);
        Activity activity1=activityDao.addActivity(activity);
        long activityId=activity1.getId();

        //保存活动标签和位置
        positionDao.addPosition(createPosition(activityAddVO.getLocation(),activityId));
        tagDao.addTags(activityAddVO.getTags(),activityId);
        return activity1;
    }

    @Override
    public Activity addActivity(ActivityAddVO activityAddVO) {
        Activity activity=createActivity(activityAddVO,activityAddVO.getPhotourl(),activityAddVO.getDescriptionurl());
        Activity activity1=activityDao.addActivity(activity);
        long activityId=activity1.getId();

        //保存活动标签和位置
        positionDao.addPosition(createPosition(activityAddVO.getLocation(),activityId));
        tagDao.addTags(activityAddVO.getTags(),activityId);
        return activity1;
    }

    @Override
    public void deleteActivity(long activityId) {

        activityDao.deleteActivity(activityId);
        List<Tag>tags=tagDao.getTagsByActivityId(activityId);
        for (Tag tag:tags){
            tagDao.deleteTags(tag.getId());
        }
        Position position=positionDao.getPositionByActivityId(activityId);
        positionDao.deletePosition(position.getId());
    }

    @Override
    public List<Activity> getCheckActivities() {
        return activityDao.getActivitiesByStatus(ActivityStatus.PENDING.ordinal());
    }

    @Override
    public void updateActivityStatus(long activityId, int activityStatus) {
        activityDao.updateActivityStatus(activityId,activityStatus);
    }

    @Override
    public List<Activity> queryActivityByKeywords(String keyword) {
        return activityDao.queryActivityByKeywords(keyword);
    }

    @Override
    public void updateActivity(long activityId,ActivityAddVO activityAddVO) {
        Activity activity=activityDao.getActivityById(activityId);
        activity=activityAddVO.transferToActivity(activity);
        activityDao.updateActivity(activity);
    }

    @Override
    public Activity getActivityById(long activityId) {
        return activityDao.getActivityById(activityId);
    }

    @Override
    public ActivityDetailVO getActivityDetailInfoById(long activityId) {
        Activity activity=activityDao.getActivityById(activityId);
        Position position=positionDao.getPositionByActivityId(activityId);
        PositionVO positionVO=new PositionVO();
        if(position!=null){
            positionVO=new PositionVO(position.getLocation(),position.getLongitude(),position.getLatitude());
        }
        List<Tag> tagList=tagDao.getTagsByActivityId(activityId);
        List<String> tags=new ArrayList<>();
        if(tagList!=null){
            for (Tag tag:tagList) {
                tags.add(tag.getName());
            }
        }
        return new ActivityDetailVO(activity,positionVO,tags);
    }

    @Override
    public List<Activity> getActivitiesByOrganizerId(long organizerId) {
        return activityDao.getActivitiesByOrganizerId(organizerId);
    }

    @Override
    public List<Activity> getHotActivities() {
        List<Activity> activityList=activityDao.getActivityByStartTime(TimeUtil.getTime());
        if(activityList==null){
            return null;
        }
        //热门活动 将活动报名人数/最大报名人数进行排序，比例越大说明越热门
        activityList.sort((activity1,activity2)->{
            double rate1 = CalculateUtil.divide(activity1.getRegisternum(),activity1.getPeoplenum());
            double rate2 = CalculateUtil.divide(activity2.getRegisternum(),activity2.getPeoplenum());
            //从大到小排序
            if(rate1>rate2) return -1;
            else if(rate1<rate2) return 1;
            return 0;
        });
        return activityList;
    }

    @Override
    public List<Activity> getNearbyActivities(PositionVO userPos) {
        List<Activity> activityList=activityDao.getActivityByStartTime(TimeUtil.getTime());
        if(activityList==null){
            return null;
        }
        //将距离从近到远进行排序
        activityList.sort((activity1,activity2)->{
            Position position1=positionDao.getPositionByActivityId(activity1.getId());
            Position position2=positionDao.getPositionByActivityId(activity2.getId());
            double distance1 = LocationUtil.getDistance(position1.getLatitude(),position1.getLongitude(),userPos.getLatitude(),userPos.getLongitude());
            double distance2 = LocationUtil.getDistance(position2.getLatitude(),position2.getLongitude(),userPos.getLatitude(),userPos.getLongitude());
            //从小到大排序
            if(distance1>distance2) return 1;
            else if(distance1<distance2) return -1;
            return 0;
        });
        return activityList;
    }

    @Override
    public List<Activity> getFriendsActivities() {
        long userId=UserUtil.getUserId();
        List<User> userList=friendshipService.getFriends(userId);
        List<Activity> activityList=new ArrayList<>();
        for (User user:userList) {
            List<Registeractivity> registeractivityList=registeractivityDao.getActivitiesByStatus(user.getId(),RegisteractivityStatus.REGISTERED.ordinal());
            if (registeractivityList==null) continue;
            registeractivityList.forEach(registeractivity -> {
                activityList.add(activityDao.getActivityById(registeractivity.getActivityid()));
            });
        }
        return activityList;
    }

    @Override
    public List<Activity> getRecommendActivities(RecommendConditionVO recommendConditionVO) {
        return cfUtil.recommend(recommendConditionVO);
    }

    @Override
    public List<Activity> getActivitiesByTag(String tagName) {
        List<Tag> tags=tagDao.getTagsByName(tagName);
        if(tags==null) return null;
        List<Activity> activityList=new ArrayList<>();
        for (Tag tag:tags) {
            Activity activity=getActivityById(tag.getActivityid());
            activityList.add(activity);
        }
        return activityList;
    }

    @Override
    public void checkin(long activityId) {
        long userId=UserUtil.getUserId();

        //更新签到人数
        Activity activity=getActivityById( activityId);
        activity.setAttendnum(activity.getAttendnum()+1);
        activity.setUpdatetime(TimeUtil.getTime());
        activityDao.updateActivity(activity);

        //更新用户签到状态
        Registeractivity registeractivity= registeractivityDao.getRegisteractivity(userId,activityId);
        if (registeractivity==null){
            throw new NotAllowedException("未报名用户不能签到！");
        }
        registeractivity.setStatus(RegisteractivityStatus.CHECKED_IN.ordinal());
        registeractivityDao.updateRegisteractivity(registeractivity);
    }

    @Override
    public boolean isCheckedin(long activityId) {
        long userId=UserUtil.getUserId();
        //判断用户签到状态
        Registeractivity registeractivity= registeractivityDao.getRegisteractivity(userId,activityId);
        if (registeractivity==null){
            throw new NotAllowedException("未报名用户不能签到！");
        }

        if(registeractivity.getStatus()==RegisteractivityStatus.CHECKED_IN.ordinal()){
            return true;
        }
        return false;
    }

    @Override
    public void register(Activity activity, Registeractivity registeractivity) {
        activityDao.updateActivity(activity);
        registeractivityDao.register(registeractivity);
        cfUtil.updateUserTags(activity.getId());
    }


    @Override
    public List<Activity> getActivitiesByUserStatus(int registerActivityStatus) {
        long userId=UserUtil.getUserId();
        List<Registeractivity> registeractivityList=registeractivityDao.getActivitiesByStatus(userId,registerActivityStatus);
        if(registeractivityList==null) return null;
        List<Activity> activityList=new ArrayList<>();
        for (Registeractivity registeractivity:registeractivityList) {
            Activity activity=activityDao.getActivityById(registeractivity.getActivityid());
            activityList.add(activity);
        }
        return activityList;
    }

    @Override
    public List<User> getCheckedInUsers(long activityId) {
        List<Registeractivity> registeractivityList=registeractivityDao.getCheckedinUsers(activityId,RegisteractivityStatus.CHECKED_IN.ordinal());
        if(registeractivityList==null) return null;
        List<User> userList=new ArrayList<>();
        for (Registeractivity registeractivity:registeractivityList) {
            User user=userDao.getUserByID(registeractivity.getUserid());
            userList.add(user);
        }
        return userList;
    }

    //判断用户是否已报名活动
    @Override
    public boolean registercheck(long activityId) {
        Registeractivity registeractivity=registeractivityDao.getRegisteractivity(UserUtil.getUserId(),activityId);
        if (registeractivity==null) {
            return false;
        }
        return true;
    }

    private Activity createActivity(ActivityAddVO activityAddVO, String photoUrl,String contentUrl){
        Activity activity=new Activity();
        activity.setName(activityAddVO.getName());
        activity.setStarttime(activityAddVO.getStarttime());
        activity.setEndtime(activityAddVO.getEndtime());
        activity.setLocation(activityAddVO.getLocation().getLocation());
        activity.setDescriptionurl(contentUrl);
        activity.setPeoplenum(activityAddVO.getPeoplenum());
        activity.setStatus(ActivityStatus.PENDING.ordinal());
        activity.setAddtime(TimeUtil.getTime());
        activity.setUpdatetime(TimeUtil.getTime());
        activity.setOrganizerid(UserUtil.getUserId());
        activity.setRegisterstarttime(activityAddVO.getRegisterstarttime());
        activity.setRegisterendtime(activityAddVO.getEndtime());
        activity.setPhotourl(photoUrl);
        return activity;
    }

    private Position createPosition(PositionVO positionVO,long activityId){
        Position position=new Position(0,positionVO.getLocation(),positionVO.getLongitude(),positionVO.getLatitude(),activityId);
        return position;
    }
}
