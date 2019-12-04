package cn.edu.sjtu.bpmproject.server.vo;

import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    private int status;
    private T data;

    public ResultVO(ResultStatus resultStatus,T data){
        this.status=resultStatus.ordinal();
        this.data=data;
    }

    public void setStatus(ResultStatus resultStatus){
        this.status=resultStatus.ordinal();
    }
}
