package com.yuan;

public class NodeValue {
	String value;//自定义节点对象字符属性	
	public NodeValue(String value)
	{//构造器
		this.value=value;
	}
	public String getValue()
	{//value的Get方法
		return this.value;
	}
	@Override
	public String toString()
	{//重写toString方法
		return value;
	}

}
