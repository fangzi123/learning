package com.wangff.learning.designpatterns.proxy;

public class LogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Exception{
        System.out.println("------Log插入前置通知代码-------------");
        Object result = invocation.process();
        System.out.println("------Log插入后置处理代码-------------");
        return result;
    }

    @Override
    public <T> T plugin(Object target) {
        return (T)JdkProxy.newProxyInstance(target,this);
    }
}