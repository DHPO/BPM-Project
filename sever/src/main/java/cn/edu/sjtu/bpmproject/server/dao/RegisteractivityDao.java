package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Registeractivity;

import java.util.List;

public interface RegisteractivityDao {

    public void updateRegisteractivity(Registeractivity registeractivity);
    public Registeractivity getRegisteractivity(long userId, long activityId);
    public void register( Registeractivity registeractivity);
    public List<Registeractivity> getActivitiesByStatus(long userId, int registerActivityStatus);
    public List<Registeractivity> getCheckedinUsers(long activityId,int registerActivityStatus);
    public List<Registeractivity> getActivitiesByUser(long userId);
}
