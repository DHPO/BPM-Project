package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Tag;

import java.util.List;

public interface TagDao {
    public void addTags(List<String> tagList, long activityId);

    public List<Tag> getTagsByName(String name);
    public List<Tag> getTagsByActivityId( long activityId);
    public List<Tag> getTags();
    public void deleteTags(long tagId);
}
