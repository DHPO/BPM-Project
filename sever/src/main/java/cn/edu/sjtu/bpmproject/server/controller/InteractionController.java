package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.vo.InteractionVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class InteractionController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "添加互动信息", notes = "添加互动信息")
    @RequestMapping(value = "/interaction", method = RequestMethod.POST)
    public ResultVO<InteractionVO> addInteractions(@RequestBody InteractionVO interactionVO) {
        // TODO
        return null;
    }

    @ApiOperation(value = "获取互动信息", notes = "获取互动信息")
    @RequestMapping(value = "/interaction", method = RequestMethod.GET)
    public ResultVO<InteractionVO> addInteractions(@RequestParam("activityId") long activityId) {
        // TODO
        return null;
    }

    @ApiOperation(value = "更新互动信息", notes = "更新互动信息")
    @RequestMapping(value = "/interaction/{interactionId}", method = RequestMethod.POST)
    public ResultVO<String> updateInteraction(@PathVariable("interactionId") long interactionId, @RequestBody Interaction interaction) {
        // TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "互动开始", notes = "互动开始")
    @RequestMapping(value = "/interaction/{interactionId}/start", method = RequestMethod.POST)
    public ResultVO<String> startInteraction(@PathVariable("interactionId") long interactionId) {
        // TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "互动结束", notes = "互动结束")
    @RequestMapping(value = "/interaction/{interactionId}/end", method = RequestMethod.POST)
    public ResultVO<String> endInteraction(@PathVariable("interactionId") long interactionId) {
        // TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "添加互动人数", notes = "添加互动人数")
    @RequestMapping(value = "/interaction/{interactionId}/attend", method = RequestMethod.POST)
    public ResultVO<String> addInteractionNum(@PathVariable("interactionId") long interactionId) {
        // TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

}
