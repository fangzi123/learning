package com.wangff.learning.designpatterns.proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneServiceImpl implements OneService {
    @Override
    public void test() {
        log.info("======={}",getClass().getName());
    }
}
