package com.zsl.web.shiro;

import com.zsl.common.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 *
 * @Filename ShiroUtils.java
 *
 * @Description shiro的通用工具类
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
public class ShiroUtils {

	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}

	public static User getUser() {
		return (User) getSubjct().getPrincipal();
	}

	/**
	 * 切换身份，登录后，动态更改subject的用户属性
	 *
	 * @param userInfo
	 */
	public static void setUser(User userInfo) {
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection principalCollection = subject.getPrincipals();
		String realmName = principalCollection.getRealmNames().iterator().next();
		PrincipalCollection newPrincipalCollection =
				new SimplePrincipalCollection(userInfo, realmName);
		subject.runAs(newPrincipalCollection);
	}

}
