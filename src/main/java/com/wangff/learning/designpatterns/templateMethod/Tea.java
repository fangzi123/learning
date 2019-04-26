package com.wangff.learning.designpatterns.templateMethod;

public class Tea extends RefreshBeverage {

    protected void brew() {
        System.out.println("步骤二 用80度热水浸泡茶叶5分钟");
    }

    protected void addCondiments() {
        System.out.println("步骤四 加入柠檬");
    }
    
    protected boolean isCustomerWantsCondiments(){
        return false;
    }
    
}