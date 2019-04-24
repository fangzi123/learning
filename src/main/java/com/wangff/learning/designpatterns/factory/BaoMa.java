package com.wangff.learning.designpatterns.factory;

import org.springframework.stereotype.Component;

@Component
public class BaoMa implements Car {
 
	@Override
	public void eat() {
		System.out.println("baoma！");
	}

	@Override
	public String support() {
		return "baoma";
	}

}
