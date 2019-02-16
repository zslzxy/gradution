/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.exec.execinterface;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.common.exec
 * @since 2019/1/31
 */
public interface Messageable {
	/**
	 * 状态码
	 * @return
	 */
	String code();

	/**
	 * 状态信息
	 * @return
	 */
	String msg();


}
