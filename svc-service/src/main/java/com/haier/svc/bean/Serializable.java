package com.haier.svc.bean;

public interface Serializable {
	byte[] serialize();
	
	void unserialize(byte[] ss);
}
