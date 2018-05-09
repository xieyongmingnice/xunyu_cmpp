package com.xunyu.cmpp.handler;

import com.google.common.primitives.Bytes;
import com.xunyu.cmpp.constant.ConnectStatus;
import com.xunyu.cmpp.constant.GlobalConstance;
import com.xunyu.cmpp.message.CmppConnectRequestMessage;
import com.xunyu.cmpp.message.CmppConnectResponseMessage;
import com.xunyu.cmpp.packet.CmppPacketType;
import com.xunyu.cmpp.packet.Message;
import com.xunyu.cmpp.packet.connect.CmppConnectRequest;
import com.xunyu.cmpp.packet.connect.CmppConnectResponse;
import com.xunyu.cmpp.utils.CachedMillisecondClock;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author xym
 * @description 客户端handler
 * @date 2018/4/18 14:09
 */
public class CmppClientChannelHandler extends ChannelHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(CmppClientChannelHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 客户端先请求连接服务器
         */
        connect(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 第一次接收应该接到的是 请求连接的response消息
         */
        //TODO 连接之后修改全局连接状态
        try {
            Message message = (Message)msg;
            long commandId = message.getHeader().getCommandId();
            if (commandId == CmppPacketType.CMPP_CONNECT_RESPONSE.getCommandId()){
                CmppConnectResponseMessage responseMessage = (CmppConnectResponseMessage)message;
                long status = responseMessage.getStatus();
                if (status == ConnectStatus.CORRECT.getId()){
                    logger.info("连接成功");
                }else {

                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("出现错误，原因：{}",cause.getMessage());
        ctx.close();
    }

    private void connect(ChannelHandlerContext ctx){
        String userName = "901782";
        String password = "ICP";
        //TODO 提供请求连接信息
        CmppConnectRequestMessage msg = new CmppConnectRequestMessage();
        String timestamp = DateFormatUtils.format(CachedMillisecondClock.INS.now(), "MMddHHmmss");
        byte[] userBytes = userName.getBytes(GlobalConstance.DEFAULT_TRANSPORT_CHARSET);
        byte[] passwdBytes = password.getBytes(GlobalConstance.DEFAULT_TRANSPORT_CHARSET);
        byte[] timestampBytes = timestamp.getBytes(GlobalConstance.DEFAULT_TRANSPORT_CHARSET);
        msg.setAuthenticatorSource(DigestUtils.md5(Bytes.concat(userBytes, new byte[9], passwdBytes, timestampBytes)));
        msg.setSourceAddr(userName);
        msg.setTimestamp(Long.parseLong(timestamp));
        msg.setVersion((short) 0X30);
        ctx.channel().writeAndFlush(msg);
    }
}
