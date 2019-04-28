package com.wangff.learning.designpatterns.strategy;

public class OperationDiv implements CalculateStrategy {
   @Override
   public int doOperation(int num1, int num2) {
       return num1 / num2;
   }
}