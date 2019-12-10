package cn.edu.sjtu.bpmproject.server.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "推荐的筛选条件")
public class RecommendConditionVO {

    private PositionVO positionVO;
}
