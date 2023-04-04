package com.wangff.learning.designpatterns.chain.chain1;

import lombok.extern.slf4j.Slf4j;

/**
 * @AUTHOR: wangff
 * @DATE: 2023/4/4 15:25
 */
@Slf4j
public class InterceptorImpl implements Interceptor {
    @Override
    public boolean handleRequest(Request request) {
        return true;
    }

    @Override
    public void aroundProcess(Request request, Response response, HandleChain chain) throws Exception {
        response.setRlt(request.getContext());
        log.info("================{}", response.getRlt());

    }
}
