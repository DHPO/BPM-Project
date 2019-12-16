package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.dao.CommentDao;
import cn.edu.sjtu.bpmproject.server.entity.Comment;
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
public class CommentDaoImpl implements CommentDao {

    private static Logger LOGGER = LoggerFactory.getLogger(ActivityDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String COMMENT="Comment/";

    @Override
    public Comment addComment(Comment comment) {
        String url= ResourceAPI.RMP_URL+COMMENT;
        Comment comment1=restTemplate.postForObject(url,comment,Comment.class);
        LOGGER.info("add comment result："+comment1);
        return comment1;
    }

    @Override
    public List<Comment> getComments(long activityId) {
        String url= ResourceAPI.RMP_URL+COMMENT+"?Comment.activityid="+activityId;
        List<Comment> comments=getComments(url);
        LOGGER.info("getComments : "+comments);
        return comments;
    }

    private List<Comment> getComments(String url){
        String comments=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(comments);
        if(!jsonObject.has("Comment")){
            return null;
        }
        comments = jsonObject.getString("Comment");
        return new Gson().fromJson(comments, new TypeToken<List<Comment>>(){}.getType());
    }
}
