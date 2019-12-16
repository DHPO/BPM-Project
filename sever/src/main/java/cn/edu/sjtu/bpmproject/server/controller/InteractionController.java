package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.service.InteractionService;
import cn.edu.sjtu.bpmproject.server.vo.InteractionVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class InteractionController {

    private static Logger LOGGER = LoggerFactory.getLogger(InteractionController.class);

    @Autowired
    private InteractionService interactionService;

    @ApiOperation(value = "添加互动信息", notes = "添加互动信息")
    @RequestMapping(value = "/interaction", method = RequestMethod.POST)
    public ResultVO<InteractionVO> addInteractions(@RequestBody InteractionVO interactionVO) {
        InteractionVO interactionVO1=interactionService.addInteractions(interactionVO);
        return new ResultVO<>(ResultStatus.SUCCESS,interactionVO1);
    }



    @ApiOperation(value = "获取互动信息", notes = "获取互动信息")
    @RequestMapping(value = "/interaction", method = RequestMethod.GET)
    public ResultVO<InteractionVO> getInteractions(@RequestParam("activityId") long activityId) {
        InteractionVO interactionVO=interactionService.getInteractions(activityId);
        return new ResultVO<>(ResultStatus.SUCCESS,interactionVO);
    }

    @ApiOperation(value = "更新互动信息", notes = "更新互动信息")
    @RequestMapping(value = "/interaction/update", method = RequestMethod.POST)
    public ResultVO<String> updateInteraction( @RequestBody Interaction interaction) {
        interactionService.updateInteraction(interaction);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "删除互动信息", notes = "删除互动信息")
    @RequestMapping(value = "/interaction/{interactionId}", method = RequestMethod.DELETE)
    public ResultVO<String> deleteInteraction(@PathVariable("interactionId") long interactionId) {
        interactionService.delelteInteraction(interactionId);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "互动开始", notes = "互动开始")
    @RequestMapping(value = "/interaction/{interactionId}/start", method = RequestMethod.POST)
    public ResultVO<String> startInteraction(@PathVariable("interactionId") long interactionId) {
        interactionService.startInteraction(interactionId);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "互动结束", notes = "互动结束")
    @RequestMapping(value = "/interaction/{interactionId}/end", method = RequestMethod.POST)
    public ResultVO<String> endInteraction(@PathVariable("interactionId") long interactionId) {
        interactionService.endInteraction(interactionId);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "添加互动人数", notes = "添加互动人数")
    @RequestMapping(value = "/interaction/{interactionId}/attend", method = RequestMethod.POST)
    public ResultVO<String> addInteractionNum(@PathVariable("interactionId") long interactionId) {
        interactionService.addInteractionNum(interactionId);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

}
