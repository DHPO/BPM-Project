package cn.edu.sjtu.bpmproject.server.dao.impl;

import cn.edu.sjtu.bpmproject.server.dao.ChatDao;
import cn.edu.sjtu.bpmproject.server.entity.Chat;
import cn.edu.sjtu.bpmproject.server.util.ResourceAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ChatDaoImpl implements ChatDao {

    private static Logger LOGGER = LoggerFactory.getLogger(ActivityDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    public static final String CHAT="Chat/";

    @Override
    public Chat addChat(Chat chat) {
        String url= ResourceAPI.RMP_URL+CHAT;
        Chat chat1=restTemplate.postForObject(url,chat,Chat.class);
        LOGGER.info("add chat result："+chat1);
        return chat1;
    }

    @Override
    public List<Chat> getChatRecords(long friendshipId) {
        String url= ResourceAPI.RMP_URL+CHAT+"?Chat.friendshipid="+friendshipId;
        List<Chat> chats=getChatRecords(url);
        LOGGER.info("getChatRecords : "+chats);
        return chats;
    }

    private List<Chat> getChatRecords(String url){
        String chats=restTemplate.getForObject(url,String.class);
        JSONObject jsonObject = JSONObject.fromObject(chats);
        if(!jsonObject.has("Chat")){
            return null;
        }
        chats = jsonObject.getString("Chat");
        return new Gson().fromJson(chats, new TypeToken<List<Chat>>(){}.getType());
    }
}
