package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.entity.User;

import java.util.List;

public interface FriendshipDao {
    public List<Friendship> getFriends(long userId);

    public Friendship addFriend(long userId, long friendId);

    public Friendship hasFriend(long userId, long friendId);
}
