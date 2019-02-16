package com.zsl.common.interfaces.kill;

import com.zsl.common.entity.Goods;
import com.zsl.common.entity.KillGoods;
import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillState;
import com.zsl.common.utils.Msg;

import java.util.Date;
import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/01
 * 作用：秒杀系统的接口
 */
public interface KillService {

    /**
     * 将对应秒杀商品的数据添加到Redis中
     * @param killTime
     */
    List<KillGoods> addKillGoodsToRedis(Date killTime);

    /**
     * 从秒杀中获取对应的秒杀商品数据
     * @return
     */
    List<KillGoods> selectKillGoods();

    /**
     * 修改秒杀商品的数据状态
     * @param killTime
     * @return
     */
    List<KillGoods> updateKillGoodsToRDBMS(Date killTime, KillState killState);

    /**
     * 查询对应的秒杀商品是够还有库存
     * @param goodsId
     * @return
     */
    Msg findKillGoodsNum(String goodsId);
}
