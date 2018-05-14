package com.xunyu.cmpp.packet;



/**
 * @author xym
 * @description 默认消息头
 * @date 2018/4/18 15:35
 */
public class DefaultHeader implements Header {
	private static final long serialVersionUID = -3059342529838994125L;

	private long headLength;
    private long packetLength;
    private long bodyLength;
    private long commandId;
    private long sequenceId;


    public void setHeadLength(long length) {
        this.headLength = length;
    }

    public long getHeadLength() {
        return headLength;
    }

    public void setPacketLength(long length) {
        this.packetLength = length;
    }

    public long getPacketLength() {
        return packetLength;
    }

    public void setBodyLength(long length) {
        this.bodyLength = length;
    }

    public long getBodyLength() {
        return bodyLength;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

    public long getCommandId() {
        return commandId;
    }

    public void setSequenceId(long transitionId) {
        this.sequenceId = transitionId & 0xffffffffL;
    }

    public long getSequenceId() {
        return sequenceId;
    }

	@Override
	public String toString() {
		return "DefaultHeader [commandId=" + commandId + ", sequenceId=" + sequenceId + "]";
	}

}
