package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.PositionDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Position;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PositionDaoImpl implements PositionDao{
    private static Logger LOGGER = LoggerFactory.getLogger(PositionDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String POSITION="Position/";

    @Override
    public Position addPosition(Position position) {
        String url= ResourceAPI.RMP_URL+POSITION;
        Position position1=restTemplate.postForObject(url,position,Position.class);
        LOGGER.info("add Position result："+position1);
        return position1;
    }

}
