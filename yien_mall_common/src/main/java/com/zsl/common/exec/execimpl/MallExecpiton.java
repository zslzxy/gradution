/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.exec.execimpl;

import com.zsl.common.exec.status.Status;

/**
 * @Filename MallExecpiton.java
 * @Description 购物商城的自定义异常
 * @Version 1.0
 * @Author 张世林
 * @Email 张世林@yiji.com
 * @History <li>Author: 张世林</li>
 * <li>Date: 2019年01月31日</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class MallExecpiton extends RuntimeException {

	private static final long serialVersionUID = -3242345352L;

	/**
	 * 定义一个状态对象
	 */
	private Status status;

	/**
	 * 获取状态值
	 *
	 * @return
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 设置状态值
	 *
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	public MallExecpiton(Status status, String msg) {
		super("状态为：" + status + "，错误信息为：" + msg);
		this.status = status;
	}

	public MallExecpiton() {
		super();
	}

	public MallExecpiton(String message) {
		super(message);
	}

	public MallExecpiton(String message, Throwable cause) {
		super(message, cause);
	}

	public MallExecpiton(Throwable cause) {
		super(cause);
	}

	protected MallExecpiton(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
