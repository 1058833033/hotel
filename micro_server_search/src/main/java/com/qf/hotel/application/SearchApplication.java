package com.qf.hotel.application;

import lombok.experimental.Accessors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ChenJie
 * @date 2020-05-24 21:55:24
 * 功能说明
 */
@SpringBootApplication(scanBasePackages = "com.qf.hotel")
@EnableEurekaClient
@Accessors(chain = true)
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }
}
