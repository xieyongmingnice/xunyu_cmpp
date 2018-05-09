package com.xunyu.cmpp.packet;




/**
 * @author xym
 * @description
 * @date 2018/4/18 16:42
 */
public interface PacketStructure {
    /**
     * 
     * @return
     */
    DataType getDataType();

    /**
     *
     * @return
     */
    boolean isFixFiledLength();

    /**
     *
     * @return
     */
    boolean isFixPacketLength();

    /**
     *
     * @return
     */
    int getLength();

    /**
     *
     * @return
     */
    int getBodyLength();
}
