package com.zsl.common.entity.myenum;

import com.zsl.common.interfaces.kill.KillService;

/**
 * @author ${张世林}
 * @date 2019/01/27
 * 作用：秒杀表中的每句状态
 */
public enum KillState {

    NK("NK","等待秒杀"),
    HK("HK","已参与秒杀"),
    UK("UK","正在秒杀"),
    WK("WK","等待审核"),
    KK("KK","拒绝参加秒杀");

    private String code;

    private String msg;

    private KillState(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }


}
