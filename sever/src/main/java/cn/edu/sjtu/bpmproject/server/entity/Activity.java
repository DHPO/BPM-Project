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
@ApiModel(description = "活动信息")
public class Activity {
    private long id;
    @ApiModelProperty(value = "活动名称")
    private String name;
    @ApiModelProperty(value = "活动开始时间")
    private long starttime;
    @ApiModelProperty(value = "活动结束时间")
    private long endtime;
    @ApiModelProperty(value = "活动地点")
    private String location;

    @ApiModelProperty(value = "描述文件的url")
    private String descriptionurl;

    @ApiModelProperty(value = "最大报名人数")
    private int peoplenum;

    @ApiModelProperty(value = "活动状态  DRAFT(\"草稿\"),PENDING(\"审核中\"),UNPASSED(\"审核未通过\"),PASSED(\"审核通过\"),REGISTERING(\"报名中\"),NOT_START(\"活动未开始\"),IN_PROGRESS(\"活动中\"),END(\"活动结束\")")
    private int status;

    @ApiModelProperty(value = "活动报名人数")
    private int registernum;

    @ApiModelProperty(value = "活动签到人数")
    private int attendnum;

    @ApiModelProperty(value = "添加时间")
    private long addtime;

    @ApiModelProperty(value = "更新时间")
    private long updatetime;

    @ApiModelProperty(value = "活动组织者id")
    private long organizerid;

    @ApiModelProperty(value = "报名开始时间")
    private long registerstarttime;
    @ApiModelProperty(value = "报名结束时间")
    private long registerendtime;
    @ApiModelProperty(value = "活动海报的url")
    private String photourl;
}
