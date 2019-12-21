package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.ActivityDao;
import cn.edu.sjtu.bpmproject.server.dao.CommentDao;
import cn.edu.sjtu.bpmproject.server.dao.InteractionDao;
import cn.edu.sjtu.bpmproject.server.dao.UserDao;
import cn.edu.sjtu.bpmproject.server.dao.impl.InteractionDaoImpl;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Comment;
import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.InteractionType;
import cn.edu.sjtu.bpmproject.server.service.ActivityService;
import cn.edu.sjtu.bpmproject.server.service.CommentService;
import cn.edu.sjtu.bpmproject.server.service.InteractionService;
import cn.edu.sjtu.bpmproject.server.service.StatisticService;
import cn.edu.sjtu.bpmproject.server.vo.ActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.InteractionStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.SpActivityStatisticVO;
import cn.edu.sjtu.bpmproject.server.vo.StatisticQueryVO;
import cn.edu.sjtu.bpmproject.server.vo.UserStatisticVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatisticServiceImpl implements StatisticService {

    private static Logger LOGGER = LoggerFactory.getLogger(StatisticServiceImpl.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private InteractionDao interactionDao;

    @Override
    public List<ActivityStatisticVO> getActivityStatistics(StatisticQueryVO statisticQueryVO) {
        long startTime = statisticQueryVO.getStarttime();
        long endTime = statisticQueryVO.getEndtime();
        long splitTime = statisticQueryVO.getSplittime();
        List<ActivityStatisticVO> activityStatisticVOS = new ArrayList<>();

        for (long sliceBeginTime = startTime; sliceBeginTime < endTime; sliceBeginTime += splitTime) {
            ActivityStatisticVO activityStatisticVO = new ActivityStatisticVO();
            long sliceEndTime = sliceBeginTime + splitTime;
            activityStatisticVO.setTime(sliceEndTime);

            //获取活动数量、用户报名人次和签到人次
            Map<String, Long> map = getRegisterAndCheckin(sliceBeginTime, sliceEndTime);
            activityStatisticVO.setActivity(map.get("activity"));
            activityStatisticVO.setCheckin(map.get("checkin"));
            activityStatisticVO.setRegister(map.get("register"));

            //获取互动统计
            activityStatisticVO.setInteraction(getInteractionStatisticVO(sliceBeginTime, sliceEndTime));

            //获取弹幕总数
            activityStatisticVO.setComment(getCommentNum(sliceBeginTime, sliceEndTime));

            activityStatisticVOS.add(activityStatisticVO);
        }

        return activityStatisticVOS;
    }

    @Override
    public List<UserStatisticVO> getUserStatistics(StatisticQueryVO statisticQueryVO) {
        long startTime = statisticQueryVO.getStarttime();
        long endTime = statisticQueryVO.getEndtime();
        long splitTime = statisticQueryVO.getSplittime();
        List<UserStatisticVO> userStatisticVOS = new ArrayList<>();

        List<User> users = userDao.getUserByTime(0, startTime);
        long userNum = (users == null ? 0 : users.size());

        for (long sliceBeginTime = startTime; sliceBeginTime < endTime; sliceBeginTime += splitTime) {
            UserStatisticVO userStatisticVO = new UserStatisticVO();
            long sliceEndTime = sliceBeginTime + splitTime;
            userStatisticVO.setTime(sliceEndTime);

            //获取用户
            List<User> userList = userDao.getUserByTime(sliceBeginTime, sliceEndTime);
            userNum += (userList == null ? 0 : userList.size());
            userStatisticVO.setUser(userNum);

            //获取用户报名人次和签到人次
            Map<String, Long> map = getRegisterAndCheckin(sliceBeginTime, sliceEndTime);
            userStatisticVO.setCheckin(map.get("checkin"));
            userStatisticVO.setRegister(map.get("register"));
            userStatisticVOS.add(userStatisticVO);
        }

        return userStatisticVOS;
    }

    /**
     * 获取活动注册和报名人次
     *
     * @param sliceBeginTime
     * @param sliceEndTime
     * @return
     */
    private Map<String, Long> getRegisterAndCheckin(long sliceBeginTime, long sliceEndTime) {
        Map<String, Long> map = new HashMap<>();
        List<Activity> activityList = activityDao.getActivityByTime(sliceBeginTime, sliceEndTime);
        long register = 0;
        long checkin = 0;
        long activityNum = 0;
        if (activityList != null) {
            for (Activity activity : activityList) {
                register += activity.getRegisternum();
                checkin += activity.getAttendnum();
            }
            activityNum = (long) activityList.size();
        }
        map.put("register", register);
        map.put("checkin", checkin);
        map.put("activity", activityNum);
        return map;
    }

    private long getCommentNum(long sliceBeginTime, long sliceEndTime) {
        List<Comment> comments = commentDao.getCommentsByTime(sliceBeginTime, sliceEndTime);
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }


    private InteractionStatisticVO getInteractionStatisticVO(long sliceBeginTime, long sliceEndTime) {
        InteractionStatisticVO interactionStatisticVO = new InteractionStatisticVO();
        interactionStatisticVO.setVote(getInteractionNum(InteractionType.VOTE.ordinal(), sliceBeginTime, sliceEndTime));
        interactionStatisticVO.setGame(getInteractionNum(InteractionType.GAME.ordinal(), sliceBeginTime, sliceEndTime));
        interactionStatisticVO.setDraw(getInteractionNum(InteractionType.DRAW.ordinal(), sliceBeginTime, sliceEndTime));
        interactionStatisticVO.setVideo(getInteractionNum(InteractionType.VIDEO.ordinal(), sliceBeginTime, sliceEndTime));
        return interactionStatisticVO;
    }

    private long getInteractionNum(int type, long sliceBeginTime, long sliceEndTime) {
        List<Interaction> interactionList = interactionDao.getInteractionsByTimeandType(type, sliceBeginTime, sliceEndTime);
        if (interactionList == null) {
            return 0;
        }
        return interactionList.size();
    }

    @Override
    public SpActivityStatisticVO getActivityStatistics(long activityId) {
        Activity activity = activityService.getActivityById(activityId);
        SpActivityStatisticVO spActivityStatisticVO = new SpActivityStatisticVO();
        spActivityStatisticVO.setId(activityId);
        spActivityStatisticVO.setRegister(activity.getRegisternum());
        spActivityStatisticVO.setCheckin(activity.getAttendnum());
        spActivityStatisticVO.setComment(commentService.getComments(activityId));
        spActivityStatisticVO.setInteraction(interactionService.getInteractions(activityId).getInteractions());
        return spActivityStatisticVO;
    }
}
