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
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import cn.edu.sjtu.bpmproject.server.util.UserUtil;
import cn.edu.sjtu.bpmproject.server.vo.ActivityAddVO;
import cn.edu.sjtu.bpmproject.server.vo.PositionVO;
import cn.edu.sjtu.bpmproject.server.vo.RecommendConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    public void deleteActivity(long activityId) {
        activityDao.deleteActivity(activityId);
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
    public Activity updateActivity(Activity activity) {
        return activityDao.updateActivity(activity);
    }

    @Override
    public Activity getActivityById(long activityId) {
        return activityDao.getActivityById(activityId);
    }

    @Override
    public List<Activity> getActivitiesByOrganizerId(long organizerId) {
        return activityDao.getActivitiesByOrganizerId(organizerId);
    }

    @Override
    public List<Activity> getHotActivities() {
        return null;
    }

    @Override
    public List<Activity> getNearbyActivities(PositionVO positionVO) {
        return null;
    }

    @Override
    public List<Activity> getFriendsActivities() {
        return null;
    }

    @Override
    public List<Activity> getRecommendActivities(RecommendConditionVO recommendConditionVO) {
        return null;
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
        updateActivity(activity);

        //更新用户签到状态
        Registeractivity registeractivity= registeractivityDao.getRegisteractivity(userId,activityId);
        if (registeractivity==null){
            throw new NotAllowedException("未报名用户不能签到！");
        }
        registeractivity.setStatus(RegisteractivityStatus.CHECKED_IN.ordinal());
        registeractivityDao.updateRegisteractivity(registeractivity);
    }

    @Override
    public void register(Activity activity, Registeractivity registeractivity) {
        activityDao.updateActivity(activity);
        registeractivityDao.register(registeractivity);
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
        Position position=new Position(0,positionVO.getLocation(),positionVO.getLongtitude(),positionVO.getLatitude(),activityId);
        return position;
    }
}
