package com.qf.hotel.application;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChenJie
 * @date 2020-05-23 09:47:09
 * 功能说明
 */
@Configuration
public class RabbitConfig {
    // 配置rabbitmq的交换机
    @Bean
    public DirectExchange getChange(){
        return new DirectExchange("hotel_exchange",true,false);
    }
}
