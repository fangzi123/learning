package com.wangff.learning;

import com.wangff.learning.designpatterns.observer.ColorEnum;
import com.wangff.learning.designpatterns.observer.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearningApplication.class)
public class LearningApplicationTests {
	@Autowired
	private Subject subject;
	@Test
	public void observer() {
//		Observer car = new CarObserver();
//		Observer bus = new BusObserver();
//		Subject subject = new LightSubject();
//		subject.addObserver(car);
//		subject.addObserver(bus);
		subject.change(ColorEnum.RED);
		subject.change(ColorEnum.YELLOW);
		subject.change(ColorEnum.GREEN);
	}

}
