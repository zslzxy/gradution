 /* www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Collections2;
import com.zsl.common.entity.Goods;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.management.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 *
 * @Filename GoodsServiceImpl.java
 *
 * @Description 商品管理的接口的实现类
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月16日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Component("goodsService")
@Service(group = "management",version = "1.0.0", timeout = 8000)
@CacheConfig(cacheManager = "cacheManager")
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	/**
	 * 查询到所有的商品
	 * @return
	 */
	@Override public PageInfo<Goods> selectAllGoodsByPage(Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Goods> goods = goodsMapper.selectAllGoods();
		Collections.shuffle(goods);
		PageInfo page = new PageInfo(goods,8);

		for (Goods good : goods) {
			System.out.println(good.getId());
		}

		return page;
	}

	/**
	 * 查询到所有的商品
	 * @return
	 */
	@Override public List<Goods> selectAllGoods() {
		List<Goods> goods = goodsMapper.selectAllGoods();
		return goods;
	}

	/**
	 * 查询到所有的商品
	 * @return
	 */
	@Override public List<Goods> selectAllGoodsByGoodsTypeId(Integer goodsTypeId) {
		List<Goods> goods = goodsMapper.selectAllGoodsByGoodsTypeId(goodsTypeId);
		return goods;
	}

	/**
	 * 根据类型查询到对应的商品的集合
	 * @param goodsTypeId ： 商品的类型的唯一索引
	 * @return
	 */
	@Override public PageInfo<Goods> selectAllGoodsByGoodsTypeIdAndPage(Integer goodsTypeId,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Goods> goods = goodsMapper.selectAllGoodsByGoodsTypeId(goodsTypeId);
		PageInfo page = new PageInfo(goods,8);

		return page;
	}

	/**
	 * genuine商品的id查询对应的商品的数据
	 * @param GoodsId : 商品的唯一索引
	 * @return
	 */
	@Cacheable(cacheNames = "goods")
	@Override public Goods selectGoodsByGoodsId(String GoodsId) {
		Goods goods = goodsMapper.selectGoodsByGoodsId(GoodsId);
		return goods;
	}

	/**
	 * 更新商品的信息
	 * @param goods : 商品的对应的信息
	 * @return
	 */
	@CachePut(value = "goods",key = "#goods.goodsId")
	@Override public Integer updateGoodsByGoodsId(Goods goods) {
		Integer num = goodsMapper.updateGoodsByGoodsId(goods);
		return num;
	}

	/**
	 * 删除对应的商品的信息
	 * @param goodsId : 商品的唯一索引
	 * @return
	 */
	@Override public Integer deleteGoodsByGoodsId(String goodsId) {
		Integer num = goodsMapper.deleteGoodsByGoodsId(goodsId);
		return num;
	}

	@Override
	public List<Goods> selectGoodsByMerchantsId(Integer merchantsId) {
		List<Goods> list = goodsMapper.selectGoodsByMerchantsId(merchantsId);
		return list;
	}
}
