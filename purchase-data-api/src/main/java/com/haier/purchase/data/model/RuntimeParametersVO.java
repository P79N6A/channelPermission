package com.haier.purchase.data.model;

import java.io.Serializable;

public class RuntimeParametersVO implements Serializable {
	private String key;
	private String value;
	private String wp_order_id;

	public String getWp_order_id() {
		return wp_order_id;
	}

	public void setWp_order_id(String wp_order_id) {
		this.wp_order_id = wp_order_id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
