package com.xunyu.cmpp.packet.connect;

import com.xunyu.cmpp.packet.CmppDataType;
import com.xunyu.cmpp.packet.DataType;
import com.xunyu.cmpp.packet.PacketStructure;

/**
 *  SP请求连接到ISMG（CMPP→CONNECT）操作
 *  CMPP_CONNECT操作的目的是SP向ISMG注册作为一个合法SP身份，若注册成功后即建立了应用层的连接，
 *  此后SP可以通过此ISMG接收和发送短信。ISMG以CMPP_CONNECT_RESP消息响应SP的请求。
 */
public enum CmppConnectRequest implements PacketStructure {

    /**
     * 源地址，此处为SP_Id，即SP的企业代码。
     */
    SOURCE_ADDR(CmppDataType.OCTCT_STRING, true, 6),

    /**
     *用于鉴别源地址。其值通过单向MD5 hash计算得出，表示如下：
     * AuthenticatorSource = MD5（Source_Addr+9 字节的0 +shared secret+timestamp）
     * Shared secret 由中国移动与源地址实体事先商定，
     * timestamp格式为：MMDDHHMMSS，即月日时分秒，10位。
     */
    AUTHENTICATOR_SOURCE(CmppDataType.OCTCT_STRING, true, 16),

    /**
     * 双方协商的版本号(高位4bit表示主版本号,低位4bit表示次版本号)
     */
    VERSION(CmppDataType.UNSINGED_INTEGER, true, 1),

    /**
     * 时间戳的明文,由客户端产生,格式为MMDDHHMMSS，即月日时分秒，10位数字的整型，右对齐 。
     */
    TIMESTAMP(CmppDataType.UNSINGED_INTEGER, true, 4);

    private DataType dataType;
    private boolean isFixFiledLength;
    private int length;
    private final static int bodyLength = SOURCE_ADDR.length + AUTHENTICATOR_SOURCE.length + VERSION.length + TIMESTAMP.length;

    CmppConnectRequest(DataType dataType, boolean isFixFiledLength, int length) {
        this.dataType = dataType;
        this.isFixFiledLength = isFixFiledLength;
        this.length = length;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public boolean isFixFiledLength() {
        return isFixFiledLength;
    }

    @Override
    public boolean isFixPacketLength() {
        return true;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getBodyLength() {
        return bodyLength;
    }
}
