package com.xunyu.cmpp.packet;

import java.io.Serializable;

/**
 * @author xym
 * @description 消息头接口
 * @date 2018/4/18 15:36
 */
public interface Header extends Serializable {
    void setHeadLength(long length);
    long getHeadLength();
    void setPacketLength(long length);
    long getPacketLength();
    void setBodyLength(long length);
    long getBodyLength();
    void setCommandId(long commandId);
    long getCommandId();
    void setSequenceId(long transitionId);
    long getSequenceId();
}
