package com.wangff.learning.designpatterns.singleton;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Singleton {
    public static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
 
    private Singleton() {
        log.info("Singleton init==={}",getClass().getName());
    }
 
    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public int add(int a,int b) {
        int rlt = a + b;
        log.info("{}+{}=={}",a,b,rlt);
        return rlt;
    }

}