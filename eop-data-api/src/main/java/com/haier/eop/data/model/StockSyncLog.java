package com.haier.eop.data.model;

import java.io.Serializable;
import java.util.Date;

public class StockSyncLog implements Serializable{
	  private static final long serialVersionUID = -3117914630224183116L;
    private Integer id;

    private Date addTime;

    private String sse;

    private String sku;

    private String scode;
    private String sourceproductId;

    private String sourceStoreCode;
    public String getSourceproductId() {
		return sourceproductId;
	}

	public void setSourceproductId(String sourceproductId) {
		this.sourceproductId = sourceproductId;
	}

	public String getSourceStoreCode() {
		return sourceStoreCode;
	}

	public void setSourceStoreCode(String sourceStoreCode) {
		this.sourceStoreCode = sourceStoreCode;
	}

	private Integer channelStockNum;

    private Integer ehaierStockNum;

    private Integer unconfirmStockNum;

    private Integer finalStockNum;

    private String stockSyncResult;
    private String systemInfo;
    private String textInfo;


    public String getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}

	public String getTextInfo() {
		return textInfo;
	}

	public void setTextInfo(String textInfo) {
		this.textInfo = textInfo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getSse() {
        return sse;
    }

    public void setSse(String sse) {
        this.sse = sse == null ? null : sse.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode == null ? null : scode.trim();
    }

    public Integer getChannelStockNum() {
        return channelStockNum;
    }

    public void setChannelStockNum(Integer channelStockNum) {
        this.channelStockNum = channelStockNum;
    }

    public Integer getEhaierStockNum() {
        return ehaierStockNum;
    }

    public void setEhaierStockNum(Integer ehaierStockNum) {
        this.ehaierStockNum = ehaierStockNum;
    }

    public Integer getUnconfirmStockNum() {
        return unconfirmStockNum;
    }

    public void setUnconfirmStockNum(Integer unconfirmStockNum) {
        this.unconfirmStockNum = unconfirmStockNum;
    }

    public Integer getFinalStockNum() {
        return finalStockNum;
    }

    public void setFinalStockNum(Integer finalStockNum) {
        this.finalStockNum = finalStockNum;
    }

    public String getStockSyncResult() {
        return stockSyncResult;
    }

    public void setStockSyncResult(String stockSyncResult) {
        this.stockSyncResult = stockSyncResult == null ? null : stockSyncResult.trim();
    }
}