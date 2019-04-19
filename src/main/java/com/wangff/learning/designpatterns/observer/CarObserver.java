package com.wangff.learning.designpatterns.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarObserver implements Observer {
    @Override
    public void move(Object msg) {
        {
            log.info("car..{}!!!",msg);
        }
    }
}
