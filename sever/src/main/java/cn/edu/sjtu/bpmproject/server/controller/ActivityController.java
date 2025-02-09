package cn.edu.sjtu.bpmproject.server.controller;


import cn.edu.sjtu.bpmproject.server.dao.TagDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Position;
import cn.edu.sjtu.bpmproject.server.entity.Tag;
import cn.edu.sjtu.bpmproject.server.enums.ActivityStatus;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.service.ActivityService;
import cn.edu.sjtu.bpmproject.server.util.FileUtil;
import cn.edu.sjtu.bpmproject.server.vo.ActivityAddVO;
import cn.edu.sjtu.bpmproject.server.vo.ActivityDetailVO;
import cn.edu.sjtu.bpmproject.server.vo.PositionVO;
import cn.edu.sjtu.bpmproject.server.vo.RecommendConditionVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ActivityController {

    private static Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @ApiOperation(value = "活动开始", notes = "活动开始")
    @RequestMapping(value = "/activity/{activityId}/start", method = RequestMethod.POST)
    public ResultVO<String> startActivity(@PathVariable("activityId") long activityId) {
        activityService.updateActivityStatus(activityId, ActivityStatus.IN_PROGRESS.ordinal());
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "活动结束", notes = "活动结束")
    @RequestMapping(value = "/activity/{activityId}/end", method = RequestMethod.POST)
    public ResultVO<String> endActivity(@PathVariable("activityId") long activityId) {
        activityService.updateActivityStatus(activityId, ActivityStatus.END.ordinal());
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "删除活动", notes = "删除活动")
    @RequestMapping(value = "/activity/{activityId}", method = RequestMethod.DELETE)
    public ResultVO<String> deleteActivity(@PathVariable(value = "activityId") long activityId) {
        activityService.deleteActivity(activityId);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "获取管理员审核列表", notes = "获取管理员审核列表")
    @RequiresRoles(value = "MANAGER")
    @RequestMapping(value = "/activity/check", method = RequestMethod.GET)
    public ResultVO<List<Activity>> getCheckActivities() {
        List<Activity> activityList=activityService.getCheckActivities();
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }

    @ApiOperation(value = "管理员审核活动", notes = "审核活动")
    @RequiresRoles(value = "MANAGER")
    @RequestMapping(value = "/activity/check/{activityId}", method = RequestMethod.POST)
    public ResultVO<String> checkActivity(@PathVariable(value = "activityId") long activityId, @RequestParam(value = "activityStatus") int activityStatus) {
        activityService.updateActivityStatus(activityId,activityStatus);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));

    }

    @ApiOperation(value = "根据关键词查询活动，如果关键词为空，则返回所有的活动", notes = "查询活动")
    @ApiImplicitParam(name = "keyword", value = "查询关键词")
    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    public ResultVO<List<Activity>> queryActivityByKeywords(@RequestParam(value = "keyword",required = false) String keyword) {
        List<Activity> activityList=activityService.queryActivityByKeywords(keyword);
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }


    @ApiOperation(value = "添加活动", notes = "添加活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityAddVO", value = "活动一系列信息，格式是String，参考：" +
                    "{\n" +
                    "\t\"name\":\"2019软院迎新晚会\",\n" +
                    "\t\"location\":{\n" +
                    "\t\t\"location\":\"上海交通大学闵行校区软件大楼1楼报告厅\",\n" +
                    "\t\t\"longtitude\":121.442,\n" +
                    "\t\t\"latitude\":31.022\n" +
                    "\t},\n" +
                    "\t\"tags\":[\"晚会\",\"学生活动\"],\n" +
                    "\t\"registerstarttime\":1575158400,\n" +
                    "\t\"registerendtime\":1575504000,\n" +
                    "\t\"starttime\":1575610200,\n" +
                    "\t\"endtime\":1575624600,\n" +
                    "\t\"peoplenum\":200\n" +
                    "}", required = true),
            @ApiImplicitParam(name = "photoFile", value = "活动海报，格式为文件", required = true),
            @ApiImplicitParam(name = "descriptionFile", value = "活动描述文件，格式为文件", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回活动信息")
    })
    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    public ResultVO<Activity> addActivity(@RequestPart("activityAddVO") String activityInfo, @RequestPart(value = "photoFile") MultipartFile photo, @RequestPart(value = "descriptionFile") MultipartFile description) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(activityInfo);
        ActivityAddVO activityAddVO = (ActivityAddVO) JSONObject.toBean(jsonObject, ActivityAddVO.class);
        LOGGER.info("activityAddVO:" + activityAddVO);
        Activity activity = activityService.addActivity(activityAddVO, FileUtil.transferFile(photo), FileUtil.transferFile(description));
        return new ResultVO<>(ResultStatus.SUCCESS.ordinal(), activity);
    }


    @ApiOperation(value = "添加活动(缩减版接口)", notes = "添加活动")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回活动信息")
    })
    @RequestMapping(value = "/activity/add", method = RequestMethod.POST)
    public ResultVO<Activity> addActivity(@RequestBody ActivityAddVO activityAddVO) throws IOException {
        Activity activity = activityService.addActivity(activityAddVO);
        return new ResultVO<>(ResultStatus.SUCCESS.ordinal(), activity);
    }

    @ApiOperation(value = "修改活动信息", notes = "修改活动信息")
    @RequestMapping(value = "/activity/{activityId}", method = RequestMethod.POST)
    public ResultVO<String> updateActivity(@PathVariable(value = "activityId") long activityId,@RequestBody ActivityAddVO activityAddVO){
        activityService.updateActivity(activityId,activityAddVO);
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value = "根据活动ID获取活动信息", notes = "根据活动ID获取活动信息")
    @RequestMapping(value = "/activity/{activityId}", method = RequestMethod.GET)
    public ResultVO<ActivityDetailVO> getActivityById(@PathVariable(value = "activityId") long activityId) {
        ActivityDetailVO activityDetailVO=activityService.getActivityDetailInfoById(activityId);
        LOGGER.info("getActivityById: "+activityDetailVO);
        return new ResultVO<>(ResultStatus.SUCCESS,activityDetailVO);
    }

    @ApiOperation(value = "获取活动组织者的所有活动", notes = "获取活动组织者的所有活动")
    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    public ResultVO<List<Activity>> getActivitiesByOrganizerId(@RequestParam(value = "organizerId") long organizerId) {
        List<Activity> activityList=activityService.getActivitiesByOrganizerId(organizerId);
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }

    @ApiOperation(value = "获取热门活动", notes = "获取热门活动")
    @RequestMapping(value = "/activity/hot", method = RequestMethod.GET)
    public ResultVO<List<Activity>> getHotActivities() {
        List<Activity> activityList=activityService.getHotActivities();
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }

    @ApiOperation(value = "获取附近的活动", notes = "获取附近的活动")
    @RequestMapping(value = "/activity/nearby", method = RequestMethod.POST)
    public ResultVO<List<Activity>> getNearbyActivities(@RequestBody PositionVO positionVO) {
        List<Activity> activityList=activityService.getNearbyActivities(positionVO);
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }

    @ApiOperation(value = "获取好友报名的活动", notes = "获取好友报名的活动")
    @RequestMapping(value = "/activity/friends", method = RequestMethod.GET)
    public ResultVO<List<Activity>> getFriendsActivities() {
        List<Activity> activityList=activityService.getFriendsActivities();
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }

    @ApiOperation(value = "获取综合推荐的活动(最多推荐10个活动)", notes = "获取综合推荐的活动")
    @RequestMapping(value = "/activity/recommend", method = RequestMethod.POST)
    public ResultVO<List<Activity>> getRecommendActivities(@RequestBody RecommendConditionVO recommendConditionVO) {
        List<Activity> activityList=activityService.getRecommendActivities(recommendConditionVO);
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }

    @ApiOperation(value = "根据标签获取活动", notes = "根据标签获取活动")
    @RequestMapping(value = "/activity/tag", method = RequestMethod.GET)
    public ResultVO<List<Activity>> getActivitiesByTag(@RequestParam("tag") String tag) {
        List<Activity> activityList=activityService.getActivitiesByTag(tag);
        return new ResultVO<>(ResultStatus.SUCCESS,activityList);
    }

    @ApiOperation(value = "删除所有活动", notes = "删除所有活动")
    @RequestMapping(value = "/activity", method = RequestMethod.DELETE)
    public ResultVO<String> deleteAllActivities() {
        List<Activity> activityList=activityService.queryActivityByKeywords("");
        for (Activity activity:activityList){
            activityService.deleteActivity(activity.getId());
        }
        return new ResultVO<>(ResultStatus.SUCCESS,"删除完成");
    }


}
