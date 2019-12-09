package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface PhotoDao {

    public Photo savePhoto(long activityId);

    public String addPhoto( File photoFile) throws IOException;
}
