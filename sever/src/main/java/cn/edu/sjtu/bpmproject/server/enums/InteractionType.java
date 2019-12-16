package cn.edu.sjtu.bpmproject.server.enums;

public enum InteractionType {
    COMMENT("弹幕"), VOTE("投票"), GAME("答题闯关"),SCORE("评分"),DRAW("抽奖"),VIDEO("直播");
    private String type;
    private InteractionType(String type){
        this.type=type;
    }
}
