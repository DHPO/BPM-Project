package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Registeractivity;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.vo.ActivityAddVO;
import cn.edu.sjtu.bpmproject.server.vo.PositionVO;
import cn.edu.sjtu.bpmproject.server.vo.RecommendConditionVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ActivityService {
    public Activity addActivity(ActivityAddVO activityAddVO, File photoFile, File contentFile) throws IOException;

    public void deleteActivity(long activityId);

    public List<Activity> getCheckActivities();

    public void updateActivityStatus(long activityId, int activityStatus);

    public List<Activity> queryActivityByKeywords( String keyword);

    public Activity updateActivity( Activity activity);

    public Activity getActivityById( long activityId);

    public List<Activity> getActivitiesByOrganizerId(long organizerId);

    public List<Activity> getHotActivities();

    public List<Activity> getNearbyActivities( PositionVO positionVO);

    public List<Activity> getFriendsActivities();

    public List<Activity> getRecommendActivities( RecommendConditionVO recommendConditionVO);

    public List<Activity> getActivitiesByTag( String tag);


    public void checkin( long activityId);
    public void register(Activity activity, Registeractivity registeractivity);
    public List<Activity> getActivitiesByUserStatus( int registerActivityStatus);

    public List<User> getCheckedInUsers(long activityId);

    public boolean registercheck(long activityId);

}
