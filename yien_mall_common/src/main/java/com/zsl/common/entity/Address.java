package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author ${张世林}
 * @date 2019/01/11
 * 作用：用户地址
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address implements Serializable {

    /**
     * 地址表中的id
     */
    private Integer id;

    /**
     * 地址中的唯一索引
     */
    private String addressId;

    /**
     * 收货地址
     */
    private String addressPlace;

    /**
     * 收货人联系电话
     */
    private BigInteger addressMobile;

    /**
     * 收货人姓名
     */
    private String addressName;

    /**
     * 用户表中的用户唯一索引
     */
    private String addressUserId;

    private Date rawAddTime;

    private Date rawUpdateTime;

}
