package cn.edu.sjtu.bpmproject.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "好友关系")
public class Friendship {
    @ApiModelProperty(value = "主键id")
    private long id;
    @ApiModelProperty(value = "用户1 ID")
    long user1id;
    @ApiModelProperty(value = "用户2 ID")
    long user2id;
    @ApiModelProperty(value = "好友状态： FRIEND(\"好友\"),DELETED(\"已删除\"),BLACK(\"已拉黑\")")
    int status;
    @ApiModelProperty(value = "添加好友时间")
    long addtime;
}
