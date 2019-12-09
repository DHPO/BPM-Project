package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.enums.UserRole;
import cn.edu.sjtu.bpmproject.server.enums.UserStatus;
import cn.edu.sjtu.bpmproject.server.service.UserService;
import cn.edu.sjtu.bpmproject.server.util.PasswordHelper;
import cn.edu.sjtu.bpmproject.server.util.TimeUtil;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/api")
public class LoginController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultVO<String> register( @RequestParam(value = "username") String username,
                            @RequestParam(value = "password") String password) {

        User user=createUser(username,password);
        //对用户密码进行加密
        user.setPassword(passwordHelper.encryptPassword(user));
        LOGGER.info("register user info:" + user);
        userService.addUser(user);
        return new ResultVO<>(ResultStatus.SUCCESS,(ResultStatus.getStatus(ResultStatus.SUCCESS)));
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVO<User> login (
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) throws IncorrectCredentialsException {

        //对密码进行加密
        password=passwordHelper.encryptPassword(createUser(username,password));

        //验证用户名密码是否正确
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        LOGGER.info("登录成功");

        User user = userService.getUserByUsername(username);
        subject.getSession().setAttribute("user", user);
        return new ResultVO<>(ResultStatus.SUCCESS,user);

    }

    @ApiOperation(value = "用户请先登录", notes = "用户请先登录")
    @RequestMapping(value = "/userLogin",method = RequestMethod.GET)
    public void userLogin() throws MissingServletRequestParameterException{
        throw new MissingServletRequestParameterException("","");
    }


    @ApiOperation(value = "用户登出", notes = "用户登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultVO<String> logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }


    private User createUser(String username,String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.GENERAL.ordinal());
        user.setStatus(UserStatus.NORMAL.ordinal());
        user.setSalt(passwordHelper.createSalt(user));
        user.setAddtime(TimeUtil.getTime());
        return user;
    }
}
