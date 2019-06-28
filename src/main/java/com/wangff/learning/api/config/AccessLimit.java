package com.wangff.learning.api.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于注解的请求限制
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    /**
     * 请求限制数
     * @return
     */
    int limit() default 1;


    /**
     * 时间范围 单位s
     * @return
     */
    int timeScope() default 2;

}
