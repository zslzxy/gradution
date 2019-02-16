package com.zsl.common.entity.myenum;

/**
 * @author ${张世林}
 * @date 2019/01/19
 * 作用： 快递的发货状态
 */
public enum KdStatusEnum {

    NotCourier("01","未发货"),
    HasCourier("02","已发货"),
    GetCourier("03","已收货"),
    RetCourier("04","已退货"),
    ExgCourier("05","待换货"),
    AlExgCourier("06","已换货");

    private String code;
    private String msg;

    private KdStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
