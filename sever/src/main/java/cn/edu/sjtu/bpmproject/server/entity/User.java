package cn.edu.sjtu.bpmproject.server.entity;

import cn.edu.sjtu.bpmproject.server.enums.UserRole;
import cn.edu.sjtu.bpmproject.server.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String username;
    private String password;
    private int role;
    private int status;
    private String salt;

}
