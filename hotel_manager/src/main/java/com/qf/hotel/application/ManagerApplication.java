package com.qf.hotel.application;

import com.qf.hotel.FeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * @author ChenJie
 * @date 2020-05-14 20:26:56
 * 功能说明
 */
@SpringBootApplication(scanBasePackages = "com.qf.hotel")
@EnableEurekaClient
@Import(value = FeignConfiguration.class)
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
