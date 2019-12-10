package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.enums.UserRole;
import cn.edu.sjtu.bpmproject.server.enums.UserStatus;
import cn.edu.sjtu.bpmproject.server.service.UserService;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value="/users", method= RequestMethod.GET)
    @RequiresRoles(value = "MANAGER")
    public ResultVO<String> getUsers(){
        String users=userService.getUsers();
        return new ResultVO<>(ResultStatus.SUCCESS,users);
    }

    @ApiOperation(value="获取用户信息", notes="获取用户信息")
    @RequestMapping(value="/user", method= RequestMethod.GET)
    public ResultVO<User> getUserByID(){
        Subject subject = SecurityUtils.getSubject();
        User user=(User)subject.getSession().getAttribute("user");
        return new ResultVO<>(ResultStatus.SUCCESS,user);
    }

    @ApiOperation(value="删除用户", notes="删除用户")
    @RequestMapping(value="/user/{userId}", method= RequestMethod.DELETE)
    @RequiresRoles(value = "MANAGER")
    public ResultVO<String> deleteUser(@PathVariable("userId") long userId){
        //TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value="改变用户状态（例如封禁）", notes="改变用户状态")
    @RequestMapping(value="/user/{userId}", method= RequestMethod.POST)
    @RequiresRoles(value = "MANAGER")
    public ResultVO<String> changeUserStatus(@PathVariable("userId") long userId,@RequestParam("userStatus") int userStatus){
        //TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

    @ApiOperation(value="根据用户名获取用户列表", notes="根据用户名获取用户列表")
    @RequestMapping(value="/users/find", method= RequestMethod.GET)
    public ResultVO<String> findUserByName(@RequestParam("username") String username){
        // TODO
        return null;
    }


}
