package cn.edu.sjtu.bpmproject.server.util;

import cn.edu.sjtu.bpmproject.server.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

public class PasswordHelper {

    /**  加密算法 */
    public final static String hashAlgorithmName = "SHA-256";
    /**  循环次数 */
    public final static int hashIterations = 16;

    public String encryptPassword(User user) {
        return new SimpleHash(hashAlgorithmName, user.getPassword(), user.getSalt(), hashIterations).toString();
    }

    /**
     * salt取当前时间的最后3位
     * @return
     */
    public String createSalt(User user){
        int len=user.getUsername().length()+user.getPassword().length();
        return String.valueOf(len);
    }
}