package cn.edu.sjtu.bpmproject.server.config;

import cn.edu.sjtu.bpmproject.server.controller.LoginController;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.UserRole;
import cn.edu.sjtu.bpmproject.server.enums.UserStatus;
import cn.edu.sjtu.bpmproject.server.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserRealm extends AuthorizingRealm {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class) ;

    @Autowired
    private UserService userService;
    /**
     * 认证权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        LOGGER.info("user:"+user);
        if(user == null) {
            throw new UnknownAccountException("账号不存在");
        }
        String role= UserRole.getUserRole(user.getRole()).toString();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(role);
        return info;
    }

    /**
     * 认证登录信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //查询用户信息
        User user = userService.getUserByUsername(token.getUsername());
        //账号不存在
        if(user == null) {
            throw new UnknownAccountException();
        }
        //账号锁定
        if(user.getStatus() == UserStatus.BLACK_LIST.ordinal()){
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo
                (user, user.getPassword(),
                        ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }
}
