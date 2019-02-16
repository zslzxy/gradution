/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zsl.common.entity.*;
import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.interfaces.kill.KillTimeService;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.management.MerchantsService;
import com.zsl.common.interfaces.management.RegistService;
import com.zsl.common.interfaces.management.UserService;
import com.zsl.common.myenum.Level;
import com.zsl.common.myenum.State;
import com.zsl.common.utils.Msg;
import com.zsl.web.config.scheduler.KillEndJob;
import com.zsl.web.config.scheduler.KillPreJob;
import com.zsl.web.config.scheduler.QuartzManager;
import com.zsl.web.utils.CronDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.*;

/**
 * @Filename AdminManagementController.java
 * @Description : 管理员界面的controller
 * @Version 1.0
 * @Author 张世林
 * @Email 张世林@yiji.com
 * @History <li>Author: 张世林</li>
 * <li>Date: 2019年01月21日</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Controller
@RequestMapping("/admin")
public class AdminManagementController {

	@Autowired
	private Gson gson;

	@Reference(group = "management", version = "1.0.0")
	private KillTimeService killTimeService;

	@Autowired
	private QuartzManager quartzManager;

	@Reference(group = "management", version = "1.0.0")
	private RegistService registService;

	@Value("${management.admin.password}")
	private String yienAdminPassword;

	@RequestMapping("/toAdmin.html")
	public String toAdmin() {
		return "management/admin";
	}

	@Reference(group = "management", version = "1.0.0")
	private MerchantsService merchantsService;

	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	@Reference(group = "management", version = "1.0.0")
	private UserService userService;

	@Value("${kill.name.jobGroupName}")
	private String jobGroupName;

	@Value(("${kill.name.triggerGroupName}"))
	private String triggerGroupName;

	/**
	 * 添加管理员账号
	 *
	 * @param password
	 * @param adminId
	 * @param adminName
	 * @param adminPassword
	 * @param adminAddress
	 * @param adminCardNo
	 * @param adminMobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addAdmin.html")
	public Msg addAdmin(@RequestParam("password") String password,
						@RequestParam("adminId") String adminId,
						@RequestParam("adminName") String adminName,
						@RequestParam("adminPassword") String adminPassword,
						@RequestParam("adminAddress") String adminAddress,
						@RequestParam("adminCardNo") String adminCardNo,
						@RequestParam("adminMobile") BigInteger adminMobile) {
		if (!password.equals(yienAdminPassword)) {
			return Msg.fail().addInfo("新增管理员失败，管理管理员账号错误");
		}
		User user = new User();
		user.setUserId(adminId);
		user.setUserName(adminName);
		user.setUserPassword(adminPassword);
		user.setUserCardNo(adminCardNo);
		user.setUserAddress(adminAddress);
		user.setUserMobileNo(adminMobile);
		user.setUserLevel(Level.admin);
		user.setRawAddTime(new Date());
		user.setUserState(State.Cerdibility_Optimal);
		//将数据插入到数据库中
		Msg msg = registService.insertOne(user);

		return msg;
	}

	/**
	 * 创建商品的zTree对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createzTree.html")
	public String getAllGoods() {
		List<Goods> goods = goodsService.selectAllGoods();
		//创建商城
		Ztree ztreeMalls = new Ztree("-1", -3, "易恩商城", "", "");

		//创建商家节点
		Ztree ztreeMerchants = new Ztree("-2", -1, "商家", "", "");
		//创建商品节点
		Ztree ztreeGoods = new Ztree("0", -1, "商品","","");

		//创建商品节点下的分类
		Ztree ztreeBook = new Ztree("5001",0,"图书","","");
		Ztree ztreePaper = new Ztree("5002", 0, "纸巾","","");
		Ztree ztreeOrther = new Ztree("5003", 0, "百货","","");
		List<Ztree> ztrees = new LinkedList<Ztree>();
		ztrees.add(ztreeGoods);
		ztrees.add(ztreeMalls);
		ztrees.add(ztreeMerchants);
		ztrees.add(ztreeBook);
		ztrees.add(ztreePaper);
		ztrees.add(ztreeOrther);
		for (Goods good : goods) {
			Ztree ztree = new Ztree();
			ztree.setId(good.getGoodsId());
			ztree.setPId(0);
			if (good.getGoodsTypeId() == 6) {
				ztree.setPId(5001);
			} else if (good.getGoodsTypeId() == 7) {
				ztree.setPId(5002);
			} else {
				ztree.setPId(5003);
			}
			ztree.setName(good.getGoodsName());
			ztrees.add(ztree);
		}

		/**
		 * 将商家数据添加到集合中
		 */
		List<Merchants> merchants = merchantsService.selectAll();
		for (Merchants merchant : merchants) {
			Ztree ztree = new Ztree(merchant.getMerchantsId().toString(), -2, merchant.getMerchantsStoreName(), "", "");
			ztrees.add(ztree);
			for (Goods good : goods) {
				if (good.getGoodsMerchantsId().equals(merchant.getMerchantsId())) {
					Ztree ztree1 = new Ztree(good.getGoodsId(), merchant.getMerchantsId(), good.getGoodsName(), "", "");
					ztrees.add(ztree1);
				}
			}
		}

		String s = gson.toJson(ztrees);
		return s;
	}

	@ResponseBody
	@RequestMapping("/selectUserByPage.html")
	public Msg selectUserByPage(@RequestParam(name = "page", defaultValue = "1") Integer page) {
		PageInfo<User> pageInfo = userService.selectUserByPage(page);
		return Msg.success().addInfo("用户查询成功").addMap("pageInfo",pageInfo);
	}

	/**
	 * 重启秒杀定时任务队列，实现原理：
	 * 		将数据库中所有的秒杀时间为 WK的数据取出，将其添加到Quartz定时任务中
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/restartKillModel")
	public Msg restartKillModel() {
		List<KillTime> list = killTimeService.selectKillTimeByState(KillTimeState.WK);

		try {
			for (KillTime killTime : list) {
				quartzManager.addJob(KillPreJob.class, KillEndJob.class,
						CronDateUtils.getCron(killTime.getPreTime()), CronDateUtils.getCron(killTime.getEndTime()),
						killTime.getPreTime().toString(), killTime.getEndTime().toString(), jobGroupName,
						killTime.getPreTime().toString(), killTime.getEndTime().toString(), triggerGroupName);
			}
			return Msg.success().addInfo("重启秒杀队列成功");
		} catch (RuntimeException ex) {
			return Msg.fail().addInfo("重启秒杀队列失败");
		}
	}
}
