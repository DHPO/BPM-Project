package cn.edu.sjtu.bpmproject.server.enums;

public enum FriendshipStatus {
    FRIEND("好友"),DELETED("已删除"),BLACK("已拉黑");
    private String status;
    private FriendshipStatus(String status){
        this.status=status;
    }


}
