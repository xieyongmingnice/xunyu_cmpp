/**
 * 
 */
package com.xunyu.cmpp.packet;

import com.xunyu.cmpp.codec.CmppConnectRequestMessageCodec;
import com.xunyu.cmpp.codec.CmppConnectResponseMessageCodec;
import com.xunyu.cmpp.packet.connect.CmppConnectRequest;
import com.xunyu.cmpp.packet.connect.CmppConnectResponse;
import io.netty.handler.codec.MessageToMessageCodec;


/**
 * @author xym
 * @description
 * @date 2018/4/18 14:57
 */
public enum CmppPacketType implements PacketType {
    /**
     * 请求连接
     */
    CMPP_CONNECT_REQUEST(0x00000001L, CmppConnectRequest.class, CmppConnectRequestMessageCodec.class),
    /**
     * 请求连接应答
     */
    CMPP_CONNECT_RESPONSE(0x80000001L, CmppConnectResponse.class, CmppConnectResponseMessageCodec.class)
    //TODO 其他方式补全
    ;


    private long commandId;

    private Class<? extends PacketStructure> packetStructure;

    private Class<? extends MessageToMessageCodec> codec;
    
    CmppPacketType(long commandId, Class<? extends PacketStructure> packetStructure, Class<? extends MessageToMessageCodec> codec) {
        this.commandId = commandId;
        this.packetStructure = packetStructure;
        this.codec = codec;
    }
    @Override
    public long getCommandId() {
        return commandId;
    }

    @Override
    public PacketStructure[] getPacketStructures() {
    	return packetStructure.getEnumConstants();
    }

    @Override
    public long getAllCommandId() {
        long defaultId = 0x0;
        long allCommandId = 0x0;
        for(CmppPacketType packetType : CmppPacketType.values()) {
            allCommandId |= packetType.commandId;
        }
        return allCommandId ^ defaultId;
    }

    @Override
	public MessageToMessageCodec getCodec() {
		
		try {
			return codec.newInstance();
		} catch (InstantiationException e) {
			return null;
		}
		catch(  IllegalAccessException e){
			return null;
		}
	}
}
