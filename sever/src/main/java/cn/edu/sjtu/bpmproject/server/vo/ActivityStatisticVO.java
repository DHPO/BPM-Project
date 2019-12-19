package cn.edu.sjtu.bpmproject.server.vo;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "活动统计分析信息")
public class ActivityStatisticVO {
    @ApiModelProperty("截止的时间戳")
    private long time;
    @ApiModelProperty("从开始到结束时间的完成的活动数量")
    private long activity;
    @ApiModelProperty("从开始到结束时间的报名人数")
    private long register;
    @ApiModelProperty("从开始到结束时间的签到人数")
    private long checkin;
    @ApiModelProperty("从开始到结束时间的互动使用次数")
    private InteractionStatisticVO interaction;
    @ApiModelProperty("从开始到结束时间的弹幕总数")
    private long comment;
}
