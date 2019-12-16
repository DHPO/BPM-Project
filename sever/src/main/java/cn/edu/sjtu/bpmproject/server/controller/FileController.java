package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.dao.ContentDao;
import cn.edu.sjtu.bpmproject.server.dao.PhotoDao;
import cn.edu.sjtu.bpmproject.server.entity.Photo;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.util.FileUtil;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class FileController {
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private ContentDao contentDao;

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public ResultVO<String> uploadPhoto(@RequestPart(value = "photo") MultipartFile photo) throws IOException {
        String url= photoDao.addPhoto(FileUtil.transferFile(photo));
        return new ResultVO<>(ResultStatus.SUCCESS,url);
    }

    @ApiOperation(value = "上传内容", notes = "上传内容")
    @RequestMapping(value = "/content", method = RequestMethod.POST)
    public ResultVO<String> uploadFile(@RequestPart(value = "content") MultipartFile content) throws IOException {
        String url= contentDao.addContent(FileUtil.transferFile(content));
        return new ResultVO<>(ResultStatus.SUCCESS,url);
    }
}
