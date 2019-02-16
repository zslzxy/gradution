/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.management.mapper;

import com.zsl.common.entity.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @Filename GoodsMapper.java
 *
 * @Description 商品的mapper
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
@Repository
public interface GoodsMapper {

	Integer insertGoods(Goods goods);

	Integer updateGoodsByGoodsId(Goods goods);

	Integer deleteGoodsByGoodsId(String goodsId);

	Goods selectGoodsByGoodsId(String goodsId);

	List<Goods> selectAllGoods();

	List<Goods> selectAllGoodsByGoodsTypeId(Integer goodsTypeId);

    List<Goods> selectGoodsByMerchantsId(Integer merchantsId);
}
