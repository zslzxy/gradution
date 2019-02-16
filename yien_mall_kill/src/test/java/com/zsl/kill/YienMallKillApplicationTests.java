package com.zsl.kill;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.zsl.common.entity.KillGoods;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.management.KillGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YienMallKillApplicationTests {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Reference(group = "management", version = "1.0.0")
    private KillGoodsService killGoodsService;

    @Autowired
    private Gson gson;

    @Test
    public void contextLoads() {
//        List<KillGoods> list = (List<KillGoods>) redisTemplate.opsForList().leftPop("killGoods");
//        for (KillGoods killGoods : list) {
//            System.out.println(killGoods);
//        }
        Object killGoods = redisTemplate.opsForList().leftPop("killGoods");
        System.out.println(killGoods);

    }


    @Test
    public void test1() {
        List<KillGoods> list = killGoodsService.selectAll();
        ArrayList<String> list1 = new ArrayList<>();
        for (KillGoods goods : list) {
            System.out.println(goods);
            String s = gson.toJson(goods);
            list1.add(s);
            redisTemplate.opsForList().leftPush("killGoods",goods);
        }
    }

    @Test
    public void test2() {
        List<KillGoods> list = (List<KillGoods>) redisTemplate.opsForList().range("killGoods",0 , -1);
        for (KillGoods killGoods : list) {
            System.out.println(killGoods);
        }
    }

    @Test
    public void test3() {
        List<KillGoods> list = killGoodsService.selectByKillTime("2019-02-06 17:35:00");
        for (KillGoods killGoods : list) {
            redisTemplate.opsForList().leftPush("list",killGoods);
        }
        redisTemplate.expire("list", 2, TimeUnit.MINUTES);
    }

    @Test
    public void test4() {
        List<KillGoods> killGoods = (List<KillGoods>) redisTemplate.opsForList().range("list",0,-1);
        for (KillGoods killGood : killGoods) {
            System.out.println(killGood);
        }
    }
}

