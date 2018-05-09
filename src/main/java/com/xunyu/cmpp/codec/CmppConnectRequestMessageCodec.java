package com.xunyu.cmpp.codec;

import com.xunyu.cmpp.message.CmppConnectRequestMessage;
import com.xunyu.cmpp.packet.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author xym
 * @description 请求连接消息 编解码器
 * @date 2018/4/18 15:48
 */
public class CmppConnectRequestMessageCodec extends MessageToMessageCodec<Message,CmppConnectRequestMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CmppConnectRequestMessage cmppConnectRequestMessage, List<Object> list) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {

    }
}
