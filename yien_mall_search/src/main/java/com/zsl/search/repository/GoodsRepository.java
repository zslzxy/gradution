/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.search.repository;

import com.zsl.common.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @Filename GoodsRepository.java
 *
 * @Description 商品信息的 ESRepository
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
public interface GoodsRepository extends ElasticsearchRepository<Goods,Integer> {
	/**
	 * 根据商品名称查询对应的商品的集合
	 * @param goodsName
	 * @return
	 */
	List<Goods> findByGoodsName(String goodsName);

	/**
	 * 根据商品名称、商品描述查询对应的商品信息
	 * @param goodsName : 商品的名称
	 * @param goodsDesc : 商品的描述
	 * @return
	 */
	List<Goods> findByGoodsNameAndGoodsDesc(String goodsName, String goodsDesc);

	/**
	 * 根据商品唯一索引 goodsId删除对应的文档
	 * @param goodsId : 商品的id
	 */
	void deleteByGoodsId(String goodsId);

	/**
	 * 根据商品的名称、商品类型id、商品的总体描述查询对应的商品数据
	 * @param goodsName ： 商品名称
	 * @param goodsTypeId ： 商品类型id
	 * @param goodsDesc ： 商品的总体描述
	 * @return ：商品对应的数据的集合
	 */
	List<Goods> findByGoodsNameAndGoodsTypeIdAndGoodsDesc(String goodsName, Integer goodsTypeId, String goodsDesc);

	/**
	 * 根据商品名称以及商品的类型id查询
	 * @param goodsName
	 * @param goodsTypeId
	 * @return
	 */
	List<Goods> findByGoodsNameAndGoodsTypeId(String goodsName, Integer goodsTypeId);

	/**
	 * 根据商品名称、商品类型id、商品对应的商家id查询对应的商品数据
	 * @param goodsName
	 * @param goodsTypeId
	 * @param goodsMerchantsId
	 * @return
	 */
	List<Goods> findByGoodsNameAndGoodsTypeIdAndGoodsMerchantsId(String goodsName, Integer goodsTypeId, Integer goodsMerchantsId);

	/**
	 * 根据商品名称、商品对应的商家id查询对应的商品数据
	 * @param goodsName
	 * @param goodsMerchantsId
	 * @return
	 */
	List<Goods> findByGoodsNameAndGoodsMerchantsId(String goodsName, Integer goodsMerchantsId);

}
