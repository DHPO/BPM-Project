package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.InteractionDao;
import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import cn.edu.sjtu.bpmproject.server.vo.InteractionVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class InteractionDaoImpl implements InteractionDao{

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String INTERACTION="Interaction/";

    @Override
    public Interaction addInteraction(Interaction interaction){
        String url= ResourceAPI.RMP_URL+INTERACTION;
        LOGGER.info("addInteraction ："+interaction);
        Interaction interaction1=restTemplate.postForObject(url,interaction,Interaction.class);
        LOGGER.info("addInteraction result："+interaction1);
        return interaction1;
    }

    @Override
    public List<Interaction> getInteractions(long activityId) {
        String url= ResourceAPI.RMP_URL+INTERACTION+"?Interaction.activityid="+activityId;
        String interactions=restTemplate.getForObject(url,String.class);
        LOGGER.info("getInteractions result："+interactions);
        JSONObject jsonObject = JSONObject.fromObject(interactions);
        if(!jsonObject.has("Interaction")){
            return null;
        }
        interactions = jsonObject.getString("Interaction");
        return (List<Interaction>) JSONArray.toList(JSONArray.fromObject(interactions), Interaction.class);
    }

    @Override
    public void updateInteraction(Interaction interaction) {
        String url=ResourceAPI.RMP_URL+INTERACTION+interaction.getId();
        restTemplate.put(url,interaction);
    }

    @Override
    public Interaction getInteractionById(long interactionId) {
        String url=ResourceAPI.RMP_URL+INTERACTION+interactionId;
        return restTemplate.getForObject(url,Interaction.class);
    }
}
