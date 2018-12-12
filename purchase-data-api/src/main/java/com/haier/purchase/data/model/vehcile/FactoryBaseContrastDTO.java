package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author
 **/
public class FactoryBaseContrastDTO implements Serializable {

	private static final long serialVersionUID = -7913045118225917120L;
	private int id;
	private String factoryCode;// 工厂编码
	private String baseCode;// 基地编码
	private String baseName;// 基地名称

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

}
