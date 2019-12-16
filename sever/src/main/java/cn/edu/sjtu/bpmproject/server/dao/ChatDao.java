package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Chat;
import cn.edu.sjtu.bpmproject.server.vo.ChatVO;

import java.util.List;

public interface ChatDao {
    public Chat addChat(Chat chat) ;

    public List<Chat> getChatRecords(long friendshipId);
}
