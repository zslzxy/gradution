package com.zsl.management.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ${张世林}
 * @date 2019/02/06
 * 作用：
 */
@Configuration
public class BeanConfig {
    /**
     * 使用序列化转换器（json数据）
     * @return
     */
    @Bean
    public MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

}
