package cn.edu.sjtu.bpmproject.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interaction {
    private long id;
    private String description;
    private int type;
    private long attendnum;
    private long activityid;
    private long starttime;
    private long endtime;
}
