package cn.edu.sjtu.bpmproject.server.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "统计查询条件")
public class StatisticQueryVO {
    @ApiModelProperty( "开始时间")
    private long starttime;
    @ApiModelProperty( "结束时间")
    private long endtime;
    @ApiModelProperty( "间断时间（以13位时间戳给出）")
    private long splittime;
}
