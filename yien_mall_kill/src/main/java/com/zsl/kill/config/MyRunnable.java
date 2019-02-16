package com.zsl.kill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.HOURS.sleep(1);
            clearKillGoodsCache();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空缓存
     */
    @CacheEvict(value = "killGoods")
    public void clearKillGoodsCache() {
    }
}