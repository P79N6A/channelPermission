package com.haier.svc.bean;

public class UnSerializeResult {
	public Object value;
	public int		hv;
	
	public UnSerializeResult() {
	}
	
	public UnSerializeResult(Object value, int hv) {
		this.value = value;
		this.hv = hv;
	}
}
