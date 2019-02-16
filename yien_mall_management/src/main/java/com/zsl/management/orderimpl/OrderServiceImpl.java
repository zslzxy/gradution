package com.zsl.management.orderimpl;

import com.zsl.common.entity.*;
import com.zsl.common.entity.myenum.CourierEnum;
import com.zsl.common.entity.myenum.PayEnum;
import com.zsl.common.interfaces.order.OrderService;
import com.zsl.common.utils.Msg;
import com.zsl.management.mapper.AddressMapper;
import com.zsl.management.mapper.GoodsMapper;
import com.zsl.management.mapper.OrderMapper;
import com.zsl.management.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author ${张世林}
 * @date 2019/01/19
 * 作用：订单管理的service
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(group = "order", version = "1.0.0")
public class OrderServiceImpl implements OrderService {

    /**
     * 自动注入userMapper
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 自动注入addressMapper
     */
    @Autowired
    private AddressMapper addressMapper;

    /**
     * 自动注入 GoodsMapper
     */
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 创建订单
     * @param userId
     * @param goodsId
     * @param addressId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,
                   isolation = Isolation.READ_COMMITTED,
                   rollbackFor = RuntimeException.class,
                   timeout = 10)
    @Override
    public Msg createOrder(String userId, String goodsId, String addressId,String couriesType,Integer goodsNum) {
        User user = userMapper.selectOneByUserId(userId);
        Goods goods = goodsMapper.selectGoodsByGoodsId(goodsId);
        Address address = addressMapper.selectAddressByAddressId(addressId);
        UserTrans userTrans = new UserTrans();

        //设置交易记录的唯一索引
        userTrans.setTransId(String.valueOf(System.nanoTime()));
        userTrans.setTransGoodsNum(goodsNum);
        userTrans.setTransUserName(user.getUserName());
        userTrans.setTransUserId(user.getUserId());
        userTrans.setTransAddressId(address.getAddressId());
        userTrans.setTransAddressPlace(address.getAddressPlace());
        userTrans.setTransMerchantsId(goods.getGoodsMerchantsId());
        userTrans.setTransGoodsId(goods.getGoodsId());
        userTrans.setTransGoodsName(goods.getGoodsName());
        //设置订单的金额
        userTrans.setTransGoodsAmount(goods.getGoodsNowAmount().multiply(new BigDecimal(goodsNum)));
        userTrans.setTransCreateTime(new Date());
        userTrans.setTransPayStatus(PayEnum.NotPay);
        userTrans.setTransDeliveryType(CourierEnum.valueOf(couriesType));
        userTrans.setRawAddTime(new Date());
        //剩下的先不填
        try {
            orderMapper.insertOne(userTrans);
        } catch (DataAccessException ex) {
            return Msg.fail().addInfo("订单创建失败").addMap("userTrans",userTrans);
        }
        return Msg.success().addMap("userTrans",userTrans).addInfo("订单创建成功");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = RuntimeException.class,
            timeout = 10)
    @Override
    public Msg createKillOrder(String userId, String goodsId, String addressId, String couriesType, Integer goodsNum,
                               String killGoodsAmount) {

        //验证是有还有足够的商品


        User user = userMapper.selectOneByUserId(userId);
        Goods goods = goodsMapper.selectGoodsByGoodsId(goodsId);
        Address address = addressMapper.selectAddressByAddressId(addressId);
        UserTrans userTrans = new UserTrans();

        //设置交易记录的唯一索引
        userTrans.setTransId(String.valueOf(System.nanoTime()));
        userTrans.setTransGoodsNum(goodsNum);
        userTrans.setTransUserName(user.getUserName());
        userTrans.setTransUserId(user.getUserId());
        userTrans.setTransAddressId(address.getAddressId());
        userTrans.setTransAddressPlace(address.getAddressPlace());
        userTrans.setTransMerchantsId(goods.getGoodsMerchantsId());
        userTrans.setTransGoodsId(goods.getGoodsId());
        userTrans.setTransGoodsName(goods.getGoodsName());
        //设置订单的金额
        userTrans.setTransGoodsAmount(new BigDecimal(killGoodsAmount).multiply(new BigDecimal(goodsNum)));
        userTrans.setTransCreateTime(new Date());
        userTrans.setTransPayStatus(PayEnum.NotPay);
        userTrans.setTransDeliveryType(CourierEnum.valueOf(couriesType));
        userTrans.setRawAddTime(new Date());
        //剩下的先不填
        try {
            orderMapper.insertOne(userTrans);
        } catch (DataAccessException ex) {
            return Msg.fail().addInfo("订单创建失败").addMap("userTrans",userTrans);
        }
        return Msg.success().addMap("userTrans",userTrans).addInfo("订单创建成功");
    }

    /**
     * 查询到对应的订单的信息，然后使用订单的状态对订单进行分组
     * @param userId
     * @return
     */
    @Override
    public Map<String,List<UserTrans>> selectOrderByUserId(String userId) {

        List<UserTrans> list = orderMapper.selectOrderByUserId(userId);
        Map<String,List<UserTrans>> map = new HashMap<>(10);
        map.put("list",list);
        for (UserTrans userTran : list) {
            if (map.containsKey(userTran.getTransPayStatus().toString())) {
                map.get(userTran.getTransPayStatus().toString()).add(userTran);
            } else {
                LinkedList<UserTrans> linkedList = new LinkedList<>();
                linkedList.add(userTran);
                map.put(userTran.getTransPayStatus().toString(),linkedList);
            }
        }

        for (UserTrans userTran : list) {
            if (userTran.getTransKdStatus() != null) {
                if (map.containsKey(userTran.getTransKdStatus().toString())) {
                    map.get(userTran.getTransPayStatus().toString()).add(userTran);
                } else {
                    LinkedList<UserTrans> linkedList = new LinkedList<>();
                    linkedList.add(userTran);
                    map.put(userTran.getTransKdStatus().toString(),linkedList);
                }
            }
        }
        return map;
    }
}
