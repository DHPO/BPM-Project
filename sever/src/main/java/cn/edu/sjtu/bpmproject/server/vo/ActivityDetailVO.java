package cn.edu.sjtu.bpmproject.server.vo;

import cn.edu.sjtu.bpmproject.server.entity.Activity;
import cn.edu.sjtu.bpmproject.server.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetailVO {
    private Activity activity;
    private PositionVO position;
    private List<String> tags;
}
