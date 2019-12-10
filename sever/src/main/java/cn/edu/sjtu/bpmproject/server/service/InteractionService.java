package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import cn.edu.sjtu.bpmproject.server.vo.InteractionVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface InteractionService {

    public InteractionVO addInteractions(InteractionVO interactionVO);

    public InteractionVO getInteractions(long activityId);

    public void updateInteraction(Interaction interaction);

    public void startInteraction(long interactionId);

    public void endInteraction(long interactionId) ;

    public void addInteractionNum(long interactionId);


}
