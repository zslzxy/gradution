package com.zsl.common.interfaces.search;

import com.zsl.common.entity.Goods;
import com.zsl.common.utils.Msg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/01
 * 作用：
 */
public interface GoodsSearchService {

    /**
     * 根据商品名称、商品类型id、商品对应的商家id查询对应的商品数据
     * @param goodsName
     * @param goodsTypeId
     * @param goodsMerchantsId
     * @return
     */
    List<Goods> findByGoodsNameAndGoodsTypeIdAndGoodsMerchantsId(String goodsName, Integer goodsTypeId, Integer goodsMerchantsId);

    /**
     * 根据商品名称、商品对应的商家id查询对应的商品数据
     * @param goodsName
     * @param goodsMerchantsId
     * @return
     */
    List<Goods> findByGoodsNameAndGoodsMerchantsId(String goodsName, Integer goodsMerchantsId);

    /**
     * 查询所有的商品
     * @return
     */
    Iterable<Goods> selectAllGoods();

    /**
     * 新增所有商品
     * @return
     */
    Msg insertAllGoodsToES();

    /**
     * 删除所有商品
     * @return
     */
    Msg deleteAllGoodsFromES();

    /**
     * 根据商品名称查询商品
     * @param goodsName
     * @return
     */
    List<Goods> findByGoodsName(String goodsName);

    /**
     * 根据商品现价进行range查询
     * @param field : 字段名称
     * @param fromAmount : 价格的最低范围
     * @param toAmount : 价格的最搞范围
     * @return
     */
    Iterable<Goods> findByGoodsNowMoney(String field, double fromAmount, double toAmount);

    /**
     * 根据商品的名称和总体描述，查询需要的商品
     * @param goodsName
     * @param goodsDesc
     * @return
     */
    Iterable<Goods> findByGoodsNameAndGoodsDesc(String goodsName, String goodsDesc);

    /**
     * 根据商品的唯一索引 goodsId 删除对应的商品
     * @param goodsId
     */
    void deleteByGoodsId(String goodsId);

    /**
     * 根据商品的id，来进行商品的局部修改操作
     * @param goodsId
     */
    void updateByGoodsId(String goodsId);

    /**
     * 根据商品的名称、商品的类型、商品的总体描述查询对应的商品数据
     * @param goodsName
     * @param goodsTypeId
     * @param goodsDesc
     * @return
     */
    List<Goods> findByGoodsNameAndGoodsTypeIdAndGoodsDesc(String goodsName, Integer goodsTypeId, String goodsDesc);

    /**
     * 根据商品的名称以及商品类型查询对应的商品数据
     * @param goodsName
     * @param goodsTypeId
     * @return
     */
    List<Goods> findByGoodsNameAndGoodsTypeId(String goodsName, Integer goodsTypeId);

}
