package com.xunyu.cmpp.message;

import com.xunyu.cmpp.constant.GlobalConstance;
import com.xunyu.cmpp.packet.CmppPacketType;
import com.xunyu.cmpp.packet.DefaultMessage;
import com.xunyu.cmpp.packet.Header;

/**
 * @author xym
 * @description 连接响应消息实体类
 * @date 2018/4/18 16:02
 */
public class CmppConnectResponseMessage extends DefaultMessage {

    private static final long serialVersionUID = -5010314567064353091L;
    private long status = 3;
    private byte[] authenticatorISMG = GlobalConstance.EMPTY_BYTES;
    private short version = 0x30;

    public CmppConnectResponseMessage(Header header ) {
        super(CmppPacketType.CMPP_CONNECT_RESPONSE,header);
    }
    public CmppConnectResponseMessage(long sequenceId) {
        super(CmppPacketType.CMPP_CONNECT_RESPONSE,sequenceId);
    }
    /**
     * @return the status
     */
    public long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(long status) {
        this.status = status;
    }

    /**
     * @return the authenticatorISMG
     */
    public byte[] getAuthenticatorISMG() {
        return authenticatorISMG;
    }

    /**
     * @param authenticatorISMG the authenticatorISMG to set
     */
    public void setAuthenticatorISMG(byte[] authenticatorISMG) {
        this.authenticatorISMG = authenticatorISMG;
    }

    /**
     * @return the version
     */
    public short getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(short version) {
        this.version = version;
    }
}
