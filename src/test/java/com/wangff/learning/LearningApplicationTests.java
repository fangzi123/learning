package com.wangff.learning;

import com.alibaba.fastjson.JSON;
import com.wangff.learning.designpatterns.adapter.Adaptee;
import com.wangff.learning.designpatterns.adapter.TargetAdapter;
import com.wangff.learning.designpatterns.build.*;
import com.wangff.learning.designpatterns.chain.CaseChain;
import com.wangff.learning.designpatterns.chain.OneCase;
import com.wangff.learning.designpatterns.chain.TwoCase;
import com.wangff.learning.designpatterns.command.ConcreteCommandA;
import com.wangff.learning.designpatterns.command.Invoker;
import com.wangff.learning.designpatterns.command.Receiver;
import com.wangff.learning.designpatterns.composite.Composite;
import com.wangff.learning.designpatterns.composite.Leaf;
import com.wangff.learning.designpatterns.decorator.*;
import com.wangff.learning.designpatterns.factory.Car;
import com.wangff.learning.designpatterns.factory.FactoryCar;
import com.wangff.learning.designpatterns.flyweight.Circle;
import com.wangff.learning.designpatterns.flyweight.ShapeFactory;
import com.wangff.learning.designpatterns.iterator.ConcreteAggregate;
import com.wangff.learning.designpatterns.iterator.Iterator;
import com.wangff.learning.designpatterns.iterator.MyList;
import com.wangff.learning.designpatterns.observer.Subject;
import com.wangff.learning.designpatterns.proxy.JdkProxy;
import com.wangff.learning.designpatterns.proxy.OneService;
import com.wangff.learning.designpatterns.proxy.OneServiceImpl;
import com.wangff.learning.designpatterns.singleton.Singleton;
import com.wangff.learning.designpatterns.strategy.*;
import com.wangff.learning.designpatterns.templateMethod.Coffee;
import com.wangff.learning.designpatterns.templateMethod.RefreshBeverage;
import com.wangff.learning.designpatterns.templateMethod.Tea;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

	@Test
	public void build() {
		IBuilder builderA = new BuilderA();
		Director director = new Director(builderA);
		Production p=director.build();


		Simple simple = Simple.builder().id(1L).build();
		log.info("simple=={}", JSON.toJSONString(simple));
	}

	@Test
	public void executeStrategy() {
		int a=4,b=2;
		CalculatorContext context = new CalculatorContext(new OperationAdd());
		System.out.println("a + b = "+context.executeStrategy(a, b));

		CalculatorContext context2 = new CalculatorContext(new OperationSub());
		System.out.println("a - b = "+context2.executeStrategy(a, b));

		CalculatorContext context3 = new CalculatorContext(new OperationMul());
		System.out.println("a * b = "+context3.executeStrategy(a, b));

		CalculatorContext context4 = new CalculatorContext(new OperationDiv());
		System.out.println("a / b = "+context4.executeStrategy(a, b));

		CalculateStrategy strategy = new OperationSub();
		strategy.doOperation(a,b);
	}

    private static final String colors[] = { "Red", "Green", "Blue"};

    private static String getRandomColor() {
        return colors[(int)(Math.random()*colors.length)];
    }
    private static int getRandomX() {
        return (int)(Math.random()*100 );
    }
    private static int getRandomY() {
        return (int)(Math.random()*100);
    }

    @Test
    public void flyweight() {
        for(int i=0; i < 10; ++i) {
            Circle circle =
                    (Circle) ShapeFactory.getCircle(getRandomColor());
            circle.setXYR(getRandomX(),getRandomY(),10);
            circle.draw();
        }
    }
    @Test
    public  void command() {
        Receiver receiver = new Receiver();
        Invoker invoker = new Invoker(new ConcreteCommandA(receiver));
        invoker.orderA("111111111111");
    }

    //通过递归遍历树
    public static void display(Composite root){
        for(com.wangff.learning.designpatterns.composite.Component c:root.getChildren()){	//叶子节点
            if(c instanceof Leaf){
                c.doSomething();
            }else{	//树枝节点
                display((Composite)c);
            }
        }
    }
    @Test
    public  void composite() {
        //创建一个根节点
        Composite root = new Composite();
        root.doSomething();
        //创建一个树枝构件
        Composite branch = new Composite();
        //创建一个叶子节点
        Leaf leaf = new Leaf();
        //建立整体
        root.add(branch);
        branch.add(leaf);
    }



}
