package com.wangff.learning.designpatterns.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }
    
    @Override
    public void sampleOperation() {
        log.info("A装饰前置业务");
        super.sampleOperation();
        log.info("A装饰后置业务");
    }
}