/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.search.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.Goods;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.search.GoodsSearchService;
import com.zsl.common.utils.Msg;
import com.zsl.search.repository.GoodsRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @Filename GoodsSearchServiceImpl.java
 *
 * @Description 查询到所有的商品的数据
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月18日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Component
@Service(group = "search",version = "1.0.0")
public class GoodsSearchServiceImpl implements GoodsSearchService {

	private static final Logger logger = LoggerFactory.getLogger(GoodsSearchServiceImpl.class);

	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	@Autowired
	private GoodsRepository goodsRepository;

	@Override
	public List<Goods> findByGoodsNameAndGoodsTypeIdAndGoodsMerchantsId(String goodsName, Integer goodsTypeId, Integer goodsMerchantsId) {
		return goodsRepository.findByGoodsNameAndGoodsTypeIdAndGoodsMerchantsId(goodsName,goodsTypeId,goodsMerchantsId);
	}

	@Override
	public List<Goods> findByGoodsNameAndGoodsMerchantsId(String goodsName, Integer goodsMerchantsId) {
		return goodsRepository.findByGoodsNameAndGoodsMerchantsId(goodsName,goodsMerchantsId);
	}

	/**
	 * 查询所有的商品
	 * @return
	 */
	@Override public Iterable<Goods> selectAllGoods() {
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("goodsName", "中国古代");
		Iterable<Goods> search = goodsRepository.search(matchQueryBuilder);
		return search;
	}

	/**
	 * 添加所有数据添加到ES节点中
	 * @return
	 */
	@Override public Msg insertAllGoodsToES() {
		List<Goods> goods = goodsService.selectAllGoods();
		goodsRepository.saveAll(goods);
		return Msg.success().addInfo("数据导入ES成功");
	}

	/**
	 * 删除所有的数据
	 * @return
	 */
	@Override
	public Msg deleteAllGoodsFromES() {
		goodsRepository.deleteAll();
		return Msg.success().addInfo("全部删除成功");
	}

	/**
	 * 根据商品名称查询对应的商品的数据
	 * @param goodsName
	 * @return
	 */
	@Override public List<Goods> findByGoodsName(String goodsName) {
		List<Goods> byGoodsName = goodsRepository.findByGoodsName(goodsName);
		return byGoodsName;
	}

	/**
	 * 根据商品的金额进行范围匹配查询
	 * @return
	 */
	@Override public Iterable<Goods> findByGoodsNowMoney(String field, double fromAmount, double toAmount) {
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
		rangeQueryBuilder.from(fromAmount);
		rangeQueryBuilder.to(toAmount);
		Iterable<Goods> search = goodsRepository.search(rangeQueryBuilder);
		return search;
	}

	/**
	 * 根据商品名称和商品的总体描述查询对应的商品数据
	 * @param goodsName : 商品名称
	 * @param goodsDesc : 商品描述
	 * @return
	 */
	@Override public Iterable<Goods> findByGoodsNameAndGoodsDesc(String goodsName, String goodsDesc) {
		List<Goods> list = goodsRepository
				.findByGoodsNameAndGoodsDesc(goodsName, goodsDesc);
		return list;
	}

	/**
	 * 根据商品的唯一索引删除对应的商品的信息
	 * @param goodsId
	 */
	@Override public void deleteByGoodsId(String goodsId) {
		goodsRepository.deleteByGoodsId(goodsId);
	}

	/**
	 * 根据商品的id，来修改对应的商品的数据
	 * @param goodsId
	 */
	@Override public void updateByGoodsId(String goodsId) {
		//先查询对应的商品信息
		Goods goods = goodsService.selectGoodsByGoodsId(goodsId);
		//重新保存对应的商品数据即可实现全覆盖操作
		goodsRepository.save(goods);
	}

	/**
	 * 根据商品的名称、商品类型id、商品的总体描述查询对应的商品数据
	 * @param goodsName ： 商品名称
	 * @param goodsTypeId ： 商品类型id
	 * @param goodsDesc ： 商品的总体描述
	 * @return ：商品对应的数据的集合
	 */
	@Override public List<Goods> findByGoodsNameAndGoodsTypeIdAndGoodsDesc(String goodsName, Integer goodsTypeId,
																		   String goodsDesc) {
		List<Goods> list = goodsRepository
				.findByGoodsNameAndGoodsTypeIdAndGoodsDesc(goodsName, goodsTypeId, goodsDesc);
		return list;
	}

	/**
	 * 根据商品名称、商品类型id查询对应的商品的数据
	 * @param goodsName : 商品名称
	 * @param goodsTypeId : 商品类型id
	 * @return
	 */
	@Override public List<Goods> findByGoodsNameAndGoodsTypeId(String goodsName, Integer goodsTypeId) {
		List<Goods> list = goodsRepository
				.findByGoodsNameAndGoodsTypeId(goodsName, goodsTypeId);
		return list;
	}
}
