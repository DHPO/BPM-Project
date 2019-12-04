package cn.edu.sjtu.bpmproject.server.enums;

public enum UserStatus {
    NORMAL("正常用户"),BLACK_LIST("黑名单用户");
    private String status;
    private UserStatus(String status){
        this.status=status;
    }
    public static UserStatus getUserStatus(int status){
        return values()[status];
    }
}
