package com.wangff.learning;

import com.wangff.learning.designpatterns.adapter.Adaptee;
import com.wangff.learning.designpatterns.adapter.TargetAdapter;
import com.wangff.learning.designpatterns.chain.CaseChain;
import com.wangff.learning.designpatterns.chain.OneCase;
import com.wangff.learning.designpatterns.chain.TwoCase;
import com.wangff.learning.designpatterns.decorator.*;
import com.wangff.learning.designpatterns.factory.Car;
import com.wangff.learning.designpatterns.factory.FactoryCar;
import com.wangff.learning.designpatterns.iterator.ConcreteAggregate;
import com.wangff.learning.designpatterns.iterator.Iterator;
import com.wangff.learning.designpatterns.iterator.MyList;
import com.wangff.learning.designpatterns.observer.Subject;
import com.wangff.learning.designpatterns.proxy.JdkProxy;
import com.wangff.learning.designpatterns.proxy.OneService;
import com.wangff.learning.designpatterns.proxy.OneServiceImpl;
import com.wangff.learning.designpatterns.singleton.Singleton;
import com.wangff.learning.designpatterns.templateMethod.Coffee;
import com.wangff.learning.designpatterns.templateMethod.RefreshBeverage;
import com.wangff.learning.designpatterns.templateMethod.Tea;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.SingleThreadModel;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearningApplication.class)
public class LearningApplicationTests {
	@Autowired
	private Subject subject;

	@Test
	public void observer() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("red",1);
		jsonObject.put("green","2");
		subject.change(jsonObject);
		subject.change(jsonObject.toJSONString());
	}
	@Test
	public void adapter() {
		TargetAdapter adapter = new TargetAdapter(new Adaptee());
		adapter.execute();
	}

	@Test
	public void chain() {
		String context = "123";
		CaseChain caseChain = new CaseChain();
		caseChain.addBaseCase(new OneCase())
				 .addBaseCase(new TwoCase());
		caseChain.doSomething(context, caseChain);
	}

	@Test
	public void decorator() {
		Decorator decorator = new ConcreteDecoratorB(new ConcreteDecoratorA(new ConcreteComponent()));
		decorator.sampleOperation();
	}

	@Test
	public void jdkProxy() {
		JdkProxy jdkProxy = new JdkProxy(new OneServiceImpl());
		OneService proxy =(OneService)jdkProxy.getProxy();
		proxy.test();
	}

	@Test
	public void iterator() {
		MyList list=new ConcreteAggregate();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		Iterator it=list.iterator();
		while(it.hasNext()){
			log.info("{}",it.next());
		}
	}

	@Test
	public void singleton() {
		Singleton.getInstance().add(1,2);
	}
	@Autowired
	private FactoryCar factoryCar;
	@Test
	public void factory() {
		Car car = factoryCar.getInstance("benchi");
		car.eat();
	}

	@Test
	public void templateMethod() {
		/**
		 * 父类提供模板方法
		 * 共性的放到抽象父类中实现
		 * 个性化的在子类中实现
		 */
		System.out.println("制备咖啡中······");
		RefreshBeverage b1 = new Coffee();
		b1.prepareBeverageTemplate();
		System.out.println("咖啡好了········");

		//制备茶的测试代码
		System.out.println("\n*********************************");
		System.out.println("制备茶水中······");
		RefreshBeverage b2 = new Tea();
		b2.prepareBeverageTemplate();
		System.out.println("茶水好了······");
	}
}
