package com.qf.hotel.aop;

import java.lang.annotation.*;

/**
 * @author ChenJie
 * @date 2020-05-29 09:58:26
 * 功能说明
 */
// 标记一些元注解，标记注解的注解
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})// 作用范围 可以标记类和方法
@Retention(RetentionPolicy.RUNTIME)// 有效范围
@Inherited // 当前注解具有继承性，父类有了该注解，子类自动继承该注解
public @interface IsLogin {

    // 设置注解的方法  -  没有方法体
    boolean mustLogin() default false;  // 是否需要强制登录
}
