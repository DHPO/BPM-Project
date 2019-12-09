package cn.edu.sjtu.bpmproject.server.enums;

public enum ChatType {

    TEXT("文字"),PICTURE("图片");
    private String type;
    private ChatType(String type){
        this.type=type;
    }

}
