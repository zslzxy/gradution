package com.zsl.search;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ImportResource;

@EnableHystrix
@EnableDubbo
@SpringBootApplication
public class YienMallSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(YienMallSearchApplication.class, args);
    }

}

