/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.intercepter.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.Goods;
import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.interfaces.kill.KillTimeService;
import com.zsl.common.interfaces.management.GoodsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.intercepter.service
 * @since 2019/2/13
 */
@Component
public class KillTimeServiceDo {

	@Reference(group = "management", version = "1.0.0")
	private KillTimeService killTimeService;
	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	public Boolean validationKillOpen() {
		List<KillTime> list = killTimeService.selectKillTimeByState(KillTimeState.UK);
		if (list.size() == 0) {
			return false;
		}
		return true;
	}

	public void test() {
		List<Goods> list = goodsService.selectAllGoods();
		System.out.println("集合的大小为："+list.size());
	}
}
