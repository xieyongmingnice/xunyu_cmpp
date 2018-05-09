package com.xunyu.cmpp.handler;

import io.netty.channel.ChannelHandler;

/**
 * @author
 * @description
 * @date 2018/4/19 17:35
 */
public interface ChannelHandlerHolder {
    ChannelHandler [] handlers();
}
