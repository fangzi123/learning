package com.wangff.learning.designpatterns.observer;

public class LightSubject extends   Subject {

    @Override
    public void change(Object message) {
        super.message = message;
        super.notifyObservers();
    }
}
