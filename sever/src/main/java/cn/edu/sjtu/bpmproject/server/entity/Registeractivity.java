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
@ApiModel(description = "用户参与活动信息")
public class Registeractivity {
    @ApiModelProperty(value = "主键id")
    private long id;
    @ApiModelProperty(value = "活动id")
    private long activityid;
    @ApiModelProperty(value = "参与状态 REGISTERED(\"已报名\"),NOT_CHECKED_IN(\"未签到\"),CHECKED_IN(\"已签到\")")
    private int status;
    @ApiModelProperty(value = "报名用户id")
    private long userid;
    @ApiModelProperty(value = "报名时间")
    private long registertime;
}
