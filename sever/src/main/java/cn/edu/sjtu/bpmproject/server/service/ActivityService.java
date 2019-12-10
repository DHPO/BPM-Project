package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.vo.ActivityAddVO;

import java.io.File;
import java.io.IOException;

public interface ActivityService {
    public Activity addActivity(ActivityAddVO activityAddVO, File photoFile, File contentFile) throws IOException;
}
