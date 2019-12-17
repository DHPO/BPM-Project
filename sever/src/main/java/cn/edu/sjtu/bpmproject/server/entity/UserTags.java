package cn.edu.sjtu.bpmproject.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTags {
    long id;
    long userid;
    String tagname;
    long number;
}
