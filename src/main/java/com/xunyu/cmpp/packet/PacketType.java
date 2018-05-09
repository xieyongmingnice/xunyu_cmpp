package com.xunyu.cmpp.packet;

import io.netty.handler.codec.MessageToMessageCodec;


/**
 * @author xym
 * @description
 * @date 2018/4/18 16:29
 */
public interface PacketType {

    long getCommandId();

    PacketStructure[] getPacketStructures();

    long getAllCommandId();

    MessageToMessageCodec getCodec();
}
