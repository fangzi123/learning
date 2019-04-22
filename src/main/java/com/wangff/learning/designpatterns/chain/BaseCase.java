package com.wangff.learning.designpatterns.chain;

interface BaseCase {
// 所有 case 处理逻辑的方法 
    void doSomething(String input, BaseCase baseCase);
}