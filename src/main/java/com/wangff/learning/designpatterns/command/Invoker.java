package com.wangff.learning.designpatterns.command;

public class Invoker {
        private ICommand concreteCommandA;
        public Invoker(ICommand concreteCommandA){
            this.concreteCommandA = concreteCommandA;
        }
        public void orderA(String cmd){
            concreteCommandA.execute(cmd);
        }
    }