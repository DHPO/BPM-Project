package cn.edu.sjtu.bpmproject.server.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.entity.PushMessage;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.service.SocketIOService;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

@Service(value = "socketIOService")
public class SocketIOServiceImpl implements SocketIOService {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    //客户端连上socket服务器时执行此事件
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        if (userId != null) {
            clientMap.put(userId, client);
        }
        LOGGER.info(userId+" : connect success!");
    }

    //客户端断开socket服务器时执行此事件
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        if (userId != null) {
            clientMap.remove(userId);
            client.disconnect();
        }
        LOGGER.info(userId+" : disconnect success!");
    }


    //服务器向客户端发送弹幕
    public void pushMessageToOne(PushMessage pushMessage,String id) {
        if (!StringUtils.isEmpty(id)) {
            SocketIOClient client = clientMap.get(id);
            if (client != null) {
                client.sendEvent("pushMsg", pushMessage);
                LOGGER.info(pushMessage.getUserId()+" : pushMsg success!");
            }
        }
    }

    //服务器向所有客户端发弹幕
    public void pushMessageToAll(PushMessage pushMessage) {
        for (SocketIOClient client:clientMap.values()) {
            client.sendEvent("pushMsg", pushMessage);
        }
        LOGGER.info(pushMessage.getUserId()+" : pushMsg success!");
    }
}