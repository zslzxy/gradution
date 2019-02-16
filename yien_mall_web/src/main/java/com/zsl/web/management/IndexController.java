/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zsl.common.entity.Goods;
import com.zsl.common.interfaces.management.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.management
 * @since 2019/1/4
 */
@Controller
public class IndexController {

	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String toIndex() {
		return "redirect:/index";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String index() {
		return "management/index";
	}

}
