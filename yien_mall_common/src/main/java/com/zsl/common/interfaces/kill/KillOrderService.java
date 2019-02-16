package com.zsl.common.interfaces.kill;

import com.zsl.common.entity.UserTrans;
import com.zsl.common.utils.Msg;

import java.util.List;
import java.util.Map;

/**
 * @author ${张世林}
 * @date 2019/01/01
 * 作用：
 */
public interface KillOrderService {

	/**
	 * 传入数据，进行商品库存的匹配操作
	 *
	 * @param userId
	 * @param goodsId
	 * @param addressId
	 * @param couriesType
	 * @param goodsNum
	 * @param killGoodsAmount
	 * @return
	 */
	Msg matchKillOrder(String userId, String goodsId, String addressId, String couriesType, Integer goodsNum,
					   String killGoodsAmount);

	/**
	 * 清空用户不付款的数据，并还原秒杀商品数据
	 *
	 * @param orderId
	 * @param goodsId
	 * @return
	 */
	Msg returnKillInfo(String orderId, String goodsId, Integer goodsNum);

}

