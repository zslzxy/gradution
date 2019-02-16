package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.KillGoods;
import com.zsl.common.entity.myenum.KillState;
import com.zsl.common.interfaces.management.KillGoodsService;
import com.zsl.common.utils.Msg;
import com.zsl.management.mapper.KillGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/27
 * 作用：
 */
@Component
@Service(group = "management", version = "1.0.0",timeout = 8000)
public class KillGoodsServiceImpl implements KillGoodsService {

    @Autowired
    private KillGoodsMapper killGoodsMapper;

    @Override
    public List<KillGoods> selectAll() {
        List<KillGoods> killGoods = killGoodsMapper.selectAll();
        return killGoods;
    }

    @Override
    public List<KillGoods> selectByState(KillState killState) {
        List<KillGoods> killGoods = killGoodsMapper.selectByState(killState);
        return killGoods;
    }

    @Override
    public Integer updateKillGoods(KillGoods killGoods) {
        Integer integer = killGoodsMapper.updateKillGoods(killGoods);
        return integer;
    }

    @Override
    public Integer insertOne(KillGoods killGoods) {
        Integer integer = killGoodsMapper.insertOne(killGoods);
        return integer;
    }

    @Override
    public List<KillGoods> selectByKillTime(String killTime) {

        return killGoodsMapper.selectByKillTime(killTime);
    }
}
