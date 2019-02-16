package com.zsl.common.entity.myenum;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Filename KillTimeState.java
 *
 * @Description 秒杀时间的状态
 *
 * @Version 1.0
 *
 * @Author 10838
 *
 * @Email 10838@yiji.com
 *
 * @History
 *<li>Author: 10838</li>
 *<li>Date: 2019年02月05日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public enum KillTimeState {

    UK("UK","正在秒杀"),
    WK("WK","等待秒杀"),
    HK("HK","已参与秒杀");

    
    /** 枚举值码 */
    private final String code;
    
    /** 枚举描述 */
    private final String msg;	
    
    /**
     * 构建一个 KillTimeState 。
     * @param code 枚举值码。
     * @param msg 枚举描述。
     */
    private KillTimeState (String code, String msg) {
    	this.code = code;
    	this.msg = msg;
    }
    
    /**
     * 得到枚举值码。
     * @return 枚举值码。
     */
    public String getCode() {
    	return code;
    }
    
    /**
     * 得到枚举描述。
     * @return 枚举描述。
     */
    public String getMsg() {
    	return msg;
    }
    
    /**
     * 得到枚举值码。
     * @return 枚举值码。
     */
    public String code() {
    	return code;
    }
    
    /**
     * 得到枚举描述。
     * @return 枚举描述。
     */
    public String msg() {
    	return msg;
    }
    
    /**
     * 通过枚举值码查找枚举值。
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
     */
    public static KillTimeState findKillTimeState(String code) {
    	for (KillTimeState killTimeState : values()) {
    		if (killTimeState.getCode().equals(code)) {
    			return killTimeState;
    		}
    	}
    	throw new IllegalArgumentException("ResultInfo Status not legal:" + code);
    }
    
    public static KillTimeState getByCode(String code) {
    	return findKillTimeState(code);
    }
    /**
     * 获取全部枚举值。
     * 
     * @return 全部枚举值。
     */
    public static List<KillTimeState> getAllKillTimeState() {
    	List<KillTimeState> list = new ArrayList<KillTimeState>();
    	for (KillTimeState killTimeState : values()) {
    		list.add(killTimeState);
    	}
    	return list;
    }
    
    /**
     * 获取全部枚举值码。
     * 
     * @return 全部枚举值码。
     */
    public static List<String> getAllKillTimeStateCode() {
    	List<String> list = new ArrayList<String>();
    	for (KillTimeState killTimeState : values()) {
    		list.add(killTimeState.code());
    	}
    	return list;
    }
    
    /**
     * 判断给定的枚举，是否在列表中
     * @param values 列表
     * @return
     */
    public boolean isInList (KillTimeState... values) {
    	for (KillTimeState killTimeState : values) {
    		if (this == killTimeState) {
    			return true;
    		}
    	}
    	return false;
    }

}
