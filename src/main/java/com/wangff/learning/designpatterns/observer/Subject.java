package com.wangff.learning.designpatterns.observer;


public interface  Subject {
     void addObserver(Observer o) ;
     void deleteObserver(Observer o);
     void addObservers();
     void notifyObservers() ;
     void change(Object message);
}
