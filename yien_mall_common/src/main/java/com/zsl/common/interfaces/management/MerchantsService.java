/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.interfaces.management;

import com.zsl.common.entity.Merchants;

import java.util.List;

/**
 *
 * @Filename MerchantsService.java
 *
 * @Description 商户管理service接口
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
public interface MerchantsService {

	/**
	 * 新增一个商户
	 * @param merchants
	 * @return
	 */
	Integer insertOne(Merchants merchants);

	Integer updateOne(Merchants merchants);

	Integer deleteBatch(Integer[] ids);

	List<Merchants> selectAll();

	List<Merchants> selectByUserId(String userId);

	Merchants selectOne(Integer merchantsId);

}
