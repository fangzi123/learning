package com.wangff.learning.designpatterns.observer;

import com.wangff.learning.designpatterns.observer.enums.ColorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ObserverImpl1 implements Observer {
    @Override
    public void execute(Object msg) {
        {
            doBusiness(msg);
        }
    }

    private void doBusiness(Object msg) {
      log.info("beanName=={},msg=={}",this.getClass().getName(),msg.toString());
    }
}
