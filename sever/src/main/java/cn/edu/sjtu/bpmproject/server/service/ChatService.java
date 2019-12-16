package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.Chat;
import cn.edu.sjtu.bpmproject.server.vo.ChatVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ChatService {
    public Chat addChatContent(ChatVO chatVO);

    public Chat addPhotoShare(ChatVO chatVO,File photo) throws IOException;

    public List<Chat> getChatRecords(long friendshipId, int recordNum) ;

}
