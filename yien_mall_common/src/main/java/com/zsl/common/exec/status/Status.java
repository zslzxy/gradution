/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.exec.status;

import com.zsl.common.exec.execinterface.Messageable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Filename Status.java
 *
 * @Description 枚举异常状态响应结果
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月31日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public enum Status implements Messageable {

	SUCCESS("success", "成功"),
	FAIL("fail", "失败"),
	PROCESSING("processing", "处理中");

	private final String code;

	private final String msg;

	Status(String code, String msg) {
		this.code = code;
		this.msg =msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 根据枚举值码找到对应的枚举对象
	 * @param code
	 * @return
	 */
	public static Status findStatus(String code) {
		for (Status status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		//如果没有对应的code的状态码，则会抛出异常信息
		throw new IllegalArgumentException("未查询到: "+ code +" 对应的状态码的枚举对象");
	}

	/**
	 * 根据枚举状态码来获取对应的Status
	 * @param code
	 * @return
	 */
	public static Status getByCode(String code) {
		return findStatus(code);
	}

	/**
	 * 获取到所有的状态码对象
	 * @return
	 */
	public static List<Status> getAllStatus() {
		List<Status> list = new ArrayList<Status>();
		for (Status status : values()) {
			list.add(status);
		}
		return list;
	}

	/**
	 * 获取所有的状态码
	 * @return
	 */
	public static List<String> getAllCode() {
		List<String> list = new ArrayList<String>();
		for (Status status : values()) {
			list.add(status.code());
		}
		return list;
	}

	/**
	 * 判断当前枚举对象是否是这个对象中
	 * @param values
	 * @return
	 */
	public boolean isInList(Status... values) {
		for (Status value : values) {
			if (value == this) {
				return true;
			}
		}
		return false;
	}

	public String code() {
		return code;
	}

	public String msg() {
		return msg;
	}
}
