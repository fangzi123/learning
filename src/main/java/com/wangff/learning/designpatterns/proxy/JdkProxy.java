package com.wangff.learning.designpatterns.proxy;
 
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@Slf4j
public class JdkProxy<T> implements InvocationHandler {

    private Object target;
    private Interceptor interceptor;

    public JdkProxy(Object target,Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }
 
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation(target,method,args);
//        return invocation.process();
//        return method.invoke(target,args);
        return interceptor.intercept(invocation);
    }
 
    /**
     * 生成代理对象
     * @return
     */
    public static <T> T newProxyInstance(Object target,Interceptor interceptor){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new JdkProxy(target,interceptor));
    }
}
