package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.entity.User;

import java.util.List;

public interface FriendshipService {
    public List<User> getFriends(long userId);
    public Friendship addFriend(long userId, long friendId);
    public void deleteFriend(long friendId);

}
