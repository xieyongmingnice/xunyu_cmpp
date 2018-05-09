package com.xunyu.cmpp.packet;

import com.xunyu.cmpp.utils.CachedMillisecondClock;
import com.xunyu.cmpp.utils.DefaultSequenceNumberUtil;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xym
 * @description 默认消息体
 * @date 2018/4/18 15:41
 */
public class DefaultMessage implements Message ,Cloneable {
	private static final long serialVersionUID = -4245789758843785127L;
	private PacketType packetType;
	private long timestamp = CachedMillisecondClock.INS.now();
	//消息的生命周期，单位秒, 0表示永不过期
	private long lifeTime=0;
	private AtomicInteger requests = new AtomicInteger();
	private Header header;
	private byte[] buffer;

	/**
	 * CMPP的消息字段太少,增加一个附加字段,方便业务处理,
	 * 比如给attach设置一个Map
	 **/
	private Serializable attachment;
	
	public DefaultMessage() {
	};

	public DefaultMessage(PacketType packetType, Header header) {
		setPacketType(packetType);
		if (header == null) {
			header = new DefaultHeader();

			header.setSequenceId(DefaultSequenceNumberUtil.getSequenceNo());
		}
		header.setCommandId(packetType.getCommandId());
		setHeader(header);
	};

	public DefaultMessage(PacketType packetType) {
		this(packetType, DefaultSequenceNumberUtil.getSequenceNo());
	}

	public DefaultMessage(PacketType packetType, long sequenceId) {
		setPacketType(packetType);
		Header header = new DefaultHeader();
		header.setSequenceId(sequenceId);
		header.setCommandId(packetType.getCommandId());
		setHeader(header);
	}

	@Override
	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;

	}

	@Override
	public PacketType getPacketType() {
		return packetType;
	}

	@Override
	public int incrementAndGetRequests() {
		return requests.incrementAndGet();
	}

	@Override
   public void resetRequests(){
	   requests = new AtomicInteger();
   }


	@Override
	public void setHeader(Header header) {
		this.header = header;
	}

	@Override
	public Header getHeader() {
		return header;
	}

	@Override
	public void setBodyBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	@Override
	public byte[] getBodyBuffer() {
		return buffer;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}
	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public long getLifeTime() {
		return lifeTime;
	}
	@Override
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	@Override
	public String toString() {
		return "DefaultMessage [packetType=" + packetType + ", header=" + header + ", getClass()=" + getClass() + "]";
	}

	@Override
	public boolean isTerminationLife() {
		return lifeTime !=0 && (( timestamp + lifeTime*1000 ) - CachedMillisecondClock.INS.now() < 0L);
	}

	@Override
	public Serializable getAttachment() {
		return attachment;
	}
	@Override
	public void setAttachment(Serializable attachment) {
		this.attachment = attachment;
	}

	@Override
	protected DefaultMessage clone() throws CloneNotSupportedException {
		DefaultMessage msg =  (DefaultMessage) super.clone();
		
		DefaultHeader newHeader = new DefaultHeader();
		newHeader.setSequenceId(header.getSequenceId());
		newHeader.setCommandId(packetType.getCommandId());
		msg.setHeader(newHeader);
		
		msg.resetRequests();
		msg.setTimestamp(CachedMillisecondClock.INS.now());
		return msg;
	}
}
