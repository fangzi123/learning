package com.wangff.learning.designpatterns.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarObserver implements Observer {
    @Override
    public void move(Object msg) {
        {
            if (msg instanceof ColorEnum) {
                if(ColorEnum.RED.getMode()==((ColorEnum) msg).getMode()){
                    log.info("car..{}!!!","stop");
                }else if(ColorEnum.YELLOW.getMode()==((ColorEnum) msg).getMode()){
                    log.info("car..{}!!!","slow");
                }else {
                    log.info("car..{}!!!","go");
                }
            }

        }
    }
}
