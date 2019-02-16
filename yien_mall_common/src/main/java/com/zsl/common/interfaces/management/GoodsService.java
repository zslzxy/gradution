/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.interfaces.management;

import com.github.pagehelper.PageInfo;
import com.zsl.common.entity.Goods;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 * @Filename GoodsService.java
 *
 * @Description 商品管理的接口
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
public interface GoodsService {

	/**
	 * 查询到所有的商品数据
	 */
	PageInfo<Goods> selectAllGoodsByPage(Integer pageNum, Integer pageSize);

	/**
	 * 查询到所有的商品数据
	 */
	List<Goods> selectAllGoods();

	/**
	 * 根据 商品的类型查询到所有的 同类型的商品的信息
	 * @param goodsTypeId ： 商品的类型的唯一索引
	 * @return
	 */
	PageInfo<Goods> selectAllGoodsByGoodsTypeIdAndPage(Integer goodsTypeId,Integer pageNum,Integer pageSize);

	/**
	 * 根据 商品的类型查询到所有的 同类型的商品的信息
	 * @param goodsTypeId ： 商品的类型的唯一索引
	 * @return
	 */
	List<Goods> selectAllGoodsByGoodsTypeId(Integer goodsTypeId);

	/**
	 * 根据商品的 唯一索引 查询到对应的商品的 一条数据
	 * @param GoodsId : 商品的唯一索引
	 * @return
	 */
	Goods selectGoodsByGoodsId(String GoodsId);

	/**
	 * 根据商品的 唯一索引 更新到对应的商品的 一条数据
	 * @param goods : 商品的对应的信息
	 * @return
	 */
	Integer updateGoodsByGoodsId(Goods goods);

	/**
	 * 根据商品的 唯一索引 删除对应的商品的 一条数据
	 * @param goodsId : 商品的唯一索引
	 * @return
	 */
	Integer deleteGoodsByGoodsId(String goodsId);

	/**
	 * 根据商品的商家id查询对应的商品
	 * @param merchantsId
	 * @return
	 */
    List<Goods> selectGoodsByMerchantsId(Integer merchantsId);
}
