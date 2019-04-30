package com.wangff.learning.designpatterns.command;

public class ConcreteCommandA implements ICommand {
        private Receiver receiver;
        public ConcreteCommandA(Receiver receiver){
            this.receiver = receiver;
        }
        @Override
        public void execute(String cmd) {
            System.out.println("ConcreteCommandA execute ...");
            receiver.execute(cmd);
        }
    }