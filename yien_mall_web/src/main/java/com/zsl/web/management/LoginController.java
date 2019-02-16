/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.management;

import com.zsl.common.utils.Msg;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.management
 * @since 2019/1/4
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "/toLogin", method = RequestMethod.GET)
	public String toLogin() {
		return "management/login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "management/login";
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Msg login(@RequestParam("remeberMe") Integer remeberMe, @RequestParam("userId") String userId,
					 @RequestParam("password") String password) {
		//得到Shiro的Subject
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userId, password);
			//设置是否记住我
			if (remeberMe == 1) {
				usernamePasswordToken.setRememberMe(true);
			}
			//执行登录的操作
			try {
				subject.login(usernamePasswordToken);
			} catch (AuthenticationException ex) {
				ex.getStackTrace();
				return Msg.fail().addInfo("登录失败，账号或密码错误");
			}
		}
		return Msg.success().addInfo("登陆成功");
	}
}
