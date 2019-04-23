package com.wangff.learning.designpatterns.proxy;
 
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@Slf4j
public class JdkProxy implements InvocationHandler {

    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }
 
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("调用代理类方法之前执行");
        Object result = method.invoke(target,args);
        log.info("调用代理类方法之后执行");
        return result;
    }
 
 
    /**
     * 生成代理对象
     * @return
     */
    public Object getProxy(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] clazzInterface = target.getClass().getInterfaces();
        Object proxy= Proxy.newProxyInstance(classLoader,clazzInterface,this);
        log.info("proxy=={}",proxy.getClass().getName());
        return proxy;
    }
}
