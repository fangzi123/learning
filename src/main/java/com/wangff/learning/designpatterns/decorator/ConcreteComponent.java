package com.wangff.learning.designpatterns.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteComponent implements Component {

    @Override
    public void sampleOperation() {
        log.info("具体组件==={}",getClass().getName());
    }
}