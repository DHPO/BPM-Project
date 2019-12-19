package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Comment;

import java.util.List;

public interface CommentDao {

    public Comment addComment(Comment comment);
    public List<Comment> getComments(long activityId);
    public List<Comment> getCommentsByTime(long startTime, long endTime);
}
