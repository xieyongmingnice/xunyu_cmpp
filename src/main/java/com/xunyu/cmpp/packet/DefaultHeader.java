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

    @Override
    public void setHeadLength(long length) {
        this.headLength = length;
    }

    @Override
    public long getHeadLength() {
        return headLength;
    }

    @Override
    public void setPacketLength(long length) {
        this.packetLength = length;
    }

    @Override
    public long getPacketLength() {
        return packetLength;
    }

    @Override
    public void setBodyLength(long length) {
        this.bodyLength = length;
    }

    @Override
    public long getBodyLength() {
        return bodyLength;
    }

     @Override
    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

     @Override
    public long getCommandId() {
        return commandId;
    }


    @Override
    public void setSequenceId(long transitionId) {
        this.sequenceId = transitionId & 0xffffffffL;
    }

    @Override
    public long getSequenceId() {
        return sequenceId;
    }

	@Override
	public String toString() {
		return "DefaultHeader [commandId=" + commandId + ", sequenceId=" + sequenceId + "]";
	}

}
