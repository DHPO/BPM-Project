package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.service.StatisticService;
import cn.edu.sjtu.bpmproject.server.vo.ActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import cn.edu.sjtu.bpmproject.server.vo.SpActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.StatisticQueryVO;
import cn.edu.sjtu.bpmproject.server.vo.UserStatisticVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class StatisticController {
    private static Logger LOGGER = LoggerFactory.getLogger(StatisticController.class);
    @Autowired
    StatisticService statisticService;

    @ApiOperation(value = "管理员获取活动粒度的统计分析", notes = "管理员获取活动粒度的统计分析")
    @RequestMapping(value = "/statistic/activities", method = RequestMethod.GET)
    public ResultVO<List<ActivityStatisticVO>> getActivityStatistics(@RequestBody StatisticQueryVO statisticQueryVO) {
        List<ActivityStatisticVO> activityStatisticVOS=statisticService.getActivityStatistics(statisticQueryVO);
        return new ResultVO<>(ResultStatus.SUCCESS,activityStatisticVOS);
    }

    @ApiOperation(value = "管理员获取用户粒度的统计分析", notes = "管理员获取用户粒度的统计分析")
    @RequestMapping(value = "/statistic/users", method = RequestMethod.GET)
    public ResultVO<List<UserStatisticVO>> getUserStatistics(@RequestBody StatisticQueryVO statisticQueryVO) {
        List<UserStatisticVO> userStatistics=statisticService.getUserStatistics(statisticQueryVO);
        return new ResultVO<>(ResultStatus.SUCCESS,userStatistics);
    }

    @ApiOperation(value = "活动组织者获取每场活动的统计分析", notes = "活动组织者获取每场活动的统计分析")
    @RequestMapping(value = "/statistic/activity/{activityId}", method = RequestMethod.GET)
    public ResultVO<SpActivityStatisticVO> getActivityStatistics(@PathVariable(value = "activityId") long activityId) {
        SpActivityStatisticVO spActivityStatisticVO=statisticService.getActivityStatistics(activityId);
        return new ResultVO<>(ResultStatus.SUCCESS,spActivityStatisticVO);
    }
}
