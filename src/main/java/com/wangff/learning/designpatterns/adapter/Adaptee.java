package com.wangff.learning.designpatterns.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Adaptee {
    public void run(){
        log.info("适配者run...");
    }
}