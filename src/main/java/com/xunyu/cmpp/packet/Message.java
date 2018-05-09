package com.xunyu.cmpp.packet;

import java.io.Serializable;

/**
 * @author xym
 * @description
 * @date 2018/4/18 16:39
 */
public interface Message extends Serializable  {

	void setPacketType(PacketType packetType);

	PacketType getPacketType();

	void setTimestamp(long milliseconds);

	long getTimestamp();

	void setLifeTime(long lifeTime);

	long getLifeTime();

    int incrementAndGetRequests();

    boolean isTerminationLife();

    void resetRequests();

    void setHeader(Header head);

    Header getHeader();

    void setBodyBuffer(byte[] buffer);

    byte[] getBodyBuffer();

    Serializable getAttachment();

    void setAttachment(Serializable attachment);
}
