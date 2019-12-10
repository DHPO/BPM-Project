package cn.edu.sjtu.bpmproject.server.controller;

import cn.edu.sjtu.bpmproject.server.entity.Friendship;
import cn.edu.sjtu.bpmproject.server.entity.User;
import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.enums.UserStatus;
import cn.edu.sjtu.bpmproject.server.service.FriendshipService;
import cn.edu.sjtu.bpmproject.server.service.UserService;
import cn.edu.sjtu.bpmproject.server.util.UserUtil;
import cn.edu.sjtu.bpmproject.server.vo.AddFriendVO;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class FriendshipController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户获得好友列表", notes = "用户获得好友列表")
    @ApiResponses({
            @ApiResponse(code=200,message = "返回好友列表信息")
    })
    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public ResultVO<List<User>> getFriends() {
        long userId= UserUtil.getUserId();
        List<User> userList=friendshipService.getFriends(userId);
        return new ResultVO<>(ResultStatus.SUCCESS.ordinal(),userList);
    }

    @ApiOperation(value = "添加好友", notes = "添加好友")
    @ApiImplicitParam(name = "friendId", value = "好友id", required=true,dataType = "Interger")
    @ApiResponses({
            @ApiResponse(code=200,message = "返回好友关系和好友信息")
    })
    @RequestMapping(value = "/friend", method = RequestMethod.POST)
    public ResultVO<AddFriendVO> addFriend(@RequestParam(value = "friendId") long friendId) {
        long userId= UserUtil.getUserId();
        Friendship friendship=friendshipService.addFriend(userId,friendId);
        LOGGER.info("addFriendship result: "+friendship);
        User friend=userService.getUserByID(friendId);
        LOGGER.info("friend info result: "+friend);

        AddFriendVO addFriendVO=new AddFriendVO(friendship,friend);
        return new ResultVO<>(ResultStatus.SUCCESS.ordinal(),addFriendVO);
    }


    @ApiOperation(value = "用户删除好友", notes = "用户删除好友")
    @RequestMapping(value = "/friend", method = RequestMethod.DELETE)
    public ResultVO<String> deleteFriend(@RequestParam(value = "friendId") long friendId) {
        //TODO
        return new ResultVO<>(ResultStatus.SUCCESS,ResultStatus.getStatus(ResultStatus.SUCCESS));
    }

}
