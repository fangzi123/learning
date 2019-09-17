package com.wangff.learning.designpatterns.chain.chain1;

import java.util.Iterator;
import java.util.List;

/**
 * @author wangff
 * @date 2019/9/16 18:11
 */
public class HandleChain {
    private List<Interceptor> interceptors;
    private Iterator<Interceptor> iterator;

    public HandleChain(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        this.iterator = interceptors.iterator();
    }

    public void intercept(Request request, Response response) throws Exception {
        if (iterator.hasNext()) {
            Interceptor interceptor = iterator.next();
            interceptor.handleRequest(request);
            interceptor.aroundProcess(request, response, this);
        }
    }
}
