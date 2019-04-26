package com.wangff.learning.designpatterns.build;

public class Director {

    private IBuilder builder;

    public Director(IBuilder builder){
        this.builder = builder;
    }

    /**
     * 构造顺序
     */
    public Production build(){
        builder.buildPart1();
        builder.buildPart2();

        return builder.build();
    }

}
