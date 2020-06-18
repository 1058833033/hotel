package com.qf.hotel.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ChenJie
 * @date 2020-05-14 15:24:50
 * 功能说明
 */
@SpringBootApplication(scanBasePackages = "com.qf.hotel")
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
