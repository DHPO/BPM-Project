package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.ActivityDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.enums.FriendshipStatus;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ActivityDaoImpl  implements ActivityDao{
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String ACTIVITY="Activity/";

    @Override
    public Activity addActivity(Activity activity) {
        String url= ResourceAPI.RMP_URL+ACTIVITY;
        Activity activity1=restTemplate.postForObject(url,activity,Activity.class);
        LOGGER.info("add activity result："+activity1);
        return activity1;
    }

    @Override
    public void deleteActivity(long activityId) {
        String url= ResourceAPI.RMP_URL+ACTIVITY+activityId;
        restTemplate.delete(url);
    }

    @Override
    public List<Activity> getActivitiesByStatus(int status) {
        String url= ResourceAPI.RMP_URL+ACTIVITY+"?Activity.status="+status;
        List<Activity> activities=getActivities(url);
        LOGGER.info("getActivitiesByStatus : "+activities);
        return activities;
    }

    @Override
    public void updateActivityStatus(long activityId, int activityStatus) {
        Activity activity=getActivityById(activityId);
        activity.setStatus(activityStatus);
        String url= ResourceAPI.RMP_URL+ACTIVITY+activityId;
        restTemplate.put(url,activity);
        LOGGER.info("update activity status result："+activity);
    }

    @Override
    public List<Activity> queryActivityByKeywords(String keyword) {
        String url= ResourceAPI.RMP_URL+ACTIVITY+"?Activity.name=(like)"+keyword;
        return getActivities(url);
    }

    @Override
    public Activity updateActivity(Activity activity) {
        String url=ResourceAPI.RMP_URL+ACTIVITY+activity.getId();
        restTemplate.put(url,activity);
        return activity;
    }

    @Override
    public Activity getActivityById(long activityId) {
        String url=ResourceAPI.RMP_URL+ACTIVITY+activityId;
        Activity activity=restTemplate.getForObject(url,Activity.class);
        return activity;
    }

    @Override
    public List<Activity> getActivitiesByOrganizerId(long organizerId) {
        String url= ResourceAPI.RMP_URL+ACTIVITY+"?Activity.organizerid="+organizerId;
        return getActivities(url);
    }



    private List<Activity> getActivities(String url){
        String activities=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(activities);
        if(!jsonObject.has("Activity")){
            return null;
        }
        activities = jsonObject.getString("Activity");
        return JSONArray.toList(JSONArray.fromObject(activities), Activity.class);
    }
}
