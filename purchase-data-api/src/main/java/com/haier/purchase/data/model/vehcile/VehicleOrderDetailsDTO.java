package com.haier.purchase.data.model.vehcile;

import java.util.Date;

public class VehicleOrderDetailsDTO extends BaseDTO {

    private static final long serialVersionUID = -3600369428291331996L;
    private Long itemId; //明细id自增主键
    private String orderNo; //整车订单编号
    private String itemNo; //行号（小单）
    private Integer rows; //明细行数
    private String status; //小单状态
    private String materielId; //物料ID
    private String materielCode; //物料编号
    private String materielName; //物料名称
    private String productGroup; //产品组
    private String productGroupName; //产品组名称
    private String brand; //品牌
    private Integer qty; //数量
    private double unitPrice; //含税开票价
    private double sumMoney; //价税合计
    private double stockPrice; //采购价
    private double retailPrice; //供价,零售价
    private double actPrice; //执行价
    private double bateRate; //直扣扣率
    private double bateMoney; //单台台返金额
    private String verCode; //特价版本号
    private Long verMoney; //特价单台差额
    private Long proLossMoney; //工程单台损失
    private double lossRate; //折扣扣率
    private Integer isfl; //返利类型
    private Integer iskpo; //商用空调标志
    private double amount; //总金额
    private double volume; //体积
    private double totalVolume; //总体积
    private String relevantOrderNo1;
    private String paymentCode; //付款方编码
    private String paymentName; //付款方名称
    private String createBy; //创建人
    private Date createTime; //创建时间
    private String lastUpdateBy; //最后修改人
    private Date lastUpdateTime; //最后修改时间
    private String vbelnDn1;//一次85单号
    private String vbelnDn5;//二次85单号
    private String vbelnSpare;//预约LBX备用DN
    private String vbelnStatus;//85单号状态
    private String lbx;
    private Date zspdt;//lbx入库时间
    private String lbxErrMsg; //lbx错误信息
    
    private Date startOrderTime;//查询用，用来关联父表时间
    private Date endOrderTime;//查询用，用来关联父表时间

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaterielCode() {
        return materielCode;
    }

    public void setMaterielCode(String materielCode) {
        this.materielCode = materielCode;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getActPrice() {
        return actPrice;
    }

    public void setActPrice(double actPrice) {
        this.actPrice = actPrice;
    }

    public double getBateRate() {
        return bateRate;
    }

    public void setBateRate(double bateRate) {
        this.bateRate = bateRate;
    }

    public double getBateMoney() {
        return bateMoney;
    }

    public void setBateMoney(double bateMoney) {
        this.bateMoney = bateMoney;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public Long getVerMoney() {
        return verMoney;
    }

    public void setVerMoney(Long verMoney) {
        this.verMoney = verMoney;
    }

    public Long getProLossMoney() {
        return proLossMoney;
    }

    public void setProLossMoney(Long proLossMoney) {
        this.proLossMoney = proLossMoney;
    }

    public double getLossRate() {
        return lossRate;
    }

    public void setLossRate(double lossRate) {
        this.lossRate = lossRate;
    }

    public Integer getIsfl() {
        return isfl;
    }

    public void setIsfl(Integer isfl) {
        this.isfl = isfl;
    }

    public Integer getIskpo() {
        return iskpo;
    }

    public void setIskpo(Integer iskpo) {
        this.iskpo = iskpo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }

    public String getRelevantOrderNo1() {
        return relevantOrderNo1;
    }

    public void setRelevantOrderNo1(String relevantOrderNo1) {
        this.relevantOrderNo1 = relevantOrderNo1;
    }
	public String getVbelnDn1() {
		return vbelnDn1;
	}

	public void setVbelnDn1(String vbelnDn1) {
		this.vbelnDn1 = vbelnDn1;
	}

	public String getVbelnStatus() {
		return vbelnStatus;
	}

	public void setVbelnStatus(String vbelnStatus) {
		this.vbelnStatus = vbelnStatus;
	}

	public String getLbx() {
		return lbx;
	}

	public void setLbx(String lbx) {
		this.lbx = lbx;
	}

	public Date getZspdt() {
		return zspdt;
	}

	public void setZspdt(Date zspdt) {
		this.zspdt = zspdt;
	}

	public String getVbelnDn5() {
		return vbelnDn5;
	}

	public void setVbelnDn5(String vbelnDn5) {
		this.vbelnDn5 = vbelnDn5;
	}

	public String getVbelnSpare() {
		return vbelnSpare;
	}

	public void setVbelnSpare(String vbelnSpare) {
		this.vbelnSpare = vbelnSpare;
	}

    public String getLbxErrMsg() {
        return lbxErrMsg;
    }

    public void setLbxErrMsg(String lbxErrMsg) {
        this.lbxErrMsg = lbxErrMsg;
    }

	public Date getStartOrderTime() {
		return startOrderTime;
	}

	public void setStartOrderTime(Date startOrderTime) {
		this.startOrderTime = startOrderTime;
	}

	public Date getEndOrderTime() {
		return endOrderTime;
	}

	public void setEndOrderTime(Date endOrderTime) {
		this.endOrderTime = endOrderTime;
	}
}


