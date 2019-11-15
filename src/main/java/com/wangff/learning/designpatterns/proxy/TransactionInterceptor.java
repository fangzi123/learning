package com.wangff.learning.designpatterns.proxy;

public class TransactionInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Exception{
        System.out.println("------事务插入前置通知代码-------------");
        Object result = invocation.process();
        System.out.println("------事务插入后置处理代码-------------");
        return result;
    }

    @Override
    public <T> T plugin(Object target) {
        return (T)JdkProxy.newProxyInstance(target,this);
    }
}