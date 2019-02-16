package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.*;
import com.zsl.common.interfaces.kill.KillOrderService;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.management.UserService;
import com.zsl.common.interfaces.order.OrderService;
import com.zsl.common.interfaces.order.OrdinaryOrderService;
import com.zsl.common.interfaces.search.UserSearchService;
import com.zsl.common.utils.Msg;
import com.zsl.web.anno.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Guard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ${张世林}
 * @date 2019/01/19
 * 作用： 订单的controller
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Reference(group = "order", version = "1.0.0")
	private OrderService orderService;

	@Reference(group = "order", version = "1.0.0")
	private KillOrderService killOrderService;

	@Reference(group = "orindary", version = "1.0.0")
	private OrdinaryOrderService ordinaryOrderService;

	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	@Reference(group = "management", version = "1.0.0")
	private UserService userService;

	/**
	 * 创建订单，将数据传入到后台，进行订单的创建的操作
	 *
	 * @param addressId   ： 地址的唯一索引
	 * @param userId      ： 用户的唯一索引
	 * @param goodsId     ： 商品的唯一索引
	 * @param couriesType : 快递方式
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/createOrdinaryOrder")
	public Msg createOrder(@RequestParam("addressId") String addressId,
						   @RequestParam("userId") String userId,
						   @RequestParam("goodsId") String goodsId,
						   @RequestParam("couriesType") String couriesType,
						   @RequestParam(name = "goodsNum", defaultValue = "1") Integer goodsNum) {
		Msg msg = ordinaryOrderService.matchOrdinaryOrder(userId, goodsId, addressId, couriesType, goodsNum);
		return msg;
	}

	/**
	 * 创建订单，将数据传入到后台，进行订单的创建的操作
	 *
	 * @param addressId   ： 地址的唯一索引
	 * @param userId      ： 用户的唯一索引
	 * @param goodsId     ： 商品的唯一索引
	 * @param couriesType : 快递方式
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/createKillOrder")
	public Msg createKillOrder(@RequestParam("addressId") String addressId,
							   @RequestParam("userId") String userId,
							   @RequestParam("goodsId") String goodsId,
							   @RequestParam("couriesType") String couriesType,
							   @RequestParam(name = "goodsNum", defaultValue = "1") Integer goodsNum,
							   @RequestParam("killGoodsAmount") String killGoodsAmount) {
		Msg msg = killOrderService.matchKillOrder(userId, goodsId, addressId, couriesType, goodsNum,killGoodsAmount);
		return msg;
	}

	/**
	 * 如果用户不付款，则直接取消缓存，并将对应的秒杀商品库存还原
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/returnKillInfo")
	public Msg returnKillInfo(String orderId, String goodsId, Integer goodsNum) {
		Msg msg = killOrderService.returnKillInfo(orderId, goodsId, goodsNum);
		return msg;
	}

	/**
	 * 验证对应的未付款的订单是否已经过期了
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/validateOrder")
	public Msg validateOrder(String orderId) {
		Msg msg = ordinaryOrderService.validateOrder(orderId);
		return msg;
	}

	/**
	 * 用户取消订单，然后需要清空缓存
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clearOrder")
	public Msg clearOrder(String orderId) {
		Msg msg = ordinaryOrderService.clearOrder(orderId);
		return msg;
	}

	/**
	 * 获取到对应用户的所有订单数据
	 *
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectOrderByUserId/{userId}")
	public Msg selectOrderByUserId(@PathVariable("userId") String userId) {
		Map<String, List<UserTrans>> map = orderService.selectOrderByUserId(userId);
		return Msg.success().addInfo("订单数据").addMap("map", map);
	}

	/**
	 * 从Redis中加载出对应用户的待付款的商品
	 *
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectOrderByUserIdFromRedis/{userId}")
	public Msg selectOrderByUserIdFromRedis(@PathVariable("userId") String userId) {
		List<BuyDto> buyDtos = ordinaryOrderService.selectOrderByUserIdFromRedis(userId);
		for (BuyDto buyDto : buyDtos) {
			System.out.println("订单数据为："+buyDto);
		}
		List<WaitPayDto> list = new ArrayList<WaitPayDto>();
		for (BuyDto buyDto : buyDtos) {
			WaitPayDto waitPayDto = new WaitPayDto();
			waitPayDto.setAddressId(buyDto.getAddressId());
			waitPayDto.setAmount(buyDto.getAmount());
			waitPayDto.setBuyEnum(buyDto.getBuyEnum());
			waitPayDto.setCouriesType(buyDto.getCouriesType());
			waitPayDto.setCreateTime(buyDto.getCreateTime());
			waitPayDto.setGoodsId(buyDto.getGoodsId());
			waitPayDto.setGoodsNum(buyDto.getGoodsNum());
			waitPayDto.setOrderId(buyDto.getOrderId());
			waitPayDto.setUserId(buyDto.getUserId());

			Address address = userService.selectAddressByAddressId(buyDto.getAddressId());
			System.out.println("针对的地址为："+address);
			System.out.println("针对的地址为："+waitPayDto.getAddressId());
			waitPayDto.setAddress(address.getAddressPlace());
			waitPayDto.setAddressName(address.getAddressName());
			waitPayDto.setAddressMobile(address.getAddressMobile());
			Goods goods = goodsService.selectGoodsByGoodsId(buyDto.getGoodsId());
			waitPayDto.setGoodsName(goods.getGoodsName());
			list.add(waitPayDto);
		}
		return Msg.success().addInfo("订单数据").addMap("list",list);
	}
}
