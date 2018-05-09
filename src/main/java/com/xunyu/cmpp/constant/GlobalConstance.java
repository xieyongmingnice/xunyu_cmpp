package com.xunyu.cmpp.constant;

import io.netty.util.AttributeKey;

import java.nio.charset.Charset;

/**
 * @author xym
 * @description 全局常量
 * @date 2018/4/18 16:18
 */
public interface GlobalConstance {
	/**
	 * 消息最大长度
	 */
	int MAX_MSG_LENGTH = 140;

	/**
	 * 空字符串
	 */
	String EMPTY_STRING = "";

	/**
	 * 空字符数组
	 */
	byte[] EMPTY_BYTES= new byte[0];

	/**
	 * 空字符串数组
	 */
	String[] EMPTY_STRING_ARRAY= new String[0];

	/**
	 *
	 */
    String IDLE_CHECKER_HANDLER_NAME = "IdleStateHandler";

	/**
	 * logger名称前缀
	 */
    String LOGGER_NAME_PREFIX = "entity.%s";

	/**
	 * 连接情况
	 */
	AttributeKey<SessionState> ATTRIBUTE_KEY = AttributeKey.newInstance(SessionState.Connect.name());

	/**
	 * 默认传输字符集
	 */
	Charset DEFAULT_TRANSPORT_CHARSET = Charset.forName("GBK");

}
