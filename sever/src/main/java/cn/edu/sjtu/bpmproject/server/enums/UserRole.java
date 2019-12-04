package cn.edu.sjtu.bpmproject.server.enums;

public enum UserRole {
    MANAGER("管理员"),GENERAL("普通用户");
    private String role;
    private UserRole(String role){
        this.role=role;
    }
    public static UserRole getUserRole(int role){
        return values()[role];
    }
}
