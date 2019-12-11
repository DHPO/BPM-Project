package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.User;

import java.util.List;

public interface UserService {
    public User getUserByUsername(String username);
    public User getUserByID(long userId);
    public void addUser(User user);
    public String getUsers();
    public void deleteUser(long userId);
    public void changeUserStatus(long userId,int userStatus);
    public String findUserByName(String username);


}
