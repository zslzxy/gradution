/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.entity.myenum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.common.entity.myenum
 * @since 2019/2/15
 */
public enum BuyEnum {

	KILL("KILL","秒杀"),
	ORINDARY("ORINDARY","普通购买");


	/** 枚举值码 */
	private final String code;

	/** 枚举描述 */
	private final String msg;

	/**
	 * 构建一个 BuyEnum 。
	 * @param code 枚举值码。
	 * @param msg 枚举描述。
	 */
	private BuyEnum (String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 得到枚举值码。
	 * @return 枚举值码。
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 得到枚举描述。
	 * @return 枚举描述。
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 得到枚举值码。
	 * @return 枚举值码。
	 */
	public String code() {
		return code;
	}

	/**
	 * 得到枚举描述。
	 * @return 枚举描述。
	 */
	public String msg() {
		return msg;
	}

	/**
	 * 通过枚举值码查找枚举值。
	 * @param code 查找枚举值的枚举值码。
	 * @return 枚举值码对应的枚举值。
	 * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
	 */
	public static BuyEnum findBuyEnum(String code) {
		for (BuyEnum buyEnum : values()) {
			if (buyEnum.getCode().equals(code)) {
				return buyEnum;
			}
		}
		throw new IllegalArgumentException("ResultInfo Status not legal:" + code);
	}

	public static BuyEnum getByCode(String code) {
		return findBuyEnum(code);
	}
	/**
	 * 获取全部枚举值。
	 *
	 * @return 全部枚举值。
	 */
	public static List<BuyEnum> getAllBuyEnum() {
		List<BuyEnum> list = new ArrayList<BuyEnum>();
		for (BuyEnum buyEnum : values()) {
			list.add(buyEnum);
		}
		return list;
	}

	/**
	 * 获取全部枚举值码。
	 *
	 * @return 全部枚举值码。
	 */
	public static List<String> getAllBuyEnumCode() {
		List<String> list = new ArrayList<String>();
		for (BuyEnum buyEnum : values()) {
			list.add(buyEnum.code());
		}
		return list;
	}

	/**
	 * 判断给定的枚举，是否在列表中
	 * @param values 列表
	 * @return
	 */
	public boolean isInList (BuyEnum... values) {
		for (BuyEnum buyEnum : values) {
			if (this == buyEnum) {
				return true;
			}
		}
		return false;
	}

}
