package cn.edu.sjtu.bpmproject.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private long id;
    private String description;
    private long commenttime;
    private long activityid;
    private long userid;
    private double positive_prob;
    private double negative_prob;
}
