package com.wangff.learning.designpatterns.observer;

import java.util.HashSet;
import java.util.Set;

public abstract class Subject {
    public Object message;
    Set<Observer> observerList = new HashSet<>();
    public void addObserver(Observer o) {
        observerList.add(o);
    }
    public void deleteObserver(Observer o) {
        observerList.remove(o);
    }

    public void notifyObservers() {
        observerList.forEach(a->{
            a.move(message);
        });
    }

    public abstract void change(Object message);
}
