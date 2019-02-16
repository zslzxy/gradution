/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.management.mapper;

import com.zsl.common.entity.Merchants;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @Filename MerchantsMapper.java
 *
 * @Description 商家管理mapper接口
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月23日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Repository
public interface MerchantsMapper {

	/**
	 * 新增一个商户数据
	 * @param merchants
	 * @return
	 */
	Integer insertOne(Merchants merchants);

	Merchants selectOne(Integer merchantsId);

	List<Merchants> selectAll();

	List<Merchants> selectByUserId(String merchantsUserId);

	Integer delectBatch(Integer[] merchantsIds);

	Integer updateOne(Merchants merchants);

}
