package cn.edu.sjtu.bpmproject.server.util;

import cn.edu.sjtu.bpmproject.server.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class UserUtil {
    public static long getUserId(){
        Subject subject = SecurityUtils.getSubject();
        long userId=((User)subject.getSession().getAttribute("user")).getId();
        return userId;
    }
}
