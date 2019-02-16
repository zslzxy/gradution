/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.User;
import com.zsl.common.interfaces.management.LoginService;
import com.zsl.management.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Filename LoginServiceImpl.java
 *
 * @Description 登录的service的impl
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月24日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Component
@Service(group = "management", version = "1.0.0",timeout = 8000)
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserMapper userMapper;

	@Override public User userLogin(String userId) {
		User user = userMapper.selectOneByUserId(userId);
		System.out.println(user);
		return user;
	}

}
