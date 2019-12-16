package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.ChatDao;
import cn.edu.sjtu.bpmproject.server.dao.PhotoDao;
import cn.edu.sjtu.bpmproject.server.entity.Chat;
import cn.edu.sjtu.bpmproject.server.service.ChatService;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import cn.edu.sjtu.bpmproject.server.util.UserUtil;
import cn.edu.sjtu.bpmproject.server.vo.ChatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private PhotoDao photoDao;

    @Override
    public Chat addChatContent(ChatVO chatVO) {
        Chat chat=new Chat(0,chatVO.getContent(),chatVO.getType(), TimeUtil.getTime(),chatVO.getFriendshipId(), UserUtil.getUserId());
        return chatDao.addChat(chat);
    }

    @Override
    public Chat addPhotoShare(ChatVO chatVO, File photoFile) throws IOException {
        String photoUrl=photoDao.addPhoto(photoFile);
        Chat chat=new Chat(0,photoUrl,chatVO.getType(), TimeUtil.getTime(),chatVO.getFriendshipId(), UserUtil.getUserId());
        return chatDao.addChat(chat);
    }

    @Override
    public List<Chat> getChatRecords(long friendshipId ,int recordNum) {
        List<Chat> chats= chatDao.getChatRecords(friendshipId);
        int size=chats.size();
        List<Chat> chatList=new ArrayList<>();
        if(size>=recordNum){
            chatList=chats.subList(size-recordNum,size);
        }else{
            chatList=chats;
        }
        return chatList;
    }
}
