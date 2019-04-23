package com.wangff.learning.designpatterns.iterator;

//定义集合可以进行的操作
public interface MyList {

    public void add(Object obj);  
    public Object get(int index);
    public Iterator iterator();  
    public int getSize();
}