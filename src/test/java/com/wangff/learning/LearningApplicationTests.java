package com.wangff.learning;

import com.wangff.learning.designpatterns.observer.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearningApplicationTests {

	@Test
	public void observer() {
		Observer car = new CarObserver();
		Observer bus = new BusObserver();

		Subject subject = new LightSubject();
		subject.addObserver(car);
		subject.addObserver(bus);
		subject.change(1111111111);
	}

}
