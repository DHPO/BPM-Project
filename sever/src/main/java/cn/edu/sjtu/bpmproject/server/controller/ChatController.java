package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.Chat;
import cn.edu.sjtu.bpmproject.server.entity.PushMessage;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.handler.MessageEventHandler;
import cn.edu.sjtu.bpmproject.server.vo.ChatVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ChatController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @ApiOperation(value = "获得之前聊天的记录", notes = "获得之前聊天的记录")
    @RequestMapping(value = "/chat/records", method = RequestMethod.GET)
    public ResultVO<List<Chat>> getChatRecords(@RequestParam(value = "friendId") long friendId,@RequestParam(value = "recordNum") int recordNum) {
        // TODO
        return null;
    }

    @ApiOperation(value = "用户给好友发文字消息", notes = "用户给好友发文字消息")
    @RequestMapping(value = "/chat/text", method = RequestMethod.POST)
    public ResultVO<String> addTextChat(@RequestBody ChatVO chatVO) {

        //TODO
        return new ResultVO<>(ResultStatus.SUCCESS,"发消息成功");
    }

    @ApiOperation(value = "用户给好友发图片消息", notes = "用户给好友发图片消息")
    @RequestMapping(value = "/chat/photo", method = RequestMethod.POST)
    public ResultVO<String> addTextChat(@RequestPart("chatVO") String chatVO, @RequestPart(value = "photoFile") MultipartFile photo) {
        //TODO
        return new ResultVO<>(ResultStatus.SUCCESS,"发图片成功");
    }


}
