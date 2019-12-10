package cn.edu.sjtu.bpmproject.server.enums;

public enum ActivityStatus {
    DRAFT("草稿"),PENDING("审核中"),UNPASSED("审核未通过"),PASSED("审核通过"),REGISTERING("报名中"),NOT_START("活动未开始"),IN_PROGRESS("活动中"),END("活动结束");
    private String status;
    private ActivityStatus(String status){
        this.status=status;
    }
}
