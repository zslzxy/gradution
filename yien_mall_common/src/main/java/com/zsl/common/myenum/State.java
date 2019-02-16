/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.myenum;

/**
 * 用户的信誉，三个等级，优（正常购物）、中（无法参与秒杀）、差（无法登陆）
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.common.myenum
 * @since 2019/1/2
 */
public enum State {
	Cerdibility_Poor("01","差"),
	Cerdibility_General("02","中"),
	Cerdibility_Optimal("03","优");

	private String code;

	private String msg;

	State(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
