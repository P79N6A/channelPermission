package com.haier.order.util;

public interface Serializable {
	byte[] serialize();
	
	void unserialize(byte[] ss);
}
