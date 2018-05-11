package com.xunyu.cmpp.codec;

import com.xunyu.cmpp.message.CmppConnectResponseMessage;
import com.xunyu.cmpp.packet.CmppPacketType;
import com.xunyu.cmpp.packet.Message;
import com.xunyu.cmpp.packet.PacketType;
import com.xunyu.cmpp.packet.connect.CmppConnectResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * @author xym
 * @description 连接响应消息 编解码器
 * @date 2018/4/18 16:13
 */
public class CmppConnectResponseMessageCodec extends MessageToMessageCodec<Message,CmppConnectResponseMessage> {
    private PacketType packetType;

    /**
     *
     */
    public CmppConnectResponseMessageCodec() {
        this(CmppPacketType.CMPP_CONNECT_RESPONSE);
    }

    public CmppConnectResponseMessageCodec(PacketType packetType) {
        this.packetType = packetType;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        long commandId = ((Long) msg.getHeader().getCommandId()).longValue();
        if (commandId != packetType.getCommandId())
        {
            //不解析，交给下一个codec
            out.add(msg);
            return;
        }
        CmppConnectResponseMessage responseMessage = new CmppConnectResponseMessage(msg.getHeader());

        ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBodyBuffer());

        responseMessage.setStatus(bodyBuffer.readUnsignedInt());
        responseMessage.setAuthenticatorISMG(bodyBuffer.readBytes(CmppConnectResponse.AUTHENTICATOR_ISMG.getLength()).array());
        responseMessage.setVersion(bodyBuffer.readUnsignedByte());

        ReferenceCountUtil.release(bodyBuffer);
        out.add(responseMessage);

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, CmppConnectResponseMessage msg, List<Object> out) throws Exception {

        ByteBuf bodyBuffer = Unpooled.buffer(CmppConnectResponse.AUTHENTICATOR_ISMG.getBodyLength());

        bodyBuffer.writeInt((int) msg.getStatus());
        bodyBuffer.writeBytes(msg.getAuthenticatorISMG());
        bodyBuffer.writeByte(msg.getVersion());

        msg.setBodyBuffer(bodyBuffer.array());
        msg.getHeader().setBodyLength(msg.getBodyBuffer().length);
        ReferenceCountUtil.release(bodyBuffer);
        out.add(msg);

    }
}
