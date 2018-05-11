package com.xunyu.cmpp.handler;

import com.xunyu.cmpp.packet.CmppPacketType;
import com.xunyu.cmpp.packet.Message;
import com.xunyu.cmpp.packet.PacketType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.MessageToMessageCodec;
import java.util.concurrent.ConcurrentHashMap;

public class EncoderAndDecoderHandler extends ChannelInboundHandlerAdapter{

    private ConcurrentHashMap<Long, MessageToMessageCodec> codecMap = new ConcurrentHashMap<Long, MessageToMessageCodec>();

    private EncoderAndDecoderHandler(){
        for (PacketType packetType : CmppPacketType.values()) {
            codecMap.put(packetType.getCommandId(), packetType.getCodec());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        long commandId = ((Message)msg).getHeader().getCommandId();
        MessageToMessageCodec codec = codecMap.get(commandId);
        codec.channelRead(ctx,msg);
    }
}
