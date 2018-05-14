package com.xunyu.cmpp.handler;

import com.xunyu.cmpp.message.CmppConnectRequestMessage;
import com.xunyu.cmpp.packet.CmppPacketType;
import com.xunyu.cmpp.packet.Message;
import com.xunyu.cmpp.packet.PacketType;
import com.xunyu.cmpp.packet.connect.CmppConnectRequest;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xym
 * @description 服务端handler
 * @date 2018/4/18 14:40
 */
public class CmppServerChannelHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(CmppServerChannelHandler.class);

    private ConcurrentHashMap<Long, MessageToMessageCodec> codecMap = new ConcurrentHashMap<Long, MessageToMessageCodec>();

    public CmppServerChannelHandler(){
        for (PacketType packetType : CmppPacketType.values()) {
            codecMap.put(packetType.getCommandId(), packetType.getCodec());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 第一次接收到的信息应该是客户端传来的连接请求
         */
        logger.info("read cmppConnectRequestMessage :{}",((CmppConnectRequestMessage)msg).getSourceAddr());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("有异常出现，原因：{}",cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("有客户端失去连接...");
    }
}
