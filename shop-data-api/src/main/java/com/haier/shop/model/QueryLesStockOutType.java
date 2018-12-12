package com.haier.shop.model;

import java.io.Serializable;
import java.util.List;

/**
 * les库存查询 cbs库存对账使用   出参
 * @author wangp-c
 *
 */
public class QueryLesStockOutType implements Serializable{

	private static final long serialVersionUID = -331639264320669441L;

	/**
	 * LES库存数---CBS使用
	 */
    private List<CBSKCType> cbskc;
    
    /**
     *  LES出入库流水---CBS使用
     */
    private List<CBSMXType> cbsmx;
    
    /**
     * LES库存明细---财务SAP使用
     */
    private List<COLLECTORType> collector;
    
    /**
     * LES出入库流水---财务SAP使用
     */
    private List<LTMXType> ltmx;

	public List<CBSKCType> getCbskc() {
		return cbskc;
	}

	public void setCbskc(List<CBSKCType> cbskc) {
		this.cbskc = cbskc;
	}

	public List<CBSMXType> getCbsmx() {
		return cbsmx;
	}

	public void setCbsmx(List<CBSMXType> cbsmx) {
		this.cbsmx = cbsmx;
	}

	public List<COLLECTORType> getCollector() {
		return collector;
	}

	public void setCollector(List<COLLECTORType> collector) {
		this.collector = collector;
	}

	public List<LTMXType> getLtmx() {
		return ltmx;
	}

	public void setLtmx(List<LTMXType> ltmx) {
		this.ltmx = ltmx;
	}
}
