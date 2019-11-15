package com.wangff.learning;

import com.wangff.learning.designpatterns.chain.CaseChain;
import com.wangff.learning.designpatterns.chain.OneCase;
import com.wangff.learning.designpatterns.chain.TwoCase;
import com.wangff.learning.designpatterns.proxy.*;

/**
 * @author wangff
 * @date 2019/8/29 18:10
 */
public class Test {

    public static void main(String[] args) {
//        CaseChain caseChain = new CaseChain();
//        caseChain.addBaseCase(new OneCase());
//        caseChain.addBaseCase(new TwoCase());
//        caseChain.doSomething("123",caseChain);
//        OneService proxyInstance =JdkProxy.newProxyInstance(new OneServiceImpl(),new TransactionInterceptor());
        Interceptor interceptor =new TransactionInterceptor();
//        OneService proxyInstance = interceptor.plugin(new OneServiceImpl());

        Interceptor logInterceptor =new LogInterceptor();
//        OneService proxyInstance2 = logInterceptor.plugin(proxyInstance);
//        proxyInstance2.test();

        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(interceptor);
        interceptorChain.addInterceptor(logInterceptor);
        OneService proxyInstance =interceptorChain.pluginAll(new OneServiceImpl());
        proxyInstance.test();

    }
}
