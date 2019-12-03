package cn.edu.sjtu.bpmproject.server.service;

import cn.edu.sjtu.bpmproject.server.entity.PushMessage;

public interface SocketIOService {

    //推送的事件
    public static final String PUSH_EVENT = "push_event";

    // 推送信息给单个用户
    public void pushMessageToOne(PushMessage pushMessage,String id);

    // 推送消息给所有用户
    public void pushMessageToAll(PushMessage pushMessage);
}