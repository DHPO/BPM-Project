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
@ApiModel(description = "互动信息")
public class Interaction {
    @ApiModelProperty(value = "主键id")
    private long id;
    @ApiModelProperty(value = "互动名称")
    private String name;
    @ApiModelProperty(value = "互动设置")
    private String description;
    @ApiModelProperty(value = "互动类型 COMMENT(\"弹幕\"), VOTE(\"投票\"), GAME(\"答题闯关\"),SCORE(\"评分\"),DRAW(\"抽奖\")")
    private int interactiontype;
    @ApiModelProperty(value = "互动参与人数")
    private int attendnum;
    @ApiModelProperty(value = "活动id")
    private long activityid;
    @ApiModelProperty(value = "互动开始时间")
    private long starttime;
    @ApiModelProperty(value = "互动结束时间")
    private long endtime;
}
