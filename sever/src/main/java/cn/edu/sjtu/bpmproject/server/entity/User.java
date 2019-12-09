package cn.edu.sjtu.bpmproject.server.entity;

import cn.edu.sjtu.bpmproject.server.enums.UserRole;
import cn.edu.sjtu.bpmproject.server.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户")
public class User {
    @ApiModelProperty(value = "主键id")
    private long id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "用户角色：MANAGER(\"管理员\"),GENERAL(\"普通用户\")")
    private int role;
    @ApiModelProperty(value = "用户状态：NORMAL(\"正常用户\"),BLACK_LIST(\"黑名单用户\")")
    private int status;
    @ApiModelProperty(value = "加密使用，勿填")
    private String salt;
    @ApiModelProperty(value = "用户添加时间")
    private long addtime;


}
