package cn.edu.sjtu.bpmproject.server.dao;


import cn.edu.sjtu.bpmproject.server.entity.User;

import java.util.List;

public interface UserDao {
    public void addUser(User user);
    public String getUsers();
    public User getUserByUsername(String username);
    public User getUserByID(long id);
}
