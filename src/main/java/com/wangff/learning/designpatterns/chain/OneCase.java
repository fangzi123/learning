package com.wangff.learning.designpatterns.chain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneCase implements BaseCase {
        @Override 
        public void doSomething(String input, BaseCase baseCase) {
            log.info("OneCase======={}",getClass().getName());
            if (input.contains("1")) {
                input+="_has(1)_";
             }
            //当前没法处理，回调回去，让下一个去处理 
            baseCase.doSomething(input, baseCase);
        }
}