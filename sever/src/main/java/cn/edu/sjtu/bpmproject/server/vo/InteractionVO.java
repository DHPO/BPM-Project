package cn.edu.sjtu.bpmproject.server.vo;

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
@ApiModel(description = "互动信息")
public class InteractionVO {
    @ApiModelProperty(value = "互动列表")
    private List<Interaction> interactions;
}
