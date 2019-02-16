/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.kill.ordinary;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.BuyDto;
import com.zsl.common.entity.Goods;
import com.zsl.common.entity.myenum.BuyEnum;
import com.zsl.common.entity.myenum.CourierEnum;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.order.OrdinaryOrderService;
import com.zsl.common.utils.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Filename OrinaryOrderServiceImpl.java
 *
 * @Description 普通商品购买，创建Dto对象
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
@Component
@Service(group = "orindary", version = "1.0.0")
public class OrinaryOrderServiceImpl implements OrdinaryOrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrinaryOrderServiceImpl.class);

	@Reference(group = "management",version = "1.0.0")
	private GoodsService goodsService;

	@Autowired
	@Qualifier("redisBuyDtoTemplate")
	private RedisTemplate redisBuyDtoTemplate;

	@Autowired
	@Qualifier("redisObjTemplate")
	private RedisTemplate redisObjTemplate;

	/**
	 * 根据用户的id查询到用户对应的未付款的订单的DTO
	 *
	 * @param userId
	 * @return
	 */
	@Override public List<BuyDto> selectOrderByUserIdFromRedis(String userId) {
		List<BuyDto> buyDtos = new ArrayList<BuyDto>();
		logger.info("用户名称为："+ userId);
		List<String> list = (List<String>) redisObjTemplate.opsForList().range(userId, 0 , -1);
		for (String s : list) {
//			redisBuyDtoTemplate.opsForList().leftPush(userId, s);
			BuyDto buyDto = (BuyDto) redisBuyDtoTemplate.opsForValue().get(s);
			if (buyDto != null) {
				buyDtos.add(buyDto);
			}
		}
		return buyDtos;
	}

	/**
	 * 用户购买商品，在这里创建DTO对象，并将其存入到Redis中，设置缓存过期时间为30分钟
	 * @param userId
	 * @param goodsId
	 * @param addressId
	 * @param couriesType
	 * @param goodsNum
	 * @return
	 */
	@Override
	public Msg matchOrdinaryOrder(String userId, String goodsId, String addressId, String couriesType, Integer goodsNum) {
		Goods goods = goodsService.selectGoodsByGoodsId(goodsId);
		//验证购买数量是否超出了商品的库存
		if (goods.getGoodsInventory() >= goodsNum) {
			BuyDto buyDto = new BuyDto();
			buyDto.setOrderId(String.valueOf(System.nanoTime()));
			buyDto.setUserId(userId);
			buyDto.setGoodsNum(goodsNum);
			buyDto.setGoodsId(goodsId);
			buyDto.setAddressId(addressId);
			buyDto.setCreateTime(new Date());
			buyDto.setCouriesType(CourierEnum.valueOf(couriesType));
			buyDto.setAmount(goods.getGoodsNowAmount().multiply(new BigDecimal(goodsNum)));
			buyDto.setBuyEnum(BuyEnum.ORINDARY);
			//使用一个集合来进行存储订单的订单号
			redisObjTemplate.opsForList().leftPush(userId, buyDto.getOrderId());
			//将订单数据存入到Redis中
			redisBuyDtoTemplate.opsForValue().set(buyDto.getOrderId(), buyDto);
			//设置订单30分钟以后过期
			redisBuyDtoTemplate.expire(buyDto.getOrderId(), 30, TimeUnit.MINUTES);

			return Msg.success().addInfo("订单创建成功，请尽快支付").addMap("orindaryDto", buyDto);
		}
		return Msg.fail().addInfo("库存不足，剩余库存为："+goods.getGoodsInventory());
	}

	/**
	 * 用户取消掉对应的未付款的订单，则取消掉对应的缓存中的数据
	 * @param orderId
	 * @return
	 */
	@Override public Msg clearOrder(String orderId) {
		BuyDto buyDto = (BuyDto) redisBuyDtoTemplate.opsForValue().get(orderId);
		if (buyDto == null) {
			return Msg.success().addInfo("订单已过期，删除成功");
		} else {
			try {
				redisBuyDtoTemplate.delete(orderId);
				return Msg.success().addInfo("订单数据成功");
			} catch (RuntimeException ex) {
				logger.info("删除对应的商品数据失败");
			}
		}
		return Msg.fail().addInfo("未知运行时异常");
	}

	/**
	 * 检查是否对应的订单已经过期了
	 * @param orderId
	 * @return
	 */
	@Override public Msg validateOrder(String orderId) {
		BuyDto buyDto = (BuyDto) redisBuyDtoTemplate.opsForValue().get(orderId);
		if (buyDto == null) {
			return Msg.success().addInfo("订单已失效，请重新创建");
		}
		Long expire = redisBuyDtoTemplate.getExpire(orderId);
		System.out.println("剩下的缓存时间："+ expire);
		if (expire <= 60) {
			return Msg.success().addInfo("订单已失效，请重新创建");
		}
		return Msg.success().addInfo("订单依然存在").addMap("buyDto",buyDto).addMap("expire", expire);
	}

}
