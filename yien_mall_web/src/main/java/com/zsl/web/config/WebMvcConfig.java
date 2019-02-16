/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.config;

import com.zsl.web.intercepter.KillInterceptor;
//import com.zsl.web.intercepter.ToKillIntercepter;
import com.zsl.web.intercepter.TokenInterceptor;
import com.zsl.web.intercepter.service.KillTimeServiceDo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @Filename WebMvcConfig.java
 *
 * @Description 添加拦截器
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年02月12日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public KillTimeServiceDo killTimeServiceDo() {
		return new KillTimeServiceDo();
	}
	//在此处，将拦截器注册为一个 Bean
	@Bean
	public HandlerInterceptor getKillInterceptor(){
		return new KillInterceptor();
	}

	@Override public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(getKillInterceptor()).addPathPatterns("/kill/toKillAjax");
	}
}
