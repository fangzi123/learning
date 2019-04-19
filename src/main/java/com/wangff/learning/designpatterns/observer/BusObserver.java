package com.wangff.learning.designpatterns.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusObserver implements Observer {
    @Override
    public void move(Object msg) {
        {
            if (msg instanceof ColorEnum) {
                if(ColorEnum.RED.getMode()==((ColorEnum) msg).getMode()){
                    log.info("bus..{}!!!","stop");
                }else if(ColorEnum.YELLOW.getMode()==((ColorEnum) msg).getMode()){
                    log.info("bus..{}!!!","slow");
                }else {
                    log.info("bus..{}!!!","go");
                }
            }
        }
    }
}
