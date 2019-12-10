package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.ActivityDao;
import cn.edu.sjtu.bpmproject.server.dao.ContentDao;
import cn.edu.sjtu.bpmproject.server.dao.PhotoDao;
import cn.edu.sjtu.bpmproject.server.dao.PositionDao;
import cn.edu.sjtu.bpmproject.server.dao.TagDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Position;
import cn.edu.sjtu.bpmproject.server.enums.ActivityStatus;
import cn.edu.sjtu.bpmproject.server.service.ActivityService;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import cn.edu.sjtu.bpmproject.server.util.UserUtil;
import cn.edu.sjtu.bpmproject.server.vo.ActivityAddVO;
import cn.edu.sjtu.bpmproject.server.vo.PositionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

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
