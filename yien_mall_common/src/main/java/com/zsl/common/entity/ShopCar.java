package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ${张世林}
 * @date 2019/01/13
 * 作用：购物车
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCar implements Serializable {

    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 购物车记录的唯一索引
     */
    private String ShopCarId;

    /**
     * 购物车对应的用户的id
     */
    private String ShopCarUserId;

    /**
     * 购物车对应的商品的id
     */
    private String ShopCarGoodsid;

    /**
     * 购物车对应的商品的name
     */
    private String ShopCarGoodsName;

    /**
     * 购物车对应的商品的价格
     */
    private BigDecimal ShopCarGoodsPrice;

    /**
     * 商品的图片地址
     */
    private String shopCarGoodsimage;

    /**
     * 新增时间
     */
    private Date rawAddTime;

    /**
     * 修改时间
     */
    private Date rawUpdateTime;

}
