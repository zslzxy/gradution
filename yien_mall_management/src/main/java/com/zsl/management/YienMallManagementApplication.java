package com.zsl.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableScheduling
@EnableDubbo
@EnableCaching
@EnableRabbit
@EnableHystrix
@SpringBootApplication
@MapperScan("com.zsl.management.mapper")
@EnableTransactionManagement
public class YienMallManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(YienMallManagementApplication.class, args);
    }

}

