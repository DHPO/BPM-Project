package cn.edu.sjtu.bpmproject.server.handler;

import java.util.UUID;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.entity.PushMessage;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

@Component
public class MessageEventHandler{
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    public static SocketIOServer socketIOServer;

    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        MessageEventHandler.socketIOServer = server;
    }

    //客户端连上socket服务器时执行此事件
    @OnConnect
    public void onConnect(SocketIOClient client) {
        UUID socketSessionId = client.getSessionId();
        String ip = client.getRemoteAddress().toString();
        LOGGER.info("client connect, socketSessionId:{}, ip:{}", socketSessionId, ip);

    }

    //客户端断开socket服务器时执行此事件
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        UUID socketSessionId = client.getSessionId();
        String ip = client.getRemoteAddress().toString();
        client.disconnect();
        LOGGER.info("client disconnect, socketSessionId:{}, ip:{}", socketSessionId, ip);

    }

    //注册房间
    @OnEvent("comment_start")
    public void commentStart(SocketIOClient client, AckRequest request, String activityId) {
        UUID socketSessionId = client.getSessionId();
        String ip = client.getRemoteAddress().toString();
        client.joinRoom(activityId);
        LOGGER.info("client comment start, room:{}, socketSessionId:{}, ip:{}", activityId, socketSessionId, ip);

        // 客户端一订阅，就马上push一次
        pushMessageToAll(new PushMessage(0,"系统","请发弹幕",Long.valueOf(activityId)));
    }


    //服务器向所有客户端发弹幕
    public static void pushMessageToAll(PushMessage pushMessage) {
        String activityId=String.valueOf(pushMessage.getActivityId());
        socketIOServer.getRoomOperations(activityId).sendEvent("comment", pushMessage);
        LOGGER.info(pushMessage.getUserId()+" : pushMsg success!");
    }
}