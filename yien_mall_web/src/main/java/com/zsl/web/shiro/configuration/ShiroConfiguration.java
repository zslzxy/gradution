/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.shiro.configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zsl.web.shiro.realm.AuthRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.shiro.configuration
 * @since 2019/1/2
 */
@Configuration
public class ShiroConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	/**
	 * 配置securityManager
	 *
	 * @param authRealm
	 * @param cookieRememberMeManager
	 * @return
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm,
										   @Qualifier("cookieRememberMeManager")
												   CookieRememberMeManager cookieRememberMeManager) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		//设置realm
		defaultWebSecurityManager.setRealm(authRealm);
		//设置remeberme
		defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
		return defaultWebSecurityManager;
	}

	/**
	 * 配置realm的bean
	 *
	 * @return
	 */
	@Bean
	public AuthRealm authRealm(
			@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
		AuthRealm authRealm = new AuthRealm();
		//配置加密方式
		authRealm.setCredentialsMatcher(hashedCredentialsMatcher);
		return authRealm;
	}

	/**
	 * 配置加密方式
	 *
	 * @return
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		//设置加密方式
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		//设置加密次数
		hashedCredentialsMatcher.setHashIterations(1024);
		return hashedCredentialsMatcher;
	}

	/**
	 * 配置cookie
	 *
	 * @return
	 */
	@Bean(name = "rememberMeCookie")
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	/**
	 * 设置cookie的 remebermeManager
	 *
	 * @param rememberMeCookie
	 * @return
	 */
	@Bean(name = "cookieRememberMeManager")
	public CookieRememberMeManager cookieRememberMeManager(
			@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie) {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie);
		return cookieRememberMeManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		HashMap<String, Filter> myFilters = new HashMap<>();
		myFilters.put("authc1", new ApiCustomUserFilter());
		shiroFilterFactoryBean.setFilters(myFilters);

		Map<String, String> map = new LinkedHashMap<>();

		map.put("/","anon");
		map.put("/search/**", "anon");
		//设置注销的url
		map.put("/login/logout", "logout");
		//匿名访问静态资源
		map.put("/webjars/**", "anon");
		//匿名访问静态资源
		map.put("/static/**", "anon");
		//首页，无需登录即可进行访问
		map.put("/index", "anon");
		//设置商品的controller不被拦截
		map.put("/goods/**", "anon");
		//登录注册不能够被拦截
		map.put("/login/**", "anon");
		map.put("/regist/**", "anon");

		//所有的请求都不进行拦截
		map.put("/ajax/**", "authc1");

		//如果要购买商品，就必须要先登录
		map.put("/goods/goodsBuy/**", "authc");
		//去购物车必须要进行登录
		map.put("/shopCar/**", "authc");
		map.put("/kill/**", "anon");
		map.put("/admin/**", "authc");
		map.put("/**", "authc");

		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setLoginUrl("/login/toLogin");
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/unauthorized");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

}
