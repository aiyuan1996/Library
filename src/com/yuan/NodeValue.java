package com.yuan;

public class NodeValue {
	String value;//�Զ���ڵ�����ַ�����	
	public NodeValue(String value)
	{//������
		this.value=value;
	}
	public String getValue()
	{//value��Get����
		return this.value;
	}
	@Override
	public String toString()
	{//��дtoString����
		return value;
	}

}
