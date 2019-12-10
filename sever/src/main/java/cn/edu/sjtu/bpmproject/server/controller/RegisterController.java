package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.vo.InteractionVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RegisterController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "活动签到", notes = "活动签到")
    @RequestMapping(value = "/checkIn", method = RequestMethod.POST)
    public ResultVO<String> checkIn(@RequestParam("activityId") long activityId,@RequestParam("userId") long userId) {
        // TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "活动报名", notes = "活动报名")
    @RequestMapping(value = "/activity/register", method = RequestMethod.POST)
    public ResultVO<String> register(@RequestParam("activityId") long activityId,@RequestParam("userId") long userId) {
        // TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "根据用户参与活动的状态获取活动", notes = "根据用户参与活动的状态获取活动")
    @RequestMapping(value = "/activity/status", method = RequestMethod.GET)
    public ResultVO<List<Activity>> getActivitiesByStatus(@RequestParam("status") int status) {
        // TODO
        return null;
    }

}
