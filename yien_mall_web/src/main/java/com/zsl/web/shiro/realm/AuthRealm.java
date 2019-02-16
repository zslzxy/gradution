/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.shiro.realm;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.User;
import com.zsl.common.interfaces.management.LoginService;
import com.zsl.common.myenum.Level;
import com.zsl.common.myenum.State;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @Filename AuthRealm.java
 *
 * @Description 权限认证热Realm
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
public class AuthRealm extends AuthorizingRealm {

	@Reference(group = "management", version = "1.0.0")
	private LoginService loginService;

	/**
	 * 执行授权的操作
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//得到这个用户对象
		User user = (User) principalCollection.getPrimaryPrincipal();
		//定义集合来存储这个用户的权限
		Set<String> list = new HashSet<>();

		if (user.getUserLevel() == Level.admin) {
			list.add("admin");
			list.add("user");
			list.add("kill");
		} else if (user.getUserLevel() == Level.merchant) {
			list.add("merchant");
			list.add("user");
			list.add("kill");
		} else if (user.getUserLevel() == Level.user) {
			list.add("user");
			list.add("kill");
		}

		if (user.getUserState() == State.Cerdibility_Poor) {
			//如果这个用户的信誉为 差，则不允许进行任何操作
			list.clear();
		} else if (user.getUserState() == State.Cerdibility_Optimal) {
			//如果这个人信誉为 中，则不能够参与秒杀
			list.remove("kill");
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(list);
		return info;
	}

	/**
	 * 执行登录、身份认证
	 *
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		//得到对应的token
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		//得到传入到token中的username
		String userId = token.getUsername();

		//得到这个人的是数据
		User user = loginService.userLogin(userId);
		System.out.println(user);

		//如果数据没有，则表示账号输入错误
		if (user == null) {
			throw new UnknownAccountException("账号密码错误，请重新输入");
		}

		Object principal = user;
		String password = user.getUserPassword();
		String salt = "yien";
		SimpleAuthenticationInfo sact = new SimpleAuthenticationInfo(principal, password, ByteSource.Util.bytes(salt),
				getName());
		return sact;
	}
}
