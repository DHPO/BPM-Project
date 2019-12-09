package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.FriendshipDao;
import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.FriendshipStatus;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FriendshipDaoImpl implements FriendshipDao {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String FRIENDSHIP="Friendship/";
    @Override
    public List<Friendship> getFriends(long userId) {
        List<Friendship> friendshipList=new ArrayList<>();
        List<Friendship> friendship1=getFriends("user1id",userId);
        if (friendship1!=null) friendshipList.addAll(friendship1);
        List<Friendship> friendship2=getFriends("user2id",userId);
        if (friendship2!=null) friendshipList.addAll(friendship2);
        LOGGER.info("getFriendships: "+friendshipList);
        return friendshipList;
    }

    private List<Friendship> getFriends(String queryParam,long userId){
        String url= ResourceAPI.RMP_URL+FRIENDSHIP+"?Friendship."+queryParam+"="+userId;
        String friendshipInfo=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(friendshipInfo);
        if(!jsonObject.has("Friendship")){
            return null;
        }
        friendshipInfo = jsonObject.getString("Friendship");
        LOGGER.info("getSubFriendships: "+friendshipInfo);
        return JSONArray.toList(JSONArray.fromObject(friendshipInfo), Friendship.class);
    }

    @Override
    public Friendship addFriend(long userId, long friendId) {
        String url= ResourceAPI.RMP_URL+FRIENDSHIP;
        Friendship friendship=new Friendship(0,userId,friendId, FriendshipStatus.FRIEND.ordinal(), TimeUtil.getTime());
        Friendship friendship1=restTemplate.postForObject(url,friendship,Friendship.class);
        LOGGER.info("add friendshipInfo result："+friendship1);
        return friendship1;
    }

    @Override
    public Friendship hasFriend(long userId, long friendId){
        String url= ResourceAPI.RMP_URL+FRIENDSHIP+"?Friendship.user1id="+userId+"&Friendship.user2id="+friendId;
        LOGGER.info("has friend url: "+url);
        String friendshipInfo=restTemplate.getForObject(url,String.class);
        LOGGER.info("has friendshipInfo result："+friendshipInfo);
        JSONObject jsonObject = JSONObject.fromObject(friendshipInfo);
        if(!jsonObject.has("Friendship")){
            return null;
        }
        friendshipInfo = jsonObject.getString("Friendship");
        Friendship friendship=((List<Friendship>) JSONArray.toList(JSONArray.fromObject(friendshipInfo), Friendship.class)).get(0);
        LOGGER.info("has friendshipInfo ："+friendship);
        return friendship;
    }
}
