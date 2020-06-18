package com.qf.hotel.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ChenJie
 * @date 2020-05-28 21:20:59
 * 功能说明
 */
@SpringBootApplication(scanBasePackages = "com.qf.hotel")
@EnableEurekaClient
@EnableTransactionManagement
@MapperScan("com.qf.hotel.mapper")
public class SSOApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class,args);
    }
}
