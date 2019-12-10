package cn.edu.sjtu.bpmproject.server.service.impl;

import cn.edu.sjtu.bpmproject.server.dao.InteractionDao;
import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import cn.edu.sjtu.bpmproject.server.service.InteractionService;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import cn.edu.sjtu.bpmproject.server.vo.InteractionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Component
public class InteractionServiceImpl implements InteractionService{

    @Autowired
    private InteractionDao interactionDao;

    @Override
    public InteractionVO addInteractions(InteractionVO interactionVO) {
        List<Interaction> interactionList=interactionVO.getInteractions();
        List<Interaction> newInteractions=new ArrayList<>();
        for (Interaction interaction:interactionList) {
            newInteractions.add(interactionDao.addInteraction(interaction));
        }
        return new InteractionVO(newInteractions);
    }

    @Override
    public InteractionVO getInteractions(long activityId) {
        List<Interaction> interactionList= interactionDao.getInteractions(activityId);
        return new InteractionVO(interactionList);
    }

    @Override
    public void updateInteraction(Interaction interaction) {
        interactionDao.updateInteraction(interaction);
    }

    @Override
    public void startInteraction(long interactionId) {
        Interaction interaction=interactionDao.getInteractionById(interactionId);
        interaction.setStarttime(TimeUtil.getTime());
        updateInteraction(interaction);
    }

    @Override
    public void endInteraction(long interactionId) {
        Interaction interaction=interactionDao.getInteractionById(interactionId);
        interaction.setEndtime(TimeUtil.getTime());
        updateInteraction(interaction);
    }

    @Override
    public void addInteractionNum(long interactionId) {
        Interaction interaction=interactionDao.getInteractionById(interactionId);
        interaction.setAttendnum(interaction.getAttendnum()+1);
        updateInteraction(interaction);
    }
}
