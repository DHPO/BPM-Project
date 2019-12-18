package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Content;
import cn.edu.sjtu.bpmproject.server.entity.Photo;

import java.io.File;
import java.io.IOException;

public interface ContentDao {
    public Content saveContent(long activityId);

    public String addContent( File contentFile) throws IOException;

    public String getContent(String url) throws IOException;
}
