package cn.edu.sjtu.bpmproject.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionVO {
    @ApiModelProperty(value = "具体地点")
    private String location;
    @ApiModelProperty(value = "经度")
    private double longtitude;
    @ApiModelProperty(value = "纬度")
    private double latitude;
}
