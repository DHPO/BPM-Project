package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.User;

import java.util.List;

public interface UserService {
    public User getUserByUsername(String username);
    public void addUser(User user);
    public String getUsers();
}
