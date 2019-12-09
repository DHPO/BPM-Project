package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.ActivityDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.enums.FriendshipStatus;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

}
