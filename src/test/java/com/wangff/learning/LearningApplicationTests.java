package com.wangff.learning;

import com.wangff.learning.designpatterns.observer.enums.ColorEnum;
import com.wangff.learning.designpatterns.observer.Subject;
import net.minidev.json.JSONObject;
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("red",1);
		jsonObject.put("green","2");
		subject.change(jsonObject);
		subject.change(jsonObject.toJSONString());
	}

}
