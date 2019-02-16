package com.zsl.kill.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.zsl.common.entity.KillGoods;
import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillState;
import com.zsl.common.interfaces.kill.KillService;
import com.zsl.common.interfaces.kill.KillTimeService;
import com.zsl.common.interfaces.management.KillGoodsService;
import com.zsl.common.utils.Msg;
import com.zsl.kill.config.MyRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ${张世林}
 * @date 2019/01/01
 * 作用：
 */
@Component(value = "killService")
@Service(group = "yien_mall_kill", version = "1.0.0", timeout = 8000)
public class KillServiceImpl implements KillService {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private MyRunnable runnable;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("redisObjTemplate")
    private RedisTemplate redisObjTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Gson gson;

    @Reference(group = "management", version = "1.0.0")
    private KillGoodsService killGoodsService;

    @Reference(group = "management",version = "1.0.0")
    private KillTimeService killTimeService;

    /**
     * 将秒杀商品添加到Redis中
     * @param killTime
     */
    @Override
    public List<KillGoods> addKillGoodsToRedis(Date killTime) {
        KillTime bean = killTimeService.selectKillTimeByKillTime(killTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(killTime);
        List<KillGoods> list = killGoodsService.selectByKillTime(format);
        for (KillGoods killGoods : list) {
            redisTemplate.opsForList().leftPush("killGoods",killGoods);
            redisObjTemplate.opsForValue().set(killGoods.getKillGoodsId(),killGoods.getKillNum());
            redisTemplate.expireAt(format, bean.getEndTime());
            redisObjTemplate.expireAt(format, bean.getEndTime());
        }
        return list;
    }

    /**
     * 查询对应的秒杀商品是否还有库存
     * @param goodsId
     * @return
     */
    @Override public Msg findKillGoodsNum(String goodsId) {
        Integer integer = (Integer) redisObjTemplate.opsForValue().get(goodsId);
        if (integer > 0 ) {
            return Msg.success().addInfo("库存足");
        }
        return Msg.fail().addInfo("商品已售完");
    }

    /**
     * 修改数据库秒杀商品的状态
     * @param killTime
     */
    @Override
    public List<KillGoods> updateKillGoodsToRDBMS(Date killTime, KillState killState) {
//        KillTime bean = killTimeService.selectKillTimeByKillTime(killTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(killTime);
        List<KillGoods> list = killGoodsService.selectByKillTime(format);
        for (KillGoods killGoods : list) {
            killGoods.setKillGoodsState(killState);
            killGoodsService.updateKillGoods(killGoods);
        }
        return list;
    }

    /**
     * 从缓存中获取对应的秒杀商品数据
     * @return
     */
    @Override
    public List<KillGoods> selectKillGoods() {
        List<KillGoods> list = (List<KillGoods>) redisTemplate.opsForList().range("killGoods",0 , -1);
        for (KillGoods killGoods : list) {
            System.out.println(killGoods);
        }
        return list;
    }

}


