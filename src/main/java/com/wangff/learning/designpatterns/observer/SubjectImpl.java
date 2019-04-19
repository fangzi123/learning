package com.wangff.learning.designpatterns.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class SubjectImpl implements   Subject {
    public Object message;
    Set<Observer> observerList = new HashSet<>();
    @Autowired
    private Observer[] observers;

    public void addObserver(Observer o) {
        observerList.add(o);
    }
    public void deleteObserver(Observer o) {
        observerList.remove(o);
    }

    @Autowired
    public void addObservers() {
        observerList.addAll(Arrays.asList(observers));
    }

    public void notifyObservers() {
        observerList.forEach(a->{
            a.execute(message);
        });
    }

    @Override
    public void change(Object message) {
        this.message = message;
        notifyObservers();
    }
}
