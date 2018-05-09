package com.xunyu.cmpp.factory;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xym
 * @description netty传输pojo使用的Marshalling序列化方式 工厂类
 * @date 2018/4/18 13:53
 */
public class MarshallingCodecFactory {

    private static Logger logger = LoggerFactory.getLogger(MarshallingCodecFactory.class);

    /**
     * @return
     * @autor dth
     * 解码器
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        /*
         * 通过 Marshalling 工具类的 getProvidedMarshallerFactory
         * 静态方法获取MarshallerFactory 实例,
         * 参数 serial 表示创建的是 Java 序列化工厂对象.它是由
         * jboss-marshalling-serial 包提供
         */
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        /*
         * 创建
         */
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(
                marshallerFactory, configuration);
        /*
         * provider : 提供商 maxSize : 单个对象最大尺寸
         */
        int maxSize = 1024 << 2;
        MarshallingDecoder decoder = new MarshallingDecoder(provider, maxSize);
        return decoder;
    }

    /**
     * @return
     * @autor dth
     * 编码器
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        logger.info("当前请求的字节数:{}",configuration.getBufferSize());
        MarshallerProvider provider = new DefaultMarshallerProvider(
                marshallerFactory, configuration);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }

}