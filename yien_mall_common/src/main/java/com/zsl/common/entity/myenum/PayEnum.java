package com.zsl.common.entity.myenum;

import java.security.PrivateKey;

/**
 * @author ${张世林}
 * @date 2019/01/19
 * 作用：付款状态
 */
public enum PayEnum {

    NotPay("NotPay","未付款"),
    HasPay("HasPay","已付款");

    private String code;
    private String msg;

    private PayEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
