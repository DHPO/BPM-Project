package cn.edu.sjtu.bpmproject.server.vo;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Comment;
import cn.edu.sjtu.bpmproject.server.entity.Interaction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "每场活动的统计分析信息")
public class SpActivityStatisticVO {
    @ApiModelProperty("活动id")
    private long id;
    @ApiModelProperty("报名人数")
    private long register;
    @ApiModelProperty("签到人数")
    private long checkin;
    @ApiModelProperty("活动弹幕")
    private List<Comment> comment;
    @ApiModelProperty("互动项目")
    private List<Interaction> interaction;
}
