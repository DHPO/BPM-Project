package cn.edu.sjtu.bpmproject.server.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户统计分析信息")
public class UserStatisticVO {

    @ApiModelProperty("截止的时间戳")
    private long time;
    @ApiModelProperty("截止到截止时间的用户总数")
    private long user;
    @ApiModelProperty("从开始到结束时间的报名人数")
    private long register;
    @ApiModelProperty("从开始到结束时间的签到人数")
    private long checkin;

}
