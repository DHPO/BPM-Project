package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.dao.UserTagsDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.UserTags;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserTagsDaoImpl implements UserTagsDao{

    private static Logger LOGGER = LoggerFactory.getLogger(TagDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String USERTAGS="Usertags/";

    @Override
    public UserTags addUserTags(UserTags userTags) {
        String url= ResourceAPI.RMP_URL+USERTAGS;
        UserTags userTags1=restTemplate.postForObject(url,userTags,UserTags.class);
        LOGGER.info("add userTags result："+userTags1);
        return userTags1;
    }

    @Override
    public void updateUserTags(UserTags userTags) {
        String url= ResourceAPI.RMP_URL+USERTAGS+userTags.getId();
        restTemplate.put(url,userTags);
        LOGGER.info("update userTags result："+userTags);
    }

    @Override
    public List<UserTags> getUserTagsByName(String name) {
        String url= ResourceAPI.RMP_URL+USERTAGS+"?Usertags.tagname="+name;
        return getUserTags(url);
    }

    @Override
    public List<UserTags> getUserTagsByUserId(long userId) {
        String url= ResourceAPI.RMP_URL+USERTAGS+"?Usertags.userid="+userId;
        return getUserTags(url);
    }

    @Override
    public UserTags getUserTagsByNameandUserId(String name, long userId) {
        String url= ResourceAPI.RMP_URL+USERTAGS+"?Usertags.userid="+userId+"&Usertags.tagname="+name;
        List<UserTags> userTags= getUserTags(url);
        if (userTags!=null){
            return userTags.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<UserTags> getUserTags() {
        String url= ResourceAPI.RMP_URL+USERTAGS;
        return getUserTags(url);
    }

    private List<UserTags> getUserTags(String url){
        String userTags=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(userTags);
        if(!jsonObject.has("Usertags")){
            return null;
        }
        userTags = jsonObject.getString("Usertags");
        return new Gson().fromJson(userTags, new TypeToken<List<UserTags>>(){}.getType());
    }
}
