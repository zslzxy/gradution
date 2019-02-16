package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodsType {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 商品的类型的id
     */
    private String goodsTypeId;

    /**
     * 商品的类型的name
     */
    private String goodsTypeName;

    /**
     * 商品的类型的  描述
     */
    private String goodsTypeDesc;

    /**
     * 新增时间
     */
    private Date rawAddTime;

    /**
     * 修改时间
     */
    private Date rawUpdateTime;

}