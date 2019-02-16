package com.zsl.common.entity.myenum;

/**
 * @author ${张世林}
 * @date 2019/01/19
 * 作用：付款方式
 */
public enum  PayTypeEnum {

    YiEnAccount("01","易恩账户"),

    ALiAccount("02","支付宝账户"),

    WeChatAccount("03","微信账户"),

    BankAccount("04","银行账户");

    private String code;

    private String msg;

    private PayTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
