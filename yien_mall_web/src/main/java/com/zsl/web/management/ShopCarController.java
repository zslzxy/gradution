package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.ShopCar;
import com.zsl.common.interfaces.management.ShopCarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/13
 * 作用：购物车的controller
 */
@Controller
@RequestMapping("/shopCar")
public class ShopCarController {

	@Reference(group = "management", version = "1.0.0")
	private ShopCarService shopCarService;

	@RequestMapping("/toShopCar")
	public String toShopCar(@RequestParam(name = "userId", defaultValue = "-1") String userId, ModelMap modelMap) {
		List<ShopCar> list = shopCarService.selectShopCarByUserId(userId);
		modelMap.addAttribute("list", list);
		return "management/shopCar";
	}

}
