/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.entity.myenum;

/**
 *
 * @Filename CourierEnum.java
 *
 * @Description 物流方式枚举类
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月14日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public enum  CourierEnum {

	ZT("ZT","中通快递"),
	YT("YT","圆通快递"),
	SF("SF","顺丰快递"),
	YD("YD","韵达快递"),
	YZ("YZ","邮政快递"),
	HT("HT","汇通快递");

	private String code;

	private String msg;

	private CourierEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 根据 code编码获取对应的Enum
	 * @param code
	 * @return
	 */
	public static CourierEnum getCourierEnumByCode(String code) {
		// CourierEnum.values() 得到的是 枚举类的 value
		for (CourierEnum courierEnum : CourierEnum.values()) {
			if (courierEnum.code.equals(code)) {
				return courierEnum;
			}
		}
		return null;
	}

	/**
	 * 根据 msg过去对应的Enum
	 * @param msg
	 * @return
	 */
	public static CourierEnum getCourierEnumByMsg(String msg) {
		// CourierEnum.values() 得到的是 枚举类的 value
		for (CourierEnum courierEnum : CourierEnum.values()) {
			if (courierEnum.msg.equals(msg)) {
				return courierEnum;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

