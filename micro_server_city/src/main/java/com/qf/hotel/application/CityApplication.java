package com.qf.hotel.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ChenJie
 * @date 2020-05-14 18:08:07
 * 功能说明
 */
@SpringBootApplication(scanBasePackages = "com.qf.hotel")
@MapperScan("com.qf.hotel.mapper")
@EnableEurekaClient
@EnableTransactionManagement // 开启事务管理
public class CityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityApplication.class,args);
    }
}
