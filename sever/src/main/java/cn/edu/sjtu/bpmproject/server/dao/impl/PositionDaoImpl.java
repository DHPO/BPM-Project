package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.PositionDao;
import cn.edu.sjtu.bpmproject.server.entity.Position;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    @Override
    public Position getPositionById(long positionId) {
        String url= ResourceAPI.RMP_URL+POSITION+positionId;
        Position position=restTemplate.getForObject(url,Position.class);
        return position;
    }

    @Override
    public Position getPositionByActivityId(long activityId) {
        String url= ResourceAPI.RMP_URL+POSITION+"?Position.activityid="+activityId;
        List<Position> positions=getPositions(url);
        if (positions!=null){
           return positions.get(0);
        }
        return null;
    }

    @Override
    public void deletePosition(long id) {
        String url= ResourceAPI.RMP_URL+POSITION+id;
        restTemplate.delete(url);
    }

    private List<Position> getPositions(String url){
        String positions=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(positions);
        if(!jsonObject.has("Position")){
            return null;
        }
        positions = jsonObject.getString("Position");
        return new Gson().fromJson(positions, new TypeToken<List<Position>>(){}.getType());
    }

}
