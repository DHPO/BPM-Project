package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.vo.AllActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import cn.edu.sjtu.bpmproject.server.vo.SpActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.UserStatisticVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class StatisticController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "管理员获取活动粒度的统计分析", notes = "管理员获取活动粒度的统计分析")
    @RequestMapping(value = "/statistic/activities", method = RequestMethod.GET)
    public ResultVO<AllActivityStatisticVO> getActivityStatistics() {
        // TODO
        return null;
    }

    @ApiOperation(value = "管理员获取用户粒度的统计分析", notes = "管理员获取用户粒度的统计分析")
    @RequestMapping(value = "/statistic/users", method = RequestMethod.GET)
    public ResultVO<UserStatisticVO> getUserStatistics() {
        // TODO
        return null;
    }

    @ApiOperation(value = "活动组织者获取每场活动的统计分析", notes = "活动组织者获取每场活动的统计分析")
    @RequestMapping(value = "/statistic/activity/{activityId}", method = RequestMethod.GET)
    public ResultVO<SpActivityStatisticVO> getActivityStatistics(@PathVariable(value = "activityId") long activityId) {
        // TODO
        return null;
    }
}
