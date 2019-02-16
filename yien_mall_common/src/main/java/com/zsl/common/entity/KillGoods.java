package com.zsl.common.entity;

import com.zsl.common.entity.myenum.KillState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @Filename KillGoods.java
 *
 * @Description 秒杀的商品表实体类
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月14日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KillGoods implements Serializable {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 商品秒杀表中的唯一索引
     */
    private String killId;

    /**
     * 商品表的唯一索引
     */
    private String killGoodsId;

    /**
     * 商品名称
     */
    private String killGoodsName;

    /**
     * 商品现在的价格
     */
    private BigDecimal killGoodsAmount;

    /**
     * 商品原价
     */
    private BigDecimal killDefaultAmount;

    /**
     * 秒杀商品的状态
     */
    private KillState killGoodsState;

    /**
     * 商品参加秒杀的时间（参加哪一个批次的秒杀活动）
     */
    private Date killGoodsTime;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 新增时间
     */
    private Date rawAddTime;

    /**
     * 修改时间
     */
    private Date rawUpdateTime;

    /**
     * 参与秒杀商品的数量
     */
    private Integer killNum;
}