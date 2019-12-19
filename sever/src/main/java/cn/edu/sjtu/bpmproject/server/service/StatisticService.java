package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.vo.ActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.SpActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.StatisticQueryVO;
import cn.edu.sjtu.bpmproject.server.vo.UserStatisticVO;

import java.util.List;

public interface StatisticService {
    public List<ActivityStatisticVO> getActivityStatistics(StatisticQueryVO statisticQueryVO);

    public List<UserStatisticVO> getUserStatistics(StatisticQueryVO statisticQueryVO);

    public SpActivityStatisticVO getActivityStatistics(long activityId) ;
}
