package com.wangff.learning.designpatterns.strategy;

public class  CalculatorContext {
    private CalculateStrategy strategy;

    public CalculatorContext(CalculateStrategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}