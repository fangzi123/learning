package com.wangff.learning.designpatterns.templateMethod;

/**
 * 提供制备咖啡的具体实现子类。
 * 具体子类实现延迟步骤，满足特定的业务需求。
 * @author zplogo
 *
 */
public class Coffee extends RefreshBeverage {

    protected void brew() {
        System.out.println("步骤二 用沸水冲泡咖啡");
    }

    protected void addCondiments() {
        System.out.println("步骤四 加入糖和牛奶");
    }

}