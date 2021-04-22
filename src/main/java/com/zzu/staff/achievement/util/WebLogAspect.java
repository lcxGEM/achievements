package com.zzu.staff.achievement.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.zzu.staff.achievement.controller..*.*(..))")//切入点描述 这个是controller包的切入点 监控info日志的切入点
    public void controllerLog(){}//签名，可以理解成这个切入点的一个名称

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")//监控错误日志--》get方法
    private void getPointcut() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")//监控错误日志--》post方法
    private void postPointcut() {}

    @Before("controllerLog()") //在切入点的方法run之前要干的
    public void logBeforeController(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        // 记录下请求内容
        logger.info("################ IP为:" + request.getRemoteAddr()+"的主机请求数据，url为："+request.getRequestURL().toString());
        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("################ " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+"方法被执行");
    }

    @AfterThrowing(pointcut = "getPointcut()||postPointcut()",throwing = "e")
    public void logerrorController(JoinPoint joinPoint,Exception e){
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logger.error(methodName+"方法，执行异常，异常类为："+className+"异常信息："+e.getMessage());
    }
}
