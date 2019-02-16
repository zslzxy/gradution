/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.Goods;
import com.zsl.common.interfaces.search.GoodsSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @Filename SearchGoodsController.java
 *
 * @Description 商品查询的
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年02月01日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@RestController
@RequestMapping("/search")
public class SearchGoodsController {

	@Reference(group = "search", version = "1.0.0")
	private GoodsSearchService goodsSearchService;

	/**
	 * 根据商品名称，查询对应的商品的数据
	 * @param goodsNameValue
	 * @return
	 */
	@RequestMapping("/findByAllGoods")
	public List<Goods> findByGoodsName(String goodsNameValue) {
		List<Goods> list = goodsSearchService.findByGoodsName(goodsNameValue);
		return list;
	}

	/**
	 * 根据商品名称、商品的类型id查询对应的商品数据
	 * @param goodsNameValue
	 * @param goodsTypeId
	 * @return
	 */
	@RequestMapping("/findByGoodsNameAndGoodsTypeId")
	public List<Goods> findByGoodsNameAndGoodsTypeId(String goodsNameValue, Integer goodsTypeId) {
		List<Goods> list = goodsSearchService
				.findByGoodsNameAndGoodsTypeId(goodsNameValue, goodsTypeId);
		return list;
	}

}
