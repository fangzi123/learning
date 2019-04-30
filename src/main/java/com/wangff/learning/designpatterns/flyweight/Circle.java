package com.wangff.learning.designpatterns.flyweight;

public class Circle implements Shape{
    private String color;
    private int x,y,r;

    public Circle(String color){
        this.color = color;
    }

    public void setXYR(int x,int y,int r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void draw() {
        System.out.println("draw circle: x = " + x + " , y = " + y + " , r = " + r + " , color = " + color);
    }
}