package cn.edu.sjtu.bpmproject.server.util;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "18037529";
    public static final String API_KEY = "ZVjyoPq9nIBFnTVTTluiOSvI";
    public static final String SECRET_KEY = "OK67SZ3vvpIEw54saMPn9QVyt5GrV2Cm";

    public static void main(String[] args) {
//        // 初始化一个AipNlp
//        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
////        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
////        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 调用接口
//        String text = "什么东西！";
//
//        // 传入可选参数调用接口
//        HashMap<String, Object> options = new HashMap<String, Object>();
//
//        // 情感倾向分析
//        JSONObject res = client.sentimentClassify(text, options);
//        System.out.println(res.toString(2));

        String s="{\n" +
                "  \"log_id\": 7309915242121037648,\n" +
                "  \"text\": \"什么东西！\",\n" +
                "  \"items\": [{\n" +
                "    \"positive_prob\": 0.300668,\n" +
                "    \"sentiment\": 0,\n" +
                "    \"confidence\": 0.331849,\n" +
                "    \"negative_prob\": 0.699332\n" +
                "  }]\n" +
                "}";
        JSONObject jsonObject=new JSONObject(s);
        JSONArray hobbies = jsonObject.getJSONArray("items");
        JSONObject jsonObject1=hobbies.getJSONObject(0);
        System.out.println(jsonObject1.get("positive_prob"));


    }
}