package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.CommentDao;
import cn.edu.sjtu.bpmproject.server.entity.Comment;
import cn.edu.sjtu.bpmproject.server.entity.PushMessage;
import cn.edu.sjtu.bpmproject.server.service.CommentService;
import cn.edu.sjtu.bpmproject.server.util.BaiduAPIUtil;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CommentServiceImpl implements CommentService{

    private AipNlp client = new AipNlp(BaiduAPIUtil.APP_ID, BaiduAPIUtil.API_KEY, BaiduAPIUtil.SECRET_KEY);

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment addComment(PushMessage pushMessage) {
        String text=pushMessage.getContent();
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();

        // 情感倾向分析
        JSONObject res = client.sentimentClassify(text, options);
        res= res.getJSONArray("items").getJSONObject(0);

        double positive_prob=(double)res.get("positive_prob");
        double negative_prob=(double)res.get("negative_prob");

        Comment comment=new Comment(0,text, TimeUtil.getTime(),pushMessage.getActivityId(),pushMessage.getUserId(),positive_prob,negative_prob);
        return commentDao.addComment(comment);
    }

    @Override
    public List<Comment> getComments(long activityId) {
        return commentDao.getComments(activityId);
    }
}
