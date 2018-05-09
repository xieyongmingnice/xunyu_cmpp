package com.xunyu.cmpp.handler;

import com.xunyu.cmpp.constant.GlobalConstance;
import com.xunyu.cmpp.constant.SessionState;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xym
 * @description 心跳检测处理器
 * @date 2018/4/18 18:18
 */
@Sharable
public class CmppServerIdleStateHandler extends ChannelHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CmppServerIdleStateHandler.class);

	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.ALL_IDLE) {
            	//如果是CMPP连接未建立，直接关闭
            	if(ctx.channel().attr(GlobalConstance.ATTRIBUTE_KEY).get() != SessionState.Connect){
            		logger.warn("connectting time out. ");
            		ctx.close();
            	}else{
//            		ctx.channel().writeAndFlush(new CmppActiveTestRequestMessage());
            	}
            } 
        }else{
        	ctx.fireUserEventTriggered(evt);
        }
    }
}
