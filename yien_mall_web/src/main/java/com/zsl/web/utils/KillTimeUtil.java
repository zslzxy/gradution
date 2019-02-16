/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.utils;

import com.zsl.common.entity.KillTime;

import java.util.Collections;
import java.util.List;

import static com.zsl.web.utils.CronDateUtils.getCron;

/**
 *
 * @Filename KillTimeUtil.java
 *
 * @Description 秒杀时间的管理
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年02月12日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class KillTimeUtil {

	public static KillTime getFirstFormList(List<KillTime> list) {
		KillTime killTime;
		try {
			Collections.sort(list);
			killTime = list.get(0);// 从数据库查询出来的
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
		return killTime;
	}

}
