package cn.edu.sjtu.bpmproject.server.enums;

public enum ResultStatus {
    SUCCESS("成功"), FORBIDDEN("抱歉您没有权限访问该内容"), PASSWORD_ERROR("密码错误"), USER_NOT_EXIST("用户不存在"),
    LOCKED_ACCOUNT("账号已被锁定,请联系管理员"), NOT_LOGIN("请先登录"), SYSTEM_ERROR("恭喜你，触发bug了！ღ( ´･ᴗ･` )比心");

    private String status;

    private ResultStatus(String status) {
        this.status = status;
    }

    public static String getStatus(ResultStatus resultStatus){
        return resultStatus.status;
    }

}
