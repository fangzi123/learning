package com.wangff.learning.designpatterns.composite.app;

import java.util.ArrayList;

public class Branch implements IBranch{
	//存储子节点的信息
	private ArrayList subordinateList = new ArrayList();
	//树枝节点的名称
	private String name = "";
	//树枝节点的职位
	private String position = "";
	//树枝节点的薪水
	private int salary = 0;
	//通过构造函数传递树枝节点的参数
	public Branch(String name, String position,int salary) {
		this.name = name;
		this.position = position;
		this.salary = salary;
	}
	//增加一个下属，可能 是小头目，也可能是个小兵
	public void addSubordinate(ICorp corp) {
		this.subordinateList.add(corp);
	}
	//我有哪些下属
	public ArrayList<ICorp> getSubordinate() {
		return this.subordinateList;
	}
	//得到自己的信息
	public String getInfo() {
		String info = "";
		info = "名称:"+this.name;
		info = info + "\t职位:"+this.position;
		info = info + "\t薪水:"+this.salary;
		return info;
	}
}