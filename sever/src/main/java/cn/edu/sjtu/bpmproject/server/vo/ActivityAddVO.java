package cn.edu.sjtu.bpmproject.server.vo;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
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

    @ApiModelProperty(value = "描述")
    private String descriptionurl;

    @ApiModelProperty(value = "图片url")
    private String photourl;

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


    public Activity transferToActivity(Activity activity){
        activity.setName(this.getName());
        activity.setLocation(this.getLocation().getLocation());
        activity.setDescriptionurl(this.descriptionurl);
        activity.setPhotourl(this.photourl);
        activity.setRegisterstarttime(this.registerstarttime);
        activity.setRegisterendtime(this.registerendtime);
        activity.setStarttime(this.starttime);
        activity.setEndtime(this.endtime);
        activity.setPeoplenum(this.peoplenum);
        return activity;
    }
}
