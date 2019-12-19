package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.UserDao;
import cn.edu.sjtu.bpmproject.server.entity.Tag;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private static Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

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
        List<User> userList=getUsers(url);
        if(userList!=null) return  userList.get(0);
        return null;
    }

    private List<User> getUsers(String url){
        String userInfo=restTemplate.getForObject(url,String.class);
        LOGGER.info("login user result："+userInfo);
        JSONObject jsonObject = JSONObject.fromObject(userInfo);
        if(!jsonObject.has("User")){
            return null;
        }
        userInfo = jsonObject.getString("User");
        return new Gson().fromJson(userInfo, new TypeToken<List<User>>(){}.getType());
    }

    @Override
    public User getUserByID(long id) {
        String url= ResourceAPI.RMP_URL+USER+id;
        return restTemplate.getForObject(url,User.class);
    }

    @Override
    public void deleteUser(long userId) {
        String url= ResourceAPI.RMP_URL+USER+userId;
        restTemplate.delete(url);
    }

    @Override
    public void changeUserStatus(long userId, int userStatus) {
        String url= ResourceAPI.RMP_URL+USER+userId;
        User user=getUserByID(userId);
        user.setStatus(userStatus);
        restTemplate.put(url,user);
        LOGGER.info("update user status result："+user);
    }

    @Override
    public String findUserByName(String username) {
        String url= ResourceAPI.RMP_URL+USER+"?User.username=(like)"+username;
        return restTemplate.getForObject(url,String.class);
    }

    @Override
    public List<User> getUserByTime(long startTime, long endTime) {
        String url= ResourceAPI.RMP_URL+USER+"?User.addtime=(gte)"+startTime+"&User.addtime=(lt)"+endTime;
        return getUsers(url);
    }
}
