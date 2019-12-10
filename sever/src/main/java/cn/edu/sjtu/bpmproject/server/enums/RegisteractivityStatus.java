package cn.edu.sjtu.bpmproject.server.enums;

public enum RegisteractivityStatus {
    REGISTERED("已报名"),NOT_CHECKED_IN("未签到"),CHECKED_IN("已签到");
    private String status;
    private RegisteractivityStatus(String status){
        this.status=status;
    }
}
