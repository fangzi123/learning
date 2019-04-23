package com.wangff.learning.designpatterns.iterator;


public class ConcreteIterator implements Iterator {
    private MyList list = null;
    private int index;

    public ConcreteIterator(MyList list) {
        super();
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        if (index >= list.getSize()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object next() {
        Object object = list.get(index);
        index++;
        return object;
    }

}