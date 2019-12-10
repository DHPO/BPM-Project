package cn.edu.sjtu.bpmproject.server.dao;

import java.util.List;

public interface TagDao {
    public void addTags(List<String> tagList, long activityId);
}
