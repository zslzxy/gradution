/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.entity;

import com.zsl.common.entity.myenum.BuyEnum;
import com.zsl.common.entity.myenum.CourierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.common.entity
 * @since 2019/2/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuyDto implements Serializable {

	private static final long serialVersionUID = -23452345355L;

	/**
	 * 订单号
	 */
	protected String orderId;
	/**
	 * 用户id
	 */
	protected String userId;
	/**
	 * 商品id
	 */
	protected String goodsId;
	/**
	 * 地址id
	 */
	protected String addressId;
	/**
	 * 快递方式
	 */
	protected CourierEnum couriesType;
	/**
	 * 购买数量
	 */
	protected Integer goodsNum;
	/**
	 * 购买金额
	 */
	protected BigDecimal amount;
	/**
	 * 创建时间
	 */
	protected Date createTime;

	/**
	 * 什么场景下购买的商品
	 */
	protected BuyEnum buyEnum;

}
