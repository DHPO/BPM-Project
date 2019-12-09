package cn.edu.sjtu.bpmproject.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private long id;
    private String name;
    private long starttime;
    private long endtime;
    private String location;
    private String descriptionurl;
    private int peoplenum;
    private int status;
    private int registernum;
    private int attendnum;
    private long addtime;
    private long updatetime;
    private long organizerid;
    private long registerstarttime;
    private long registerendtime;
    private String photourl;
}
