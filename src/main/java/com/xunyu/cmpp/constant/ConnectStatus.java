package com.xunyu.cmpp.constant;
/**
 * @author xym
 * @description
 * @date 2018/4/19 15:39
 */
public enum ConnectStatus {

    CORRECT(0,"正确"),
    WRONG_STRUCT(1,"结构错误"),
    ILLEGAL_SOURCE(2,"非法的源地址"),
    AUTH_DEFEAT(3,"认证错误"),
    VERSION_TOO_HIGH(4,"版本太高"),
    OTHER_FALUTS(5,"其他错误")
    ;

    private ConnectStatus(long id,String message){
        this.id = id ;
        this.message = message;
    }

    private long id;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
