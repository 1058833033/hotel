package com.qf.hotel;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChenJie
 * @date 2020-05-15 12:53:01
 * 功能说明
 */
@Configuration
@EnableFeignClients("com.qf.hotel")
public class FeignConfiguration {
}
