package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.FriendshipDao;
import cn.edu.sjtu.bpmproject.server.dao.UserDao;
import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.service.FriendshipService;
import cn.edu.sjtu.bpmproject.server.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipDao friendshipDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getFriends(long userId) {
        List<Friendship> friendships=friendshipDao.getFriends(userId);
        if (friendships==null) return null;
        List<User> userList=new ArrayList<>();
        for(Friendship friendship:friendships){
            long friendId=0;
            if(friendship.getUser1id()==userId){
                friendId=friendship.getUser2id();
            }else{
                friendId=friendship.getUser1id();
            }
            userList.add(userDao.getUserByID(friendId));
        }
        return userList;
    }

    @Override
    public Friendship addFriend(long userId, long friendId) {
        Friendship friendship=friendshipDao.hasFriend(userId,friendId);
        if(friendship!=null)
            return friendship;
        friendship=friendshipDao.hasFriend(friendId,userId);
        if(friendship!=null)
            return friendship;
        return friendshipDao.addFriend(userId,friendId);
    }

    @Override
    public void deleteFriend(long friendId) {
        long userId= UserUtil.getUserId();
        Friendship friendship=friendshipDao.hasFriend(userId,friendId);
        if(friendship==null)
            friendship=friendshipDao.hasFriend(friendId,userId);
        friendshipDao.deleteFriend(friendship.getId());
    }
}
