package com.qf.hotel.aop;

import com.qf.hotel.pojo.User;

/**
 * @author ChenJie
 * @date 2020-05-29 15:28:35
 * 功能说明
 */
public class LoginParams {
    // 利用静态和本地线程来进行代理中传递参数给被代理对象
    // ThreadLocal - 哪个线程设置的值  哪个线程才能取出来
    private static ThreadLocal<User> user = new ThreadLocal<>();//JVM进程内存中只有一份 方法区（元空间）

    public static User getUser() {
        return user.get();
    }

    public static void setUser(User user) {
        LoginParams.user.set(user);
    }
}
