package com.xunyu.cmpp.codec;

import com.xunyu.cmpp.message.CmppConnectResponseMessage;
import com.xunyu.cmpp.packet.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author xym
 * @description 连接响应消息 编解码器
 * @date 2018/4/18 16:13
 */
public class CmppConnectResponseMessageCodec extends MessageToMessageCodec<Message,CmppConnectResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CmppConnectResponseMessage cmppConnectResponseMessage, List<Object> list) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {

    }
}
