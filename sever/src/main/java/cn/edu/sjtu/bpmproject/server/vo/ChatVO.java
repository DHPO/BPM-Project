package cn.edu.sjtu.bpmproject.server.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "聊天内容")
public class ChatVO {
    @ApiModelProperty(value = "好友id")
    private long friendId;
    @ApiModelProperty(value = "好友关系id")
    private long friendshipId;
    @ApiModelProperty(value = "内容类型 TEXT(\"文字\"),PICTURE(\"图片\")")
    private int type;
    @ApiModelProperty(value = "聊天内容")
    private String content;
}
