package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.Chat;
import cn.edu.sjtu.bpmproject.server.entity.PushMessage;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.handler.MessageEventHandler;
import cn.edu.sjtu.bpmproject.server.service.ChatService;
import cn.edu.sjtu.bpmproject.server.service.FriendshipService;
import cn.edu.sjtu.bpmproject.server.util.FileUtil;
import cn.edu.sjtu.bpmproject.server.vo.ChatVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ChatController {

    private static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private ChatService chatService;
    @Autowired
    private FriendshipService friendshipService;


    @ApiOperation(value = "获得之前聊天的记录", notes = "获得之前聊天的记录")
    @RequestMapping(value = "/chat/records", method = RequestMethod.GET)
    public ResultVO<List<Chat>> getChatRecords(@RequestParam(value = "friendId") long friendId,@RequestParam(value = "recordNum") int recordNum) {
        long friendshipId=friendshipService.getFriendship(friendId).getId();
        List<Chat> chats=chatService.getChatRecords(friendshipId,recordNum);
        return new ResultVO<>(ResultStatus.SUCCESS,chats);
    }

    @ApiOperation(value = "用户给好友发文字消息", notes = "用户给好友发文字消息")
    @RequestMapping(value = "/chat/text", method = RequestMethod.POST)
    public ResultVO<String> addTextChat(@RequestBody ChatVO chatVO) {
        Chat chat= chatService.addChatContent(chatVO);
        MessageEventHandler.sendChatMsg(chat);

        return new ResultVO<>(ResultStatus.SUCCESS,"发消息成功");
    }

//    @ApiOperation(value = "用户给好友发图片消息", notes = "用户给好友发图片消息")
//    @RequestMapping(value = "/chat/photo", method = RequestMethod.POST)
//    public ResultVO<String> addTextChat(@RequestPart("chatVO") String chatVO, @RequestPart(value = "photoFile") MultipartFile photo) throws IOException {
//        JSONObject jsonObject = JSONObject.fromObject(chatVO);
//        ChatVO chatVO1 = (ChatVO) JSONObject.toBean(jsonObject, ChatVO.class);
//        Chat chat=chatService.addPhotoShare(chatVO1, FileUtil.transferFile(photo));
//        MessageEventHandler.sendChatMsg(chat);
//        return new ResultVO<>(ResultStatus.SUCCESS,"发图片成功");
//    }

    @ApiOperation(value = "用户给好友发图片消息", notes = "用户给好友发图片消息")
    @RequestMapping(value = "/chat/photo", method = RequestMethod.POST)
    public ResultVO<String> addPhotoChat(@RequestBody ChatVO chatVO) {
        LOGGER.info("chatvo: "+chatVO);
        Chat chat=chatService.addChatContent(chatVO);
        MessageEventHandler.sendChatMsg(chat);
        return new ResultVO<>(ResultStatus.SUCCESS,"发图片成功");
    }


}
