package com.wangff.learning.designpatterns.factory;

import org.springframework.stereotype.Component;

@Component
public class BenChi implements Car {
 
	@Override
	public void eat() {
		System.out.println("benchi！");
	}
	@Override
	public String support(){
		return "benchi";
	}

}
