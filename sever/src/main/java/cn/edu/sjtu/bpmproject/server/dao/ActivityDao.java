package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Activity;

import java.util.List;

public interface ActivityDao {

    public Activity addActivity(Activity activity);

    public void deleteActivity(long activityId);

    public List<Activity> getActivitiesByStatus( int status);

    public void updateActivityStatus(long activityId, int activityStatus);

    public List<Activity> queryActivityByKeywords(String keyword);

    public Activity updateActivity( Activity activity);

    public Activity getActivityById( long activityId);

    public List<Activity> getActivitiesByOrganizerId(long organizerId);

}


