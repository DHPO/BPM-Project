package cn.edu.sjtu.bpmproject.server.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushMessage {

    private long userId;

    private String username;

    private String content;

    private long activityId;

}
