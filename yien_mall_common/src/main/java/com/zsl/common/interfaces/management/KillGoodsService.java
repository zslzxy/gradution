package com.zsl.common.interfaces.management;

import com.zsl.common.entity.KillGoods;
import com.zsl.common.entity.myenum.KillState;
import com.zsl.common.utils.Msg;

import java.util.Date;
import java.util.List;

/**
 * 秒杀商品管理的service接口
 */
public interface KillGoodsService {

    List<KillGoods> selectAll();

    List<KillGoods> selectByState(KillState killState);

    Integer updateKillGoods(KillGoods killGoods);

    Integer insertOne(KillGoods killGoods);

    /**
     * 根据秒杀时间查询对应的秒杀商品数据
     * @param killTime
     * @return
     */
    List<KillGoods> selectByKillTime(String killTime);

}
