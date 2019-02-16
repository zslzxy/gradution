package com.zsl.common.interfaces.order;

import com.zsl.common.entity.UserTrans;
import com.zsl.common.utils.Msg;

import java.util.List;
import java.util.Map;

/**
 * @author ${张世林}
 * @date 2019/01/01
 * 作用：
 */
public interface OrderService {

    /**
     * 创建订单
     * @param userId
     * @param goodsId
     * @param addressId
     * @param couriesType
     * @param goodsNum : 商品的数量
     * @return
     */
    Msg createOrder(String userId, String goodsId, String addressId, String couriesType,Integer goodsNum);

    /**
     * 查询到对应的订单的信息，然后依据  订单状态进行 map 分组
     * @param userId
     * @return
     */
    Map<String,List<UserTrans>> selectOrderByUserId(String userId);

    /**
     * 创建秒杀商品的订单
     * @param userId
     * @param goodsId
     * @param addressId
     * @param couriesType
     * @param goodsNum
     * @param killGoodsAmount
     * @return
     */
    Msg createKillOrder(String userId, String goodsId, String addressId, String couriesType, Integer goodsNum, String killGoodsAmount);
}
