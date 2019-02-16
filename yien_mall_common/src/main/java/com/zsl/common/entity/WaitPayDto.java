/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 *
 * @Filename WaitPayDto.java
 *
 * @Description 未付款商品信息展示需求的字段
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年02月15日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaitPayDto extends BuyDto {

	private String goodsName;

	private String address;

	private String addressName;

	private BigInteger addressMobile;

}
