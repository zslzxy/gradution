/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.Merchants;
import com.zsl.common.interfaces.management.MerchantsService;
import com.zsl.management.mapper.MerchantsMapper;
import org.elasticsearch.search.aggregations.metrics.geobounds.InternalGeoBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.management.impl
 * @since 2019/1/23
 */
@Service(group = "management", version = "1.0.0",timeout = 8000)
@Component("merchantsService")
public class MerchantsServiceImpl implements MerchantsService {

	@Autowired
	private MerchantsMapper merchantsMapper;
	/**
	 * 新增一个商家数据
	 * @param merchants
	 * @return
	 */
	@Override public Integer insertOne(Merchants merchants) {
		Integer integer = null;
		try {
			integer = merchantsMapper.insertOne(merchants);
		}catch (DataAccessException ex){
			return 0;
		}
		return integer;
	}

	/**
	 * 修改商家信息
	 * @param merchants
	 * @return
	 */
	@Override
	public Integer updateOne(Merchants merchants) {
		Integer integer = merchantsMapper.updateOne(merchants);
		return integer;
	}

	/**
	 * 删除多个商家数据
	 * @param ids
	 * @return
	 */
	@Override
	public Integer deleteBatch(Integer[] ids) {
		Integer integer = merchantsMapper.delectBatch(ids);
		return integer;
	}

	/**
	 * 查询所有商家信息
	 * @return
	 */
	@Override
	public List<Merchants> selectAll() {
		return merchantsMapper.selectAll();
	}

	/**
	 * 根据userid 查询对应的商家信息
	 * @param userId
	 * @return
	 */
	@Override
	public List<Merchants> selectByUserId(String userId) {
		return merchantsMapper.selectByUserId(userId);
	}

	/**
	 * 根据商家id查询对应的商家的信息
	 * @param merchantsId
	 * @return
	 */
	@Override
	public Merchants selectOne(Integer merchantsId) {
		return merchantsMapper.selectOne(merchantsId);
	}


}
