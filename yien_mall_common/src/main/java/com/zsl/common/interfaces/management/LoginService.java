/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.common.interfaces.management;

import com.zsl.common.entity.User;

import java.math.BigInteger;

/**
 * 登录的service接口
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.common.execinterface.management
 * @since 2019/1/2
 */
public interface LoginService {

	User userLogin(String userId);



}
