package com.wangff.learning.designpatterns.adapter;

public class TargetAdapter implements Target {

    private Adaptee adaptee;

    public TargetAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void execute() {
        adaptee.run();
    }
}