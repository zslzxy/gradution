package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author ${张世林}
 * @date 2019/01/11
 * 作用：用户银行卡
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bank implements Serializable {

    /**
     * ID号
     */
    private Integer id;

    /**
     * bank的唯一索引
     */
    private String bankId;

    /**
     * 银行账号
     */
    private String bankCardNo;

    /**
     * 银行账户类型
     */
    private String bankCardType;

    /**
     * 银行中文名称
     */
    private String bankZhName;

    /**
     * 银行英文名称
     */
    private String bankEnName;

    /**
     * 银行预留手机号
     */
    private BigInteger bankCardMobile;

    /**
     * 对应的用户
     */
    private String bankUserId;

    private Date rawAddTime;

    private Date rawUpdateTime;

}
