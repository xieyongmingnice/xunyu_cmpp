package com.xunyu.cmpp.handler;

import com.xunyu.cmpp.constant.GlobalConstance;
import com.xunyu.cmpp.message.CmppConnectRequestMessage;
import com.xunyu.cmpp.message.CmppConnectResponseMessage;
import com.xunyu.cmpp.packet.CmppPacketType;
import com.xunyu.cmpp.packet.Message;
import com.xunyu.cmpp.packet.PacketStructure;
import com.xunyu.cmpp.packet.PacketType;
import com.xunyu.cmpp.packet.connect.CmppConnectRequest;
import com.xunyu.cmpp.packet.connect.CmppConnectResponse;
import com.xunyu.cmpp.utils.CMPPCommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EncoderAndDecoderHandler extends MessageToMessageCodec<Message,Message>{

    private ConcurrentHashMap<Long, MessageToMessageCodec> codecMap = new ConcurrentHashMap<Long, MessageToMessageCodec>();

    public EncoderAndDecoderHandler(){
        for (PacketType packetType : CmppPacketType.values()) {
            codecMap.put(packetType.getCommandId(), packetType.getCodec());
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message  msg, List<Object> out) throws Exception {

        long commandId = ((Long) msg.getHeader().getCommandId()).longValue();
        if (commandId == CmppPacketType.CMPP_CONNECT_REQUEST.getCommandId()) {
            ByteBuf bodyBuffer = Unpooled.buffer(CmppConnectRequest.AUTHENTICATOR_SOURCE.getBodyLength());

            bodyBuffer.writeBytes(CMPPCommonUtil.ensureLength(((CmppConnectRequestMessage) msg).getSourceAddr().getBytes(GlobalConstance.DEFAULT_TRANSPORT_CHARSET),
                    CmppConnectRequest.SOURCE_ADDR.getLength(), 0));
            bodyBuffer.writeBytes(CMPPCommonUtil.ensureLength(((CmppConnectRequestMessage) msg).getAuthenticatorSource(), CmppConnectRequest.AUTHENTICATOR_SOURCE.getLength(), 0));
            bodyBuffer.writeByte(((CmppConnectRequestMessage) msg).getVersion());
            bodyBuffer.writeInt((int) msg.getTimestamp());

            msg.setBodyBuffer(bodyBuffer.array());
            msg.getHeader().setBodyLength(msg.getBodyBuffer().length);
            ReferenceCountUtil.release(bodyBuffer);
            out.add(msg);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        long commandId = ((Long) msg.getHeader().getCommandId()).longValue();

        if (commandId == CmppPacketType.CMPP_CONNECT_REQUEST.getCommandId()){
            CmppConnectRequestMessage requestMessage = new CmppConnectRequestMessage(msg.getHeader());

            ByteBuf bodyBuffer = Unpooled.wrappedBuffer(msg.getBodyBuffer());
            requestMessage.setSourceAddr(bodyBuffer.readBytes(CmppConnectRequest.SOURCE_ADDR.getLength()).toString(GlobalConstance.DEFAULT_TRANSPORT_CHARSET).trim());
            /**
             * 不可以用array()方法将ByteBuf转成byte[]
             */
            requestMessage.setAuthenticatorSource(new byte[bodyBuffer.readBytes(CmppConnectRequest.AUTHENTICATOR_SOURCE.getLength()).readableBytes()]);

            requestMessage.setVersion(bodyBuffer.readUnsignedByte());
            requestMessage.setTimestamp(bodyBuffer.readUnsignedInt());

            ReferenceCountUtil.release(bodyBuffer);
            out.add(requestMessage);
        }
    }
}
