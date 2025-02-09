package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.TagDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import cn.edu.sjtu.bpmproject.server.entity.Tag;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private static Logger LOGGER = LoggerFactory.getLogger(TagDaoImpl.class);

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
        return getTags(url);
    }

    @Override
    public List<Tag> getTagsByActivityId(long activityId) {
        String url=ResourceAPI.RMP_URL+TAG+"?Tag.activityid="+activityId;
        return getTags(url);
    }

    @Override
    public List<Tag> getTags() {
        String url=ResourceAPI.RMP_URL+TAG;
        return getTags(url);
    }

    @Override
    public void deleteTags(long tagId) {
        String url= ResourceAPI.RMP_URL+TAG+tagId;
        restTemplate.delete(url);
    }

    private List<Tag> getTags(String url){
        String tags=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(tags);
        if(!jsonObject.has("Tag")){
            return null;
        }
        tags = jsonObject.getString("Tag");
        return new Gson().fromJson(tags, new TypeToken<List<Tag>>(){}.getType());
    }
}
