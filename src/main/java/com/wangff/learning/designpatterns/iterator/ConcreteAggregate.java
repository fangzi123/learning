package com.wangff.learning.designpatterns.iterator;

public class ConcreteAggregate implements MyList {

    private Object[] list;
    private int size=0;
    private int index=0;
    public ConcreteAggregate(){
        index=0;
        size=0;
        list=new Object[100];
    }
    @Override
    public void add(Object obj) {
        list[index++]=obj;
        size++;
    }

    @Override
    public Iterator iterator() {
        
        return new ConcreteIterator(this);
    }
    @Override
    public Object get(int index) {
        
        return list[index];
    }
    @Override
    public int getSize() {
        
        return size;
    }

}