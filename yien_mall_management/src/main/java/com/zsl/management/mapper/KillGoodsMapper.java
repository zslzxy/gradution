package com.zsl.management.mapper;

import com.zsl.common.entity.KillGoods;
import com.zsl.common.entity.myenum.KillState;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 秒杀商品管理的mapper
 */
@Repository
public interface KillGoodsMapper {

    List<KillGoods> selectAll();

    List<KillGoods> selectByState(KillState killState);

    Integer updateKillGoods(KillGoods killGoods);

    Integer insertOne(KillGoods killGoods);

    List<KillGoods> selectByKillTime(String killTime);
}
