package cn.edu.sjtu.bpmproject.server.vo;

import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "结果内容")
public class ResultVO<T> {
    @ApiModelProperty(value = "结果状态 SUCCESS(\"成功\"), FORBIDDEN(\"抱歉您没有权限访问该内容\"), PASSWORD_ERROR(\"密码错误\"), USER_NOT_EXIST(\"用户不存在\"),\n" +
            "    LOCKED_ACCOUNT(\"账号已被锁定,请联系管理员\"), NOT_LOGIN(\"请先登录\"), SYSTEM_ERROR(\"系统异常\");\n")
    private int status;
    @ApiModelProperty(value = "数据内容")
    private T data;

    public ResultVO(ResultStatus resultStatus,T data){
        this.status=resultStatus.ordinal();
        this.data=data;
    }

    public void setStatus(ResultStatus resultStatus){
        this.status=resultStatus.ordinal();
    }
}
