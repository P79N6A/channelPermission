package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * HP���ز���Ʒ��Ϣʵ����
 * ������ 2017-11-21
 * @author wukunyang
 *
 */
public class OrderhpRejectionLogs implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3792333550080107347L;


	/**
	 * 
	 */



	/***/
    private Integer id;

   
    private String soNum;

 
    private String vbelnso;

    
    private String productTypeId;

    
    private String productNo;

   
    private Integer countNum;

   
    private String inwhId;

    private String inwhDate;

    private String keyProductNo;

    private String mainSku;

    private Integer subCount;

    private String channelOrderSn;

    private String channel;

    private String secCode;

    private Date hpLesDate;

    private String respDivision;

    private String hpLesId;
    
    private String kw;
    
    private String depCharge;
    
    private String repairHPRecordsID;//HP回传鉴定表主键
    
    private String virtualEntryState;//标识虚入虚出 处理状态 :默认:default   虚入成功:enterSuccess  虚出成功:outSuccess'

    
    public String getVirtualEntryState() {
		return virtualEntryState;
	}

	public void setVirtualEntryState(String virtualEntryState) {
		this.virtualEntryState = virtualEntryState;
	}

	public String getRepairHPRecordsID() {
		return repairHPRecordsID;
	}

	public void setRepairHPRecordsID(String repairHPRecordsID) {
		this.repairHPRecordsID = repairHPRecordsID;
	}

	public String getHpLesId() {
		return hpLesId;
	}

	public void setHpLesId(String hpLesId) {
		this.hpLesId = hpLesId;
	}

	public String getRespDivision() {
		return respDivision;
	}

	public void setRespDivision(String respDivision) {
		this.respDivision = respDivision;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getDepCharge() {
		return depCharge;
	}

	public void setDepCharge(String depCharge) {
		this.depCharge = depCharge;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoNum() {
        return soNum;
    }

    public void setSoNum(String soNum) {
        this.soNum = soNum == null ? null : soNum.trim();
    }

    public String getVbelnso() {
        return vbelnso;
    }

    public void setVbelnso(String vbelnso) {
        this.vbelnso = vbelnso == null ? null : vbelnso.trim();
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId == null ? null : productTypeId.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public String getInwhId() {
        return inwhId;
    }

    public void setInwhId(String inwhId) {
        this.inwhId = inwhId == null ? null : inwhId.trim();
    }

    public String getInwhDate() {
        return inwhDate;
    }

    public void setInwhDate(String inwhDate) {
        this.inwhDate = inwhDate == null ? null : inwhDate.trim();
    }

    public String getKeyProductNo() {
        return keyProductNo;
    }

    public void setKeyProductNo(String keyProductNo) {
        this.keyProductNo = keyProductNo == null ? null : keyProductNo.trim();
    }

    public String getMainSku() {
        return mainSku;
    }

    public void setMainSku(String mainSku) {
        this.mainSku = mainSku == null ? null : mainSku.trim();
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public String getChannelOrderSn() {
        return channelOrderSn;
    }

    public void setChannelOrderSn(String channelOrderSn) {
        this.channelOrderSn = channelOrderSn == null ? null : channelOrderSn.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode == null ? null : secCode.trim();
    }

    public Date getHpLesDate() {
        return hpLesDate;
    }

    public void setHpLesDate(Date hpLesDate) {
        this.hpLesDate = hpLesDate;
    }


}