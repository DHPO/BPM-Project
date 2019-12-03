package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.UserDao;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private RestTemplate restTemplate;
    public static final String USER="User/";

    @Override
    public void addUser(User user) {
        String url= ResourceAPI.RMP_URL+USER;
        LOGGER.info("register user url："+url);
        User user1=restTemplate.postForObject(url,user,User.class);
        LOGGER.info("register user result："+user1);
    }

    @Override
    public String getUsers() {
        String url= ResourceAPI.RMP_URL+USER;
        return restTemplate.getForObject(url,String.class);
    }

    @Override
    public User getUserByUsername(String username) {
        String url= ResourceAPI.RMP_URL+USER+"?User.username="+username;
        String userInfo=restTemplate.getForObject(url,String.class);
        LOGGER.info("login user result："+userInfo);
        JSONObject jsonObject = JSONObject.fromObject(userInfo);
        if(!jsonObject.has("User")){
            return null;
        }
        userInfo = jsonObject.getString("User");
        User user=((List<User>) JSONArray.toList(JSONArray.fromObject(userInfo), User.class)).get(0);
        return user;
    }
}
