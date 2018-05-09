package com.xunyu.cmpp;

import com.xunyu.cmpp.factory.MarshallingCodecFactory;
import com.xunyu.cmpp.handler.CmppClientChannelHandler;
import com.xunyu.cmpp.handler.CmppClientConnectManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;

import java.util.concurrent.TimeUnit;

/**
 * @author xym
 * @description cmpp 客户端
 * @date 2018/4/18 11:29
 */
public class CmppClient {

    public static void main(String[] args) {
        //TODO host port从配置中取
        String host = "localhost";
        int port = 8990;
        CmppClient cmppClient = CilentInstanceGetter.instance;
        cmppClient.doConnect(host,port);
    }
    /**
     * 1、创建线程池
     * 2、创建通道、注册选择器
     * 3、配置bootstrap参数
     * 4、配置通道
     * 5、建立连接
     * 6、同步等待
     */
    private void doConnect(String host,int port){
        final HashedWheelTimer timer = new HashedWheelTimer();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
            final CmppClientConnectManager manager = getManager(b,timer,port,host,true);
            b.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(manager.handlers());
                }
            });
            //发起异步链接操作
            ChannelFuture channelFuture = b.connect(host, port).sync();

            channelFuture.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    private CmppClientConnectManager getManager(Bootstrap bootstrap, HashedWheelTimer timer, int port, String host, Boolean reconnect){
        CmppClientConnectManager manager = new CmppClientConnectManager(bootstrap,timer,port,host,reconnect) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        this,
                        new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS),
                        MarshallingCodecFactory.buildMarshallingEncoder(),
                        MarshallingCodecFactory.buildMarshallingDecoder(),
                        new CmppClientChannelHandler()
                };
            }
        };
        return manager;
    }

    private CmppClient(){

    }

    private static class CilentInstanceGetter{
        private static CmppClient instance = new CmppClient();
    }


}
