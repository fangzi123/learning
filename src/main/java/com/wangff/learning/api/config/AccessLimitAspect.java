package com.wangff.learning.api.config;

import com.wangff.learning.api.utils.IpUtils;
import com.wdcloud.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 限流
 * @author wangff
 */
@Slf4j
@Aspect
@Component
public class AccessLimitAspect {

    @Autowired
    private IRedisService redisService;
    /**
     * @param point
     */
    @Around("@annotation(com.wangff.learning.api.config.AccessLimit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = IpUtils.getIpAdrress(request);
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //目标类、方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        String ipKey = String.format("%s#%s",className,name);
        int hashCode = Math.abs(ipKey.hashCode());
        String redisKey = String.format("%s_%d",ip,hashCode);
        log.info("ipKey={},redisKey={}",ipKey,redisKey);

        AccessLimit accessLimit =  method.getAnnotation(AccessLimit.class);
        long timeScope = accessLimit.timeScope();
        long limit = accessLimit.limit();
        String currentCount = redisService.get(redisKey);
        if (StringUtils.isNotBlank(currentCount)) {
            int count = Integer.valueOf(currentCount);
            if (count < limit) {
                redisService.incr(redisKey);
            } else {
                throw new RuntimeException("服务器过于繁忙");
            }
        } else {
            redisService.setEx(redisKey, "1", timeScope, TimeUnit.SECONDS);
        }
        //执行方法
        Object object = point.proceed();
        return object;
    }

}