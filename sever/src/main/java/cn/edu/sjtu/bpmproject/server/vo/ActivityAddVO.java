package cn.edu.sjtu.bpmproject.server.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "活动信息")
public class ActivityAddVO {
    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "活动地点")
    private PositionVO location;

    @ApiModelProperty(value = "活动标签")
    private List<String> tags;

    @ApiModelProperty(value = "描述url")
    private String descriptionUrl;

    @ApiModelProperty(value = "图片url")
    private String photoUrl;

    @ApiModelProperty(value = "报名开始时间")
    private long registerstarttime;
    @ApiModelProperty(value = "报名结束时间")
    private long registerendtime;
    @ApiModelProperty(value = "活动开始时间")
    private long starttime;
    @ApiModelProperty(value = "活动结束时间")
    private long endtime;
    @ApiModelProperty(value = "最大报名人数")
    private int peoplenum;

}
