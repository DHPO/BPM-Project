package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.UserTags;

import java.util.List;

public interface UserTagsDao {
    public UserTags addUserTags(UserTags userTags);
    public void updateUserTags(UserTags userTags);
    public List<UserTags> getUserTagsByName(String name);
    public List<UserTags> getUserTagsByUserId(long userId);

    public UserTags getUserTagsByNameandUserId(String name,long userId);
    public List<UserTags> getUserTags();
}
