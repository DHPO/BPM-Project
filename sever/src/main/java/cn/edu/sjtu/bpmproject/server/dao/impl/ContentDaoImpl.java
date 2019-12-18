package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.ContentDao;
import cn.edu.sjtu.bpmproject.server.entity.Content;
import cn.edu.sjtu.bpmproject.server.entity.Photo;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import cn.edu.sjtu.bpmproject.server.util.RestTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Component
public class ContentDaoImpl implements ContentDao{
    private static Logger LOGGER = LoggerFactory.getLogger(ContentDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String CONTENT="Content/";

    @Override
    public Content saveContent(long activityId) {
        String url= ResourceAPI.RMP_URL+CONTENT;
        Content content=new Content(0,activityId);
        Content content1=restTemplate.postForObject(url,content,Content.class);
        return content1;
    }

    @Override
    public String addContent(File contentFile) throws IOException {
        Content content=saveContent(0);
        String url=ResourceAPI.RMP_URL+CONTENT+content.getId();
        FileSystemResource resource = new FileSystemResource(contentFile);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        LOGGER.info("ContentDaoImpl addContent responseEntity: "+responseEntity.getBody());
        return url.replace("Entity","file");
    }

    public String getContent(String url) throws IOException{
        RestTemplate restTemplate= RestTemplateUtil.getInstance("utf-8");
        String content= restTemplate.getForObject(url,String.class);
        LOGGER.info("content: "+content);
        return content;
    }
}
