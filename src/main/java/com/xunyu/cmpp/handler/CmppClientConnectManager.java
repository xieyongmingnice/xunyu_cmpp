package com.xunyu.cmpp.handler;

import com.xunyu.cmpp.factory.MarshallingCodecFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author xym
 * @description 客户端连接管理器
 * @date 2018/4/19 11:17
 */
@ChannelHandler.Sharable
public abstract class CmppClientConnectManager extends ChannelHandlerAdapter implements TimerTask,ChannelHandlerHolder{

    private Logger logger = LoggerFactory.getLogger(CmppClientConnectManager.class);

    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int port;
    private final String host;
    private volatile boolean reconnect = true;
    private int attempts;

    public CmppClientConnectManager(Bootstrap bootstrap,Timer timer,int port,String host,Boolean reconnect){
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("当前链路激活");
        attempts = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("链路关闭");
        if (reconnect) {
            logger.info("链接关闭，将进行重连");
            if (attempts < 12) {
                attempts++;

                int timeout = 2 << attempts;

                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);

                ctx.fireChannelInactive();
            } else {
                logger.info("客户端不再进行重连...");
                ctx.close();
            }
        }
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        ChannelFuture future;
        //初始化配置(bootstrap是在构造器中传过来的)
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host, port);
        }
        //future对象
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                boolean succeed = f.isSuccess();
                //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
                if (!succeed) {
                    logger.info("重连失败");
                    f.channel().pipeline().fireChannelInactive();
                } else {
                    logger.info("重连成功");
                }
            }
        });
    }

}
