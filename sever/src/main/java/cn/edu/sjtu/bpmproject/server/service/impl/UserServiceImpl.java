package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.UserDao;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.UserRole;
import cn.edu.sjtu.bpmproject.server.enums.UserStatus;
import cn.edu.sjtu.bpmproject.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public User getUserByID(long userId) {
        return userDao.getUserByID(userId);
    }


    @Override
    public void addUser(User user) {
        if( getUserByUsername(user.getUsername())!=null){
           return;
        }
        userDao.addUser(user);
    }

    @Override
    public String getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void deleteUser(long userId) {
        userDao.deleteUser(userId);
    }

    @Override
    public void changeUserStatus(long userId, int userStatus) {
        userDao.changeUserStatus(userId,userStatus);
    }

    @Override
    public String findUserByName(String username) {
        return userDao.findUserByName(username);
    }


}
