package com.haier.purchase.data.model.vehcile;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MaterielInfoDTO extends BaseDTO {

    private static final long serialVersionUID = -385684683877697891L;
    private Long materielId; //ID主键自增
    private String materielCode; //物料编码
    private String materielName; //物料名称
    private String productGroupCode; //产品组编码
    private String productGroupName; //产品组名称
    private String brandCode; //品牌编码
    private String brandName; //品牌名称
    private double volume; //体积
    private String baseCode; //基地编码
    private String baseName; //基地名称
    private String deliveryToCode; //送达方名称
    private double price; //价格
    private double bateRate;//直扣扣率
    private Integer count;//商品数量
    private double sumPrice; //价税合计
    private double stockPrice; //采购价
    private double retailPrice; //供价,零售价
    private double actPrice; //执行价
    private double bateMoney; //单台台返金额
    private double verCode; //特价版本号
    private double verMoney; //特价单台差额
    private double proLossMoney; //工程单台损失
    private double lossRate; //折扣扣率
    private Integer isfl; //返利类型
    private Integer iskpo; //商用空调标志
    private double amount; //总金额
    private Integer status;//可下单状态(1、可下单 2、不可下单)
    private Integer	maxqty;//最大下单量
    private Integer minqty;//最小下单量

    public Long getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Long materielId) {
        this.materielId = materielId;
    }

    public String getMaterielCode() {
        return materielCode;
    }

    public void setMaterielCode(String materielCode) {
        this.materielCode = materielCode;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public String getProductGroupCode() {
        return productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getDeliveryToCode() {
        return deliveryToCode;
    }

    public void setDeliveryToCode(String deliveryToCode) {
        this.deliveryToCode = deliveryToCode;
    }

    public Integer getCount() {
        return count == null ? 0 : count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getBateRate() {
        return bateRate;
    }

    public void setBateRate(double bateRate) {
        this.bateRate = bateRate;
    }

    public double getSumPrice() {

        if (null != count ) {
            sumPrice = price * count;
            DecimalFormat df = new DecimalFormat("#.00");
            sumPrice=Double.parseDouble(df.format(sumPrice));
        } else {
            sumPrice = 0.00;
        }
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public double getBateMoney() {
        return bateMoney;
    }

    public void setBateMoney(double bateMoney) {
        this.bateMoney = bateMoney;
    }

    public double getVerCode() {
        return verCode;
    }

    public void setVerCode(double verCode) {
        this.verCode = verCode;
    }

    public double getVerMoney() {
        return verMoney;
    }

    public void setVerMoney(double verMoney) {
        this.verMoney = verMoney;
    }

    public double getProLossMoney() {
        return proLossMoney;
    }

    public void setProLossMoney(double proLossMoney) {
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

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMaxqty() {
		return maxqty;
	}

	public void setMaxqty(Integer maxqty) {
		this.maxqty = maxqty;
	}

	public Integer getMinqty() {
		return minqty;
	}

	public void setMinqty(Integer minqty) {
		this.minqty = minqty;
	}

	public double getTotalVolume() {
        Double totalVolume = new Double(0);
        if (null != this.count ) {
            totalVolume = this.volume * this.count;
            DecimalFormat df = new DecimalFormat("#.00");
            totalVolume=Double.parseDouble(df.format(totalVolume));
        }
        return totalVolume;
    }
}


