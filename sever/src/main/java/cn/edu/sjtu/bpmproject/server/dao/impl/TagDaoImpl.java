package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.TagDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Tag;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TagDaoImpl implements TagDao{
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String TAG="Tag/";

    @Override
    public void addTags(List<String> tagList,long activityId) {
        String url= ResourceAPI.RMP_URL+TAG;
        for (String tagName:tagList){
            Tag tag=new Tag(0,tagName,activityId);
            Tag tag1=restTemplate.postForObject(url,tag,Tag.class);
            LOGGER.info("add tag result："+tag1);
        }
    }

    @Override
    public List<Tag> getTagsByName(String name) {
        String url=ResourceAPI.RMP_URL+TAG+"?Tag.name="+name;
        String tags=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(tags);
        if(!jsonObject.has("Tag")){
            return null;
        }
        tags = jsonObject.getString("Tag");
        return JSONArray.toList(JSONArray.fromObject(tags), Tag.class);
    }
}
