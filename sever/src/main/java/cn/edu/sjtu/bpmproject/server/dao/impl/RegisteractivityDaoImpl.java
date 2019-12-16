package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.dao.RegisteractivityDao;
import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.entity.Registeractivity;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.RegisteractivityStatus;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
public class RegisteractivityDaoImpl implements RegisteractivityDao{
    private static Logger LOGGER = LoggerFactory.getLogger(RegisteractivityDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String REGISTERACTIVITY="Registeractivity/";


    @Override
    public void updateRegisteractivity(Registeractivity registeractivity) {
        String url= ResourceAPI.RMP_URL+REGISTERACTIVITY+registeractivity.getId();
        restTemplate.put(url,registeractivity);
    }

    @Override
    public Registeractivity getRegisteractivity(long userId, long activityId){
        String url= ResourceAPI.RMP_URL+REGISTERACTIVITY+"?Registeractivity.activityid="+activityId+"&Registeractivity.userid="+userId;
        List<Registeractivity> registeractivityList=getRegisteractivity(url);
        if (registeractivityList==null) return null;
        return registeractivityList.get(0);
    }

    @Override
    public void register(Registeractivity registeractivity) {
        String url= ResourceAPI.RMP_URL+REGISTERACTIVITY;
        Registeractivity registeractivity1=restTemplate.postForObject(url,registeractivity,Registeractivity.class);
        LOGGER.info("add registeractivity result："+registeractivity1);
    }


    @Override
    public List<Registeractivity> getActivitiesByStatus(long userId, int registerActivityStatus) {
        String url= ResourceAPI.RMP_URL+REGISTERACTIVITY+"?Registeractivity.userid="+userId+"&Registeractivity.status="+registerActivityStatus;
        return getRegisteractivity(url);
    }

    @Override
    public List<Registeractivity> getCheckedinUsers(long activityId, int registerActivityStatus) {
        String url= ResourceAPI.RMP_URL+REGISTERACTIVITY+"?Registeractivity.activityid="+activityId+"&Registeractivity.status="+registerActivityStatus;
        return getRegisteractivity(url);
    }


    private List<Registeractivity> getRegisteractivity(String url){
        String registerActivityInfo=restTemplate.getForObject(url,String.class);
        LOGGER.info("registerActivityInfo result："+registerActivityInfo);
        JSONObject jsonObject = JSONObject.fromObject(registerActivityInfo);
        if(!jsonObject.has("Registeractivity")){
            return null;
        }
        registerActivityInfo = jsonObject.getString("Registeractivity");
        return new Gson().fromJson(registerActivityInfo, new TypeToken<List<Registeractivity>>(){}.getType());

    }
}
