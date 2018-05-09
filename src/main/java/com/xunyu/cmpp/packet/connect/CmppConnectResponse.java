package com.xunyu.cmpp.packet.connect;

import com.xunyu.cmpp.packet.CmppDataType;
import com.xunyu.cmpp.packet.DataType;
import com.xunyu.cmpp.packet.PacketStructure;
/**
 * @author xym
 * @description   SP请求连接到ISMG（CMPP¬_CONNECT）操作
 *                CMPP_CONNECT操作的目的是SP向ISMG注册作为一个合法SP身份，
 *                若注册成功后即建立了应用层的连接，此后SP可以通过此ISMG接收和发送短信。
 *                ISMG以CMPP_CONNECT_RESP消息响应SP的请求。
 * @date 2018/4/18 16:15
 */
public enum  CmppConnectResponse implements PacketStructure {

    /**
     *  状态
        0：正确
        1：消息结构错
        2：非法源地址
        3：认证错
        4：版本太高
        5~ ：其他错误
     */
    STATUS(CmppDataType.UNSINGED_INTEGER, true, 1),

    /**
     * ISMG认证码，用于鉴别ISMG。
     * 其值通过单向MD5 hash计算得出，表示如下：
     * AuthenticatorISMG = MD5（Status+AuthenticatorSource+shared secret），
     * Shared secret 由中国移动与源地址实体事先商定，AuthenticatorSource为源地址实体发送给ISMG的对应消息CMPP_Connect中的值。
     * 认证出错时，此项为空。
     */
    AUTHENTICATOR_ISMG(CmppDataType.OCTCT_STRING, true, 16),

    /**
     * 服务器支持的最高版本号
     */
    VERSION(CmppDataType.UNSINGED_INTEGER, true, 1);

    private DataType dataType;
    private boolean isFixFiledLength;
    private int length;

    CmppConnectResponse(DataType dataType, boolean isFixFiledLength, int length) {
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
        return 18;
    }
}
