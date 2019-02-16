/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.config.scheduler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillState;
import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.interfaces.kill.KillService;
import com.zsl.common.interfaces.kill.KillTimeService;
import com.zsl.common.interfaces.management.KillGoodsService;
import com.zsl.web.intercepter.service.KillTimeServiceDo;
import com.zsl.web.utils.KillTimeUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.config.scheduler
 * @since 2019/2/14
 */
@Component
public class KillPreJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(KillPreJob.class);

	@Reference(group = "management", version = "1.0.0")
	private KillTimeService killTimeService;

	@Reference(group = "management", version = "1.0.0")
	private KillGoodsService killGoodsService;

	@Reference(group = "yien_mall_kill", version = "1.0.0")
	private KillService killService;

	@Autowired
	private KillTimeServiceDo killTimeServiceDo;


	@Override public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行定时任务预热");
		try {
			KillTime killTime = KillTimeUtil.getFirstFormList(killTimeService.selectKillTimeByState(KillTimeState.WK));
			//将对应批次的秒杀商品数据加入到Redis中
			killService.addKillGoodsToRedis(killTime.getKillTime());
			//此次执行完毕以后，需要将数据进行修改
			killTime.setState(KillTimeState.UK);
			killTimeService.updateKillTime(killTime);
			//修改秒杀商品的状态信息
			killService.updateKillGoodsToRDBMS(killTime.getKillTime(), KillState.UK);
		} catch (RuntimeException ex) {
			logger.info("定时任务预热出错");
		}
	}
}
