package com.wangff.learning.designpatterns.factory;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FactoryCar {
	@Autowired
	private  Car[] cars;

	public  Car getInstance(String support) {
		for (Car car : cars) {
			if (car.support().equals(support)){
				return car;
			}
		}
		return null;
	}
}