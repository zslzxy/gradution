/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zsl.common.entity.Goods;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.search.GoodsSearchService;
import com.zsl.common.utils.Msg;
import com.zsl.web.anno.Token;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.management
 * @since 2019/1/4
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	/**
	 * 查询所有的数据，根据分页的num
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectGoodsByPage")
	public PageInfo<Goods> selectGoodsByPage(
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "25") Integer pageSize,
			@RequestParam(name = "goodsType", defaultValue = "0") Integer goodsType) {
		PageInfo<Goods> pageInfo = null;
		if (goodsType >= 6 && goodsType <= 8) {
			pageInfo = goodsService.selectAllGoodsByGoodsTypeIdAndPage(goodsType, pageNum, pageSize);
			return pageInfo;
		}
		pageInfo = goodsService.selectAllGoodsByPage(pageNum, pageSize);
		return pageInfo;
	}

	@RequestMapping(value = { "/blank" }, method = RequestMethod.GET)
	public String blank() {
		return "management/blank";
	}

	@RequestMapping(value = { "/checkout" }, method = RequestMethod.GET)
	public String checkoout() {
		return "management/checkout";
	}

	/**
	 * 跳转到商品详情页面
	 *
	 * @param goodsId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/goodDetail/{goodsId}" })
	public String product(@PathVariable("goodsId") String goodsId, ModelMap modelMap) {
		Goods goods = goodsService.selectGoodsByGoodsId(goodsId);
		modelMap.addAttribute("goods", goods);
		return "management/goodDetail";
	}

	/**
	 * 跳转到商品购买页面
	 *
	 * @return
	 */
	@Token(save = true)
	@RequestMapping(value = { "/goodsBuy/{goodsId}" })
	public String goodsbuy(@PathVariable("goodsId") String goodsId, ModelMap modelMap) {
		Goods goods = goodsService.selectGoodsByGoodsId(goodsId);
		modelMap.addAttribute("goods", goods);
		return "management/goodsBuy";
	}

	/**
	 * 查询到对应的商品的信息
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goodsWindow")
	public Goods goodsWindow(@RequestParam(name = "goodsId", defaultValue = "0") String goodsId) {
		Goods goods = goodsService.selectGoodsByGoodsId(goodsId);
		return goods;
	}

	/**
	 * 更新商品信息
	 * @param goods
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateGoods")
	public Msg updateGoods(Goods goods) {
		Integer integer = goodsService.updateGoodsByGoodsId(goods);
		if (integer > 0) {
			return Msg.success().addInfo("修改数据成功");
		}
		return Msg.fail().addInfo("修改数据失败");
	}

	@ResponseBody
	@RequestMapping("/deleteGoodsByGoodsId")
	public Msg deleteGoodsByGoodsId(String goodsId) {
		Integer integer = goodsService.deleteGoodsByGoodsId(goodsId);
		if (integer == 1) {
			return Msg.success().addInfo("删除数据成功");
		}
		return Msg.fail().addInfo("删除商品失败");
	}

}
