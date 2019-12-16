package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.Comment;
import cn.edu.sjtu.bpmproject.server.entity.PushMessage;

import java.util.List;

public interface CommentService {
    public Comment addComment(PushMessage pushMessage);
    public List<Comment> getComments(long activityId);
}
