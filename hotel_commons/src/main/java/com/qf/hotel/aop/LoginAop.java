package com.qf.hotel.aop;

import com.qf.hotel.pojo.ResultData;
import com.qf.hotel.pojo.User;
import com.qf.hotel.utils.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author ChenJie
 * @date 2020-05-29 11:02:33
 * 功能说明:登录的增强类
 */
@Aspect
@Component
public class LoginAop {
    /**
     * 实现增强
     * 1、前置增强
     * 2、后置增强
     * 3、环绕增强（记这一个就行了，其他都可以用这个实现）
     * 4、后置完成增强
     * 5、异常增强
     */
    // 实现一个环绕增强  切点表达式
    //@Around("@annotation(IsLogin) && execution(* com.qf.hotel.controller.*.*(..))")
    @Around("@annotation(com.qf.hotel.aop.IsLogin)")
    public ResultData<?> isLogin(ProceedingJoinPoint joinPoint){
        // 前置增强

        // 获取页面传来的令牌
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String jwtToken = request.getParameter("jwtToken");
        System.out.println("获取到令牌:" + jwtToken);

        // 登录的用户对象
        User user = null;

        // 判断令牌
        if (jwtToken != null){
            // 开始解析令牌  验证签名
            user = JwtUtil.paseJwtToken(jwtToken);
        }

        // 判断用户对象是否为空，如果为空则表示未登录或者登录已经过期
        if (user == null) {
            // 当前未登录,根据IsLogin注解的mustLogin判断是否需要强制登录
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            // 根据方法签名获取方法的反射对象
            Method method = methodSignature.getMethod();

            // 通过方法的反射对象获取注解
            IsLogin isLogin = method.getAnnotation(IsLogin.class);

            if (isLogin.mustLogin()){
                // 必须强制登录   告诉页面去往登录界面
                return new ResultData<>().setCode(ResultData.Code.CODE_RELOGIN).setMsg("请登录！");
            }
        }

        // 1、用户登录了，获取到了用户信息
        // 2、未获取到用户信息但不要求强制登录
        // 登录了是一种处理方式，未登录一种处理方式，类似于vip


        // 获取用户信息的方式
        // 方式1：  在被代理的方法中添加一个user对象的参数，在前置增强中将解析出来的信息赋值给user
//        Object[] args = joinPoint.getArgs();// 获得原参数列表
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].getClass() == User.class) {
//                // 用户的登录信息覆盖参数
//                args[i] = user;
//            }
//        }
//        ResultData resultData =  null;
//        try {
//            // 调用目标方法
//            resultData = (ResultData) joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }

        // 方式2：  利用一个类的静态成员变量.
        // 在此处利用令牌中的信息赋值，在需要的地方取出来，注意使用ThreadLocal防止出现线程安全问题
        LoginParams.setUser(user);
        ResultData resultData =  null;
        try {
            // 调用目标方法
            resultData = (ResultData) joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.println(resultData);
        return resultData;
    }
}
