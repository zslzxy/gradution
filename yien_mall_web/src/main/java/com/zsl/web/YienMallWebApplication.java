package com.zsl.web;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.zsl.common.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Filename YienMallWebApplication.java
 *
 * @Description web端程序启动入口
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
@EnableSwagger2
@EnableDubbo
@EnableHystrix
@SpringBootApplication
public class YienMallWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(YienMallWebApplication.class, args);
	}

}

