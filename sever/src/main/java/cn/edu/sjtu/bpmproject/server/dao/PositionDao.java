package cn.edu.sjtu.bpmproject.server.dao;

import cn.edu.sjtu.bpmproject.server.entity.Position;

public interface PositionDao {

    public Position addPosition(Position position);

    public Position getPositionById(long positionId);

    public Position getPositionByActivityId(long activityId);
}
