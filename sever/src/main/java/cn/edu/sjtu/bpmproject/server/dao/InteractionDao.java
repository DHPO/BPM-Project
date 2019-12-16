package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Interaction;

import java.util.List;

public interface InteractionDao {

    public Interaction addInteraction(Interaction interaction);

    public List<Interaction> getInteractions(long activityId);

    public void updateInteraction(Interaction interaction);

    public Interaction getInteractionById(long interactionId);

    public void delelteInteraction(long interactionId);

}
