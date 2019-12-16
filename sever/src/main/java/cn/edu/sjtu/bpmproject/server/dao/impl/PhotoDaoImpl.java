package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.PhotoDao;
import cn.edu.sjtu.bpmproject.server.entity.Photo;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class PhotoDaoImpl implements PhotoDao {

    private static Logger LOGGER = LoggerFactory.getLogger(PhotoDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String PHOTO="Photo/";

    @Override
    public Photo savePhoto(long activityId) {
        String url= ResourceAPI.RMP_URL+PHOTO;
        Photo photo=new Photo(0,activityId);
        Photo photo1=restTemplate.postForObject(url,photo,Photo.class);
        return photo1;
    }

    @Override
    public String addPhoto(File photoFile) throws IOException {
        Photo photo=savePhoto(0);
        String url=ResourceAPI.RMP_URL+PHOTO+photo.getId();
        FileSystemResource resource = new FileSystemResource(photoFile);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        LOGGER.info("PhotoDaoImpl addPhoto responseEntity: "+responseEntity.getBody());
        return url.replace("Entity","file");
    }
}
