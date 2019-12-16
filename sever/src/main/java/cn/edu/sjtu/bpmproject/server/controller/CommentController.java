package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.PushMessage;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.handler.MessageEventHandler;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    private static Logger LOGGER = LoggerFactory.getLogger(CommentController.class);


    @ApiOperation(value = "用户发弹幕", notes = "用户发弹幕")
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResultVO<String> addComment(@RequestBody PushMessage pushMessage) {

        LOGGER.info("PushMessage: "+pushMessage);
        MessageEventHandler.pushMessageToAll(pushMessage);
        return new ResultVO<>(ResultStatus.SUCCESS,"发弹幕成功");
    }

}
