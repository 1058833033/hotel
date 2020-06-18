package com.qf.hotel.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ChenJie
 * @date 2020-05-17 13:18:56
 * 功能说明
 */
@SpringBootApplication(scanBasePackages = "com.qf.hotel")
@EnableEurekaClient
@MapperScan("com.qf.hotel.mapper")
@EnableTransactionManagement
public class HotelApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class,args);
    }
}
