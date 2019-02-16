/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.shiro.configuration;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.shiro.configuration
 * @since 2019/2/12
 */
@Component("killGoodsFilter")
public class KillGoodsFilter extends UserFilter {

	@Override protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

//		if () {
//			writeJson("您尚未登录", res);
//		} else {
//			redirectToLogin(request, response);
//		}
		return false;
	}
}
