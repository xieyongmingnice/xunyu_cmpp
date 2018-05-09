package com.xunyu.cmpp.message;

import com.xunyu.cmpp.constant.GlobalConstance;
import com.xunyu.cmpp.packet.CmppPacketType;
import com.xunyu.cmpp.packet.DefaultMessage;
import com.xunyu.cmpp.packet.Header;

/**
 * @author xym
 * @description 连接请求消息实体类
 * @date 2018/4/18 16:11
 */
public class CmppConnectRequestMessage extends DefaultMessage {
    private String sourceAddr = GlobalConstance.EMPTY_STRING;

    private byte[] authenticatorSource = GlobalConstance.EMPTY_BYTES;

    private short version = 0x30;

    private long timestamp = 0L;

    public CmppConnectRequestMessage(Header header) {
        super(CmppPacketType.CMPP_CONNECT_REQUEST, header);
    }

    public CmppConnectRequestMessage() {
        super(CmppPacketType.CMPP_CONNECT_REQUEST);
    }

    /**
     * @return the sourceAddr
     */
    public String getSourceAddr() {
        return sourceAddr;
    }

    /**
     * @param
     *            ，maxLength is 6
     */
    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    /**
     * @return the authenticatorSource
     */
    public byte[] getAuthenticatorSource() {
        return authenticatorSource;
    }

    /**
     * @param authenticatorSource
     *            the authenticatorSource to set
     */
    public void setAuthenticatorSource(byte[] authenticatorSource) {
        this.authenticatorSource = authenticatorSource;
    }

    /**
     * @return the version
     */
    public short getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(short version) {
        this.version = version;
    }

    /**
     * @return the timestamp
     */
    @Override
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
