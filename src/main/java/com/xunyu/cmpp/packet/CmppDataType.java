package com.xunyu.cmpp.packet;

public enum CmppDataType implements DataType{

    /**
     * 无符号整数
     */
    UNSINGED_INTEGER(1),

    /**
     * 整数
     */
    INTEGER(2),

    /**
     * 定长型字符串
     */
    OCTCT_STRING(3);

    private int commandId;

    CmppDataType(int commandId){
        this.commandId = commandId;
    }

    @Override
    public int getCommandId() {
        return commandId;
    }

}
