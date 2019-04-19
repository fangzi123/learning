package com.wangff.learning.designpatterns.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusObserver implements Observer {
    @Override
    public void move(Object msg) {
        {
            log.info("bus..{}!!!",msg);
        }
    }
}
