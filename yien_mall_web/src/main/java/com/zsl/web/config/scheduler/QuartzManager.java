package com.zsl.web.config.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzManager {

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * @param jobName          任务名
	 * @param jobGroupName     任务组名
	 * @param triggerName      触发器名
	 * @param triggerGroupName 触发器组名
	 * @param jobClass         任务
	 * @param cron             时间设置，参考quartz说明文档
	 * @Description: 添加一个定时任务
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addJob(String jobName, String jobGroupName,
							  String triggerName, String triggerGroupName, Class jobClass, String cron) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

			// 触发器
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			// 触发器名,触发器组
			triggerBuilder.withIdentity(triggerName, triggerGroupName);
			triggerBuilder.startNow();
			// 触发器时间设定
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
			// 创建Trigger对象
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();

			// 调度容器设置JobDetail和Trigger
			sched.scheduleJob(jobDetail, trigger);

			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 新建一个适合自己场景的 添加 Job的方法
	 *
	 * @param jobClassPre
	 * @param jobClassEnd
	 * @param preCron
	 * @param endCron
	 */
	public void addJob(Class jobClassPre, Class jobClassEnd, String preCron, String endCron,
					   String jobPreName, String jobEndName, String jobGroupName,
					   String triggerPreName, String triggerEndName, String triggerGroupName) {
		try {
			Scheduler sched = schedulerFactoryBean.getScheduler();
			// 任务名，任务组，任务执行类
			JobDetail jobDetailPre = JobBuilder.newJob(jobClassPre).withIdentity(jobPreName, jobGroupName).build();
			JobDetail jobDetailEnd = JobBuilder.newJob(jobClassEnd).withIdentity(jobEndName, jobGroupName).build();

			// 触发器  --- Pre
			TriggerBuilder<Trigger> triggerBuilderPre = TriggerBuilder.newTrigger();
			triggerBuilderPre.withIdentity(triggerPreName, triggerGroupName);
			triggerBuilderPre.startNow();
			// 触发器时间设定
			triggerBuilderPre.withSchedule(CronScheduleBuilder.cronSchedule(preCron));
			// 创建Trigger对象
			CronTrigger trigger = (CronTrigger) triggerBuilderPre.build();
			// 调度容器设置JobDetail和Trigger
			sched.scheduleJob(jobDetailPre, trigger);

			// 触发器  --- End
			TriggerBuilder<Trigger> triggerBuilderEnd = TriggerBuilder.newTrigger();
			triggerBuilderEnd.withIdentity(triggerEndName, triggerGroupName);
			triggerBuilderEnd.startNow();
			// 触发器时间设定
			triggerBuilderEnd.withSchedule(CronScheduleBuilder.cronSchedule(endCron));
			// 创建Trigger对象
			CronTrigger triggerEnd = (CronTrigger) triggerBuilderEnd.build();
			sched.scheduleJob(jobDetailEnd, triggerEnd);

			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName      触发器名
	 * @param triggerGroupName 触发器组名
	 * @param cron             时间设置，参考quartz说明文档
	 * @Description: 修改一个任务的触发时间
	 */
	public static void modifyJobTime(String jobName,
									 String jobGroupName, String triggerName, String triggerGroupName, String cron) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}

			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(cron)) {
				/** 方式一 ：调用 rescheduleJob 开始 */
				// 触发器
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
				// 触发器名,触发器组
				triggerBuilder.withIdentity(triggerName, triggerGroupName);
				triggerBuilder.startNow();
				// 触发器时间设定
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
				// 创建Trigger对象
				trigger = (CronTrigger) triggerBuilder.build();
				// 方式一 ：修改一个任务的触发时间
				sched.rescheduleJob(triggerKey, trigger);
				/** 方式一 ：调用 rescheduleJob 结束 */

				/** 方式二：先删除，然后在创建一个新的Job  */
				//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
				//Class<? extends Job> jobClass = jobDetail.getJobClass();
				//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
				//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
				/** 方式二 ：先删除，然后在创建一个新的Job */
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @Description: 移除一个任务
	 */
	public static void removeJob(String jobName, String jobGroupName,
								 String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();

			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Filename QuartzManager.java
	 * @Description Spring容器管理的一个移除任务的Bean对象
	 * @Version 1.0
	 * @Author 张世林
	 * @Email 张世林@yiji.com
	 * @History <li>Author: 张世林</li>
	 * <li>Date: 2019年02月14日</li>
	 * <li>Version: 1.0</li>
	 * <li>Content: create</li>
	 */
	public void removeJob(String jobPreName, String jobEndName, String jobGroupName,
						  String triggerPreName, String triggerEndName, String triggerGroupName) {
		try {
			Scheduler sched = schedulerFactoryBean.getScheduler();
			TriggerKey triggerKeyPre = TriggerKey.triggerKey(triggerPreName, triggerGroupName);
			sched.pauseTrigger(triggerKeyPre);// 停止触发器
			sched.unscheduleJob(triggerKeyPre);// 移除触发器
			sched.deleteJob(JobKey.jobKey(jobPreName, jobGroupName));// 删除任务

			TriggerKey triggerKeyEnd = TriggerKey.triggerKey(triggerEndName, triggerGroupName);
			sched.pauseTrigger(triggerKeyEnd);// 停止触发器
			sched.unscheduleJob(triggerKeyEnd);// 移除触发器
			sched.deleteJob(JobKey.jobKey(jobEndName, jobGroupName));// 删除任务
		} catch (Exception e) {
			throw new RuntimeException("删除对应的任务失败："+e);
		}
	}

	/**
	 * @Description:启动所有定时任务
	 */
	public static void startJobs() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}