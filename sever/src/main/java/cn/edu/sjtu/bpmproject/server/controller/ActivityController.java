package cn.edu.sjtu.bpmproject.server.controller;


import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.service.ActivityService;
import cn.edu.sjtu.bpmproject.server.util.FileUtil;
import cn.edu.sjtu.bpmproject.server.vo.ActivityAddVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ActivityService activityService;




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
            @ApiResponse(code=200,message = "返回活动信息")
    })
    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    public ResultVO<Activity> addActivity(@RequestPart("activityAddVO") String activityInfo , @RequestPart(value = "photoFile") MultipartFile photo, @RequestPart(value = "descriptionFile") MultipartFile description) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(activityInfo);
        ActivityAddVO activityAddVO=(ActivityAddVO)JSONObject.toBean(jsonObject,ActivityAddVO.class);
        LOGGER.info("activityAddVO:"+activityAddVO);
        Activity activity=activityService.addActivity(activityAddVO,FileUtil.transferFile(photo),FileUtil.transferFile(description));
        return new ResultVO<>(ResultStatus.SUCCESS.ordinal(),activity);
    }



}
