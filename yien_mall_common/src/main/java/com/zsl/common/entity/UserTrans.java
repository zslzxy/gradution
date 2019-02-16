package com.zsl.common.entity;

import com.zsl.common.entity.myenum.CourierEnum;
import com.zsl.common.entity.myenum.KdStatusEnum;
import com.zsl.common.entity.myenum.PayEnum;
import com.zsl.common.entity.myenum.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTrans implements Serializable {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 交易记录表中的  唯一索引
     */
    private String transId;

    /**
     * 购买的商品的数量
     */
    private Integer transGoodsNum;

    /**
     * 交易记录表中的  用户的id
     */
    private String transUserId;

    /**
     * 交易记录表中的  用户的name
     */
    private String transUserName;

    /**
     * 交易记录表中的  地址的id
     */
    private String transAddressId;

    /**
     * 交易记录表中的  地址的place
     */
    private String transAddressPlace;

    /**
     * 交易记录表中的  商家的id
     */
    private Integer transMerchantsId;

    /**
     * 交易记录表中的  商品的id
     */
    private String transGoodsId;

    /**
     * 交易记录表中的  商品的name
     */
    private String transGoodsName;

    /**
     * 交易记录表中的  商品的购买价格
     */
    private BigDecimal transGoodsAmount;

    /**
     * 交易记录表中的  这条记录创建的时间
     */
    private Date transCreateTime;

    /**
     * 交易记录表中的  这条商品记录是否已经付款
     */
    private PayEnum transPayStatus;

    /**
     * 交易记录表中的  这条商品记录的付款时间
     */
    private Date transPayTime;

    /**
     * 交易记录表中的  这条商品记录的付款方式
     */
    private PayTypeEnum transPayType;

    /**
     * 这一条商品的物流状态
     */
    private KdStatusEnum transKdStatus;

    /**
     * 交易记录表中的  这条商品记录的发货时间
     */
    private Date transDeliveryTime;

    /**
     * 交易记录表中的  这条商品记录的运费
     */
    private BigDecimal transDeliveryAmount;

    /**
     * 交易记录表中的  这条商品记录的发货方式
     */
    private CourierEnum transDeliveryType;

    /**
     * 交易记录表中的  这条商品记录的收货时间
     */
    private Date transReceiveTime;

    /**
     * 新增时间
     */
    private Date rawAddTime;

    /**
     * 修改时间
     */
    private Date rawUpdateTime;

}