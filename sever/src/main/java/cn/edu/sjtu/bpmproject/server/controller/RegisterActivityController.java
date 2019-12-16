package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Registeractivity;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.RegisteractivityStatus;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.exception.NotAllowedException;
import cn.edu.sjtu.bpmproject.server.service.ActivityService;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import cn.edu.sjtu.bpmproject.server.util.UserUtil;
import cn.edu.sjtu.bpmproject.server.vo.InteractionVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RegisterActivityController {
    private static Logger LOGGER = LoggerFactory.getLogger(RegisterActivityController.class);

    @Autowired
    private ActivityService activityService;

    @ApiOperation(value = "活动签到", notes = "活动签到")
    @RequestMapping(value = "/activity/checkin", method = RequestMethod.POST)
    public ResultVO<String> checkin(@RequestParam("activityId") long activityId) {
        activityService.checkin(activityId);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "活动报名", notes = "活动报名")
    @RequestMapping(value = "/activity/register", method = RequestMethod.POST)
    public ResultVO<String> register(@RequestParam("activityId") long activityId) {

        if(activityService.registercheck(activityId)){
            throw new NotAllowedException("不能重复报名活动");
        }

        Activity activity=activityService.getActivityById(activityId);

        if(activity.getOrganizerid()==UserUtil.getUserId()){
            throw new NotAllowedException("组织者不能报名活动！");
        }

        //判断报名人数
        int registernum=activity.getRegisternum();
        int peoplenum=activity.getPeoplenum();
        if(registernum>=peoplenum)
            return new ResultVO<>(ResultStatus.SUCCESS,"报名失败，人数已满");
        activity.setRegisternum(registernum+1);
        activity.setUpdatetime(TimeUtil.getTime());
        Registeractivity registeractivity=new Registeractivity(0,activityId, RegisteractivityStatus.REGISTERED.ordinal(), UserUtil.getUserId(), TimeUtil.getTime());
        activityService.register(activity,registeractivity);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "活动是否已报名,已报名为true", notes = "活动是否已报名")
    @RequestMapping(value = "/activity/registercheck", method = RequestMethod.POST)
    public ResultVO<Boolean> registercheck(@RequestParam("activityId") long activityId) {
        boolean checkResult=activityService.registercheck(activityId);
        return new ResultVO<>(ResultStatus.SUCCESS,checkResult);
    }

    @ApiOperation(value = "用户根据状态(已报名/已签到)获取活动", notes = "用户根据状态(已报名/已签到)获取活动")
    @RequestMapping(value = "/activity/userstatus", method = RequestMethod.GET)
    public ResultVO<List<Activity>> getActivitiesByRegisterActivityStatus(@RequestParam("status") int status) {
        List<Activity> activityList=activityService.getActivitiesByUserStatus(status);
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }


    @ApiOperation(value = "获得所有已签到的用户", notes = "获得所有已签到的用户")
    @RequestMapping(value = "/activity/checkin/users", method = RequestMethod.GET)
    public ResultVO<List<User>> getCheckedInUsers(@RequestParam("activityId") long activityId) {
        List<User> userList=activityService.getCheckedInUsers(activityId);
        return new ResultVO<>(ResultStatus.SUCCESS,userList);
    }


}
