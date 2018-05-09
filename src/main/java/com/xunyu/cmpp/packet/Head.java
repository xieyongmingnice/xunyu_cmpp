package com.xunyu.cmpp.packet;

/**
 * @author xym
 * @description
 * @date 2018/4/18 16:38
 */
public interface Head {

    /**
     * 获取数据类型
     * */
    DataType getDataType();

    /**
     * 获取消息长度
     */
    int getLength();

    /**
     * 获取消息头长度
     */
    int getHeaderLength();
}
