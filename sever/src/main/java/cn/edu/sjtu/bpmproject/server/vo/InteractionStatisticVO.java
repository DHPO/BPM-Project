package cn.edu.sjtu.bpmproject.server.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "互动统计分析信息")
public class InteractionStatisticVO {
    private long vote;
    private long game;
    private long draw;
    private long video;

}
