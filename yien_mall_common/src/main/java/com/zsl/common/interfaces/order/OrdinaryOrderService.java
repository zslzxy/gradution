/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.interfaces.order;

import com.zsl.common.entity.BuyDto;
import com.zsl.common.utils.Msg;

import java.util.List;

/**
 *
 * @Filename OrdinaryOrderService.java
 *
 * @Description 购买普通商品的 OrdinaryOrderService
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
public interface OrdinaryOrderService {
	/**
	 * 用户购买商品，在这里创建DTO对象，并将其存入到Redis中，设置缓存过期时间为30分钟
	 * @param userId
	 * @param goodsId
	 * @param addressId
	 * @param couriesType
	 * @param goodsNum
	 * @return
	 */
	Msg matchOrdinaryOrder(String userId, String goodsId, String addressId, String couriesType, Integer goodsNum);

	/**
	 * 从Redis中获取到对应的数据
	 * @param userId
	 * @return
	 */
	List<BuyDto> selectOrderByUserIdFromRedis(String userId);


	/**
	 * 根据订单 id查询到对应的BuyDto对象
	 * 也就是验证对应的订单数据已经过期了
	 */
	Msg validateOrder(String orderId);

	/**
	 * 用户取消掉对应的未付款的订单，则需要去清空缓存的操作
	 * @param orderId
	 * @return
	 */
	Msg clearOrder(String orderId);
}
