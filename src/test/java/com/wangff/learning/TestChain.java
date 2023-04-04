package com.wangff.learning;

import com.wangff.learning.designpatterns.chain.chain1.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR: wangff
 * @DATE: 2023/4/4 15:23
 */
public class TestChain {
    public static void main(String[] args) throws Exception {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new InterceptorImpl());
        interceptors.add(new InterceptorImpl2());
        HandleChain chain = new HandleChain(interceptors);
        chain.intercept(Request.builder().context("wff").build(), Response.builder().build());
    }
}
