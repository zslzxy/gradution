/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.search.GoodsSearchService;
import com.zsl.common.utils.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Filename GoodsManagementController.java
 * @Description 管理员界面的商品管理controller
 * @Version 1.0
 * @Author 张世林
 * @Email 张世林@yiji.com
 * @History <li>Author: 张世林</li>
 * <li>Date: 2019年01月18日</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Controller
@RequestMapping("/admin")
public class GoodsManagementController {

	@Reference(version = "1.0.0", group = "search")
	private GoodsSearchService goodsSearchService;

	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	@ResponseBody
	@RequestMapping("/insertAllGoodsToES")
	public Msg insertAllGoodsToES() {
		Msg msg = goodsSearchService.insertAllGoodsToES();
		return msg;
	}

	@ResponseBody
	@RequestMapping("/deleteAllGoodsFromES")
	public Msg deleteAllGoodsFromES() {
		Msg msg = goodsSearchService.deleteAllGoodsFromES();
		return msg;
	}

	/**
	 * 删除商品信息，并删除ES中对应的商品数据
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteGoods")
	public Msg deleteGoodsByGoodsId(String goodsId) {
		goodsSearchService.deleteByGoodsId(goodsId);
		Integer integer = goodsService.deleteGoodsByGoodsId(goodsId);
		if (integer == 1) {
			return Msg.success().addInfo("删除成功");
		}
		return Msg.fail().addInfo("删除失败");
	}

}
