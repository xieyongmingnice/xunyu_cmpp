package com.xunyu.cmpp.packet;

/**
 * @author xym
 * @description 消息头
 * @date 2018/4/18 16:32
 */
public enum CmppMessageHeader implements Head{

    /**
     *消息总长度(含消息头及消息体)
     */
    TOTAL_LENGTH(CmppDataType.UNSINGED_INTEGER,4),

    /**
     *命令或响应类型
     */
    COMMAND_ID(CmppDataType.UNSINGED_INTEGER,4),

    /**
     * 消息流水号,顺序累加,步长为1,循环使用（一对请求和应答消息的流水号必须相同）
     */
    SEQUENCE_ID(CmppDataType.UNSINGED_INTEGER,4)

    ;
    /**
     * 数据类型
     */
    private DataType dataType;
    /**
     *字节数
     */
    private int length;


    CmppMessageHeader(DataType dataType,int sequenceId){
        this.dataType = dataType;
        this.length = sequenceId;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getHeaderLength() {
        return 12;
    }
}
