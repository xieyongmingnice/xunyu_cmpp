package com.xunyu.cmpp.handler;

import com.xunyu.cmpp.codec.CmppHeaderCodec;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xym
 * @description  初始化pipeline，解码是在当前handler前插入，使用pipeline.addBefore方法
 * @date 2018/4/18 17:57
 */
public class CmppCodecChannelInitializer extends ChannelInitializer<Channel> {

    private Logger logger = LoggerFactory.getLogger(CmppCodecChannelInitializer.class);

    public static String pipeName(){
        return "cmppCodec";
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();


    }
}
