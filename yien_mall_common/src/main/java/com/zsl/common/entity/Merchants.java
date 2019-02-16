package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchants implements Serializable {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 商家表的  唯一索引
     */
    private Integer merchantsId;

    /**
     * 商家信息对应的用户表的id
     */
    private String merchantsUserId;

    /**
     * 商家表的  店铺名称
     */
    private String merchantsStoreName;

    /**
     * 商家表的  店铺地址
     */
    private String merchantsStorePlace;

    /**
     * 商家表的  店铺拥有者名称
     */
    private String merchantsAuthorName;

    /**
     * 商家表的  店铺拥有者电话
     */
    private BigInteger merchantsAuthorMobile;

    /**
     * 商家表的  店铺拥有者邮箱
     */
    private String merchantsAuthorEmail;

    /**
     * 商家表的  店铺创建的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date merchantsCreateTime;

    private Date rawAddTime;

    private Date rawUpdateTime;

}