package com.zsl.kill.orderimpl;

import com.zsl.common.entity.BuyDto;
import com.zsl.common.entity.myenum.BuyEnum;
import com.zsl.common.entity.myenum.CourierEnum;
import com.zsl.common.interfaces.kill.KillOrderService;
import com.zsl.common.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ${张世林}
 * @date 2019/01/19
 * 作用：订单管理的service
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(group = "order", version = "1.0.0")
public class KillOrderServiceImpl implements KillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("redisBuyDtoTemplate")
    private RedisTemplate redisBuyDtoTemplate;

    @Autowired
    @Qualifier("redisObjTemplate")
    private RedisTemplate redisObjTemplate;

    /**
     * 清空用户不付款的数据，并还原秒杀商品的库存
     * @param orderId
     * @param goodsId
     * @return
     */
    @Override public Msg returnKillInfo(String orderId, String goodsId, Integer goodsNum) {
        //清空对应的秒杀对象
        redisBuyDtoTemplate.expire(orderId,1,TimeUnit.SECONDS);

        Integer integer = (Integer) redisObjTemplate.opsForValue().get(goodsId);
        //还原秒杀商品数量
        redisObjTemplate.opsForValue().set(goodsId, integer + goodsNum);
        return Msg.success().addInfo("订单取消成功");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = RuntimeException.class,
            timeout = 10)
    @Override
    public Msg matchKillOrder(String userId, String goodsId, String addressId, String couriesType, Integer goodsNum,
                               String killGoodsAmount) {

        //验证是有还有足够的商品
        Integer integer = (Integer) redisObjTemplate.opsForValue().get(goodsId);
        if (integer >= goodsNum) {
            BuyDto buyDto = new BuyDto();
            buyDto.setOrderId(String.valueOf(System.nanoTime()));
            buyDto.setUserId(userId);
            buyDto.setGoodsNum(goodsNum);
            buyDto.setGoodsId(goodsId);
            buyDto.setAddressId(addressId);
            buyDto.setCreateTime(new Date());
            buyDto.setCouriesType(CourierEnum.valueOf(couriesType));
            buyDto.setAmount(new BigDecimal(killGoodsAmount).multiply(new BigDecimal(goodsNum)));
            buyDto.setBuyEnum(BuyEnum.KILL);
            //修改商品数量
            redisObjTemplate.opsForValue().set(goodsId, integer - goodsNum);
            //将 buyDto加载到Redis中
            redisBuyDtoTemplate.opsForValue().set(buyDto.getOrderId(),buyDto);
            //设置五分钟失效
            redisBuyDtoTemplate.expire(buyDto.getOrderId(),5,TimeUnit.MINUTES);
            return Msg.success().addInfo("秒杀成功，请立即支付，否则将会失效").addMap("killDto", buyDto);
        }
        return Msg.fail().addInfo("秒杀失败，商品已经售完");
    }



}
