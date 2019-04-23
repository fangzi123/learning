package com.wangff.learning.designpatterns.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteDecoratorB extends Decorator {

    public ConcreteDecoratorB(Component component) {
        super(component);
    }
    
    @Override
    public void sampleOperation() {
        log.info("B装饰前置业务");
        super.sampleOperation();
        log.info("B装饰后置业务");
    }
}