package cn.edu.sjtu.bpmproject.server.util;

import cn.edu.sjtu.bpmproject.server.dao.ActivityDao;
import cn.edu.sjtu.bpmproject.server.dao.PositionDao;
import cn.edu.sjtu.bpmproject.server.dao.RegisteractivityDao;
import cn.edu.sjtu.bpmproject.server.dao.TagDao;
import cn.edu.sjtu.bpmproject.server.dao.UserTagsDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Position;
import cn.edu.sjtu.bpmproject.server.entity.Registeractivity;
import cn.edu.sjtu.bpmproject.server.entity.Tag;
import cn.edu.sjtu.bpmproject.server.entity.UserTags;
import cn.edu.sjtu.bpmproject.server.enums.ActivityStatus;
import cn.edu.sjtu.bpmproject.server.vo.PositionVO;
import cn.edu.sjtu.bpmproject.server.vo.RecommendConditionVO;
import com.baidu.aip.nlp.AipNlp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CFUtil {

    @Autowired
    UserTagsDao userTagsDao;
    @Autowired
    RegisteractivityDao registeractivityDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    PositionDao positionDao;
    @Autowired
    ActivityDao activityDao;

    //更新用户标签统计数据
    public void updateUserTags(long activityId){
        List<Tag>tags=tagDao.getTagsByActivityId(activityId);
        if(tags==null) return;
        long userId=UserUtil.getUserId();
        for(Tag tag:tags){
            String tagName=tag.getName();
            UserTags userTags=userTagsDao.getUserTagsByNameandUserId(tagName,userId);
            if (userTags!=null){
                userTags.setNumber(userTags.getNumber()+1);
                userTagsDao.updateUserTags(userTags);
            }else{
                userTags=new UserTags(0,userId,tagName,1);
                userTagsDao.addUserTags(userTags);
            }
        }
    }

    //根据标签数据和地理位置进行综合推荐
    public List<Activity> recommend(RecommendConditionVO recommendConditionVO) {
        long userId=UserUtil.getUserId();
        Map<Activity,Double> recommendList=new HashMap<>();
        List<Activity> activityList=activityDao.getActivityByStartTime(TimeUtil.getTime());
        List<Long> userRegisterActivityIdList=getUserRegisterActivityId(userId);
        Map<String,Long> userTagsMap=calUserTagsNumber(userId);
        Map<String,Integer> tagsUserLength=calTagsUserLength();

        for(Activity activity:activityList){
            long activityId=activity.getId();
            //除去用户已报名的活动
            if(userRegisterActivityIdList.contains(activityId)) continue;
            double Pua=0;
            List<Tag> tags=tagDao.getTagsByActivityId(activityId);
            //计算对活动的爱好程度
            for (Tag tag:tags) {
                String tagName=tag.getName();
                Long Nub=userTagsMap.get(tagName);
                Integer Nbu=tagsUserLength.get(tagName);
                if (Nub==null) continue;
                Pua+=Nub/Math.log10(1+Nbu);
            }
            Position position=positionDao.getPositionByActivityId(activityId);
            Pua=Pua*calDistanceFactor(position,recommendConditionVO.getPositionVO());
            recommendList.put(activity,Pua);
        }

        //对活动爱好程度倒序
        Map<Activity, Double> result = new LinkedHashMap<>();
        recommendList.entrySet().stream()
                .sorted(Map.Entry.<Activity, Double>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        //取前十个为推荐活动
        List<Activity> recList=new ArrayList<>(result.keySet());
        if(recList.size()<10){
            return recList;
        }
        return recList.subList(0,10);
    }

    private List<Long> getUserRegisterActivityId(long userId){
        List<Long> userRegisterActivityId=new ArrayList<>();
        List<Registeractivity> userRegisterActivities=registeractivityDao.getActivitiesByUser(userId);
        for(Registeractivity registeractivity:userRegisterActivities){
            userRegisterActivityId.add(registeractivity.getActivityid());
        }
        return userRegisterActivityId;
    }

    /**
     * 计算N_u,b
     * @param userId
     * @return
     */
    private Map<String,Long> calUserTagsNumber(long userId){
        Map<String,Long> userTagsNumber=new HashMap<>();
        List<UserTags> userTagsList=userTagsDao.getUserTagsByUserId(userId);
        if(userTagsList==null) return userTagsNumber;
        for (UserTags userTag:userTagsList) {
            userTagsNumber.put(userTag.getTagname(),userTag.getNumber());
        }
        return userTagsNumber;
    }

    /**
     * 计算Nb(u)
     * @return
     */
    private Map<String,Integer> calTagsUserLength(){
        List<UserTags> userTagsList=userTagsDao.getUserTags();
        Map<String,Integer> tagsUserLength=new HashMap<>();
        userTagsList.forEach(userTags -> {
            tagsUserLength.merge(userTags.getTagname(),1, (prev, one) -> prev + one);
        });
        return tagsUserLength;
    }

    /**
     * 计算距离惩罚因子
     * @param activityPos
     * @param userPos
     * @return
     */
    private double calDistanceFactor(Position activityPos, PositionVO userPos){
        double factor=1;
        double dt=3;
        double distance=LocationUtil.getDistance(activityPos.getLatitude(),activityPos.getLongitude(),userPos.getLatitude(),userPos.getLongitude());
        double temp=dt/distance;
        if(temp<factor){
            factor=temp;
        }
        return factor;
    }
}