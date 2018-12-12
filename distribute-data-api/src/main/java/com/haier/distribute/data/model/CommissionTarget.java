package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.channels.ScatteringByteChannel;
import java.util.Date;

public class CommissionTarget implements Serializable {
	  /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;

	public Integer getId() {
		return id;
	}

	public BigDecimal getQuarterStandardReward() {
		return quarterStandardReward;
	}

	public void setQuarterStandardReward(BigDecimal quarterStandardReward) {
		this.quarterStandardReward = quarterStandardReward;
	}

	public BigDecimal getYearStandardReward() {
		return yearStandardReward;
	}

	public void setYearStandardReward(BigDecimal yearStandardReward) {
		this.yearStandardReward = yearStandardReward;
	}

	public void setId(Integer id) {
		this.id = id;
	}


    private Integer id;

    private Integer channelId;

    private Integer brandId;

    private String categoryId;

    private Integer policyYear;

    private BigDecimal targetValue1;

    private BigDecimal targetValue2;

    private BigDecimal targetValue3;

    private BigDecimal targetValue4;

    private BigDecimal targetValue5;

    private BigDecimal targetValue6;

    private BigDecimal targetValue7;

    private BigDecimal targetValue8;

    private BigDecimal targetValue9;

    private BigDecimal targetValue10;

    private BigDecimal targetValue11;

    private BigDecimal targetValue12;

    private BigDecimal targetValueSum;

    private BigDecimal targetPolicy1;
    private BigDecimal targetPolicy2;
    private BigDecimal targetPolicy3;
    private BigDecimal targetPolicy4;
    private BigDecimal targetPolicy5;
    private BigDecimal targetPolicy6;
    private BigDecimal targetPolicy7;
    private BigDecimal targetPolicy8;
    private BigDecimal targetPolicy9;
    private BigDecimal targetPolicy10;
    private BigDecimal targetPolicy11;
    private BigDecimal targetPolicy12;
    public BigDecimal getTargetPolicy1() {
		return targetPolicy1;
	}

	public void setTargetPolicy1(BigDecimal targetPolicy1) {
		this.targetPolicy1 = targetPolicy1;
	}

	public BigDecimal getTargetPolicy2() {
		return targetPolicy2;
	}

	public void setTargetPolicy2(BigDecimal targetPolicy2) {
		this.targetPolicy2 = targetPolicy2;
	}

	public BigDecimal getTargetPolicy3() {
		return targetPolicy3;
	}

	public void setTargetPolicy3(BigDecimal targetPolicy3) {
		this.targetPolicy3 = targetPolicy3;
	}

	public BigDecimal getTargetPolicy4() {
		return targetPolicy4;
	}

	public void setTargetPolicy4(BigDecimal targetPolicy4) {
		this.targetPolicy4 = targetPolicy4;
	}

	public BigDecimal getTargetPolicy5() {
		return targetPolicy5;
	}

	public void setTargetPolicy5(BigDecimal targetPolicy5) {
		this.targetPolicy5 = targetPolicy5;
	}

	public BigDecimal getTargetPolicy6() {
		return targetPolicy6;
	}

	public void setTargetPolicy6(BigDecimal targetPolicy6) {
		this.targetPolicy6 = targetPolicy6;
	}

	public BigDecimal getTargetPolicy7() {
		return targetPolicy7;
	}

	public void setTargetPolicy7(BigDecimal targetPolicy7) {
		this.targetPolicy7 = targetPolicy7;
	}

	public BigDecimal getTargetPolicy8() {
		return targetPolicy8;
	}

	public void setTargetPolicy8(BigDecimal targetPolicy8) {
		this.targetPolicy8 = targetPolicy8;
	}

	public BigDecimal getTargetPolicy9() {
		return targetPolicy9;
	}

	public void setTargetPolicy9(BigDecimal targetPolicy9) {
		this.targetPolicy9 = targetPolicy9;
	}

	public BigDecimal getTargetPolicy10() {
		return targetPolicy10;
	}

	public void setTargetPolicy10(BigDecimal targetPolicy10) {
		this.targetPolicy10 = targetPolicy10;
	}

	public BigDecimal getTargetPolicy11() {
		return targetPolicy11;
	}

	public void setTargetPolicy11(BigDecimal targetPolicy11) {
		this.targetPolicy11 = targetPolicy11;
	}

	public BigDecimal getTargetPolicy12() {
		return targetPolicy12;
	}

	public void setTargetPolicy12(BigDecimal targetPolicy12) {
		this.targetPolicy12 = targetPolicy12;
	}


	private BigDecimal quarterStandardReward;
    private BigDecimal yearStandardReward;
    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;

    private String channelName;
    private String brandName;
    private String cateName;
    private String channelType;
    private String distributionName;//分销表的名称
    private String col01;
    private String col02;
    private String col03;
    private String col04;
    private String col05;
    private String col06;
    private String col07;
    private String col08;
    private String col09;
    private String col10;
    private String col11;
    public String getCol01() {
		return col01;
	}

	public void setCol01(String col01) {
		this.col01 = col01;
	}

	public String getCol02() {
		return col02;
	}

	public void setCol02(String col02) {
		this.col02 = col02;
	}

	public String getCol03() {
		return col03;
	}

	public void setCol03(String col03) {
		this.col03 = col03;
	}

	public String getCol04() {
		return col04;
	}

	public void setCol04(String col04) {
		this.col04 = col04;
	}

	public String getCol05() {
		return col05;
	}

	public void setCol05(String col05) {
		this.col05 = col05;
	}

	public String getCol06() {
		return col06;
	}

	public void setCol06(String col06) {
		this.col06 = col06;
	}

	public String getCol07() {
		return col07;
	}

	public void setCol07(String col07) {
		this.col07 = col07;
	}

	public String getCol08() {
		return col08;
	}

	public void setCol08(String col08) {
		this.col08 = col08;
	}

	public String getCol09() {
		return col09;
	}

	public void setCol09(String col09) {
		this.col09 = col09;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15;
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16;
	}

	public String getCol17() {
		return col17;
	}

	public void setCol17(String col17) {
		this.col17 = col17;
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18;
	}

	public String getCol19() {
		return col19;
	}

	public void setCol19(String col19) {
		this.col19 = col19;
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20;
	}



	private String col12;
    private String col13;
    private String col14;
    private String col15;
    private String col16;
    private String col17;
    private String col18;
    private String col19;
    private String col20;
	public String getDistributionName() {
		return distributionName;
	}

	public void setDistributionName(String distributionName) {
		this.distributionName = distributionName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}



	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getPolicyYear() {
		return policyYear;
	}

	public void setPolicyYear(Integer policyYear) {
		this.policyYear = policyYear;
	}

	public BigDecimal getTargetValue1() {
		return targetValue1;
	}

	public void setTargetValue1(BigDecimal targetValue1) {
		this.targetValue1 = targetValue1;
	}

	public BigDecimal getTargetValue2() {
		return targetValue2;
	}

	public void setTargetValue2(BigDecimal targetValue2) {
		this.targetValue2 = targetValue2;
	}

	public BigDecimal getTargetValue3() {
		return targetValue3;
	}

	public void setTargetValue3(BigDecimal targetValue3) {
		this.targetValue3 = targetValue3;
	}

	public BigDecimal getTargetValue4() {
		return targetValue4;
	}

	public void setTargetValue4(BigDecimal targetValue4) {
		this.targetValue4 = targetValue4;
	}

	public BigDecimal getTargetValue5() {
		return targetValue5;
	}

	public void setTargetValue5(BigDecimal targetValue5) {
		this.targetValue5 = targetValue5;
	}

	public BigDecimal getTargetValue6() {
		return targetValue6;
	}

	public void setTargetValue6(BigDecimal targetValue6) {
		this.targetValue6 = targetValue6;
	}


	public BigDecimal getTargetValue8() {
		return targetValue8;
	}

	public BigDecimal getTargetValue7() {
		return targetValue7;
	}

	public void setTargetValue7(BigDecimal targetValue7) {
		this.targetValue7 = targetValue7;
	}

	public void setTargetValue8(BigDecimal targetValue8) {
		this.targetValue8 = targetValue8;
	}

	public BigDecimal getTargetValue9() {
		return targetValue9;
	}

	public void setTargetValue9(BigDecimal targetValue9) {
		this.targetValue9 = targetValue9;
	}

	public BigDecimal getTargetValue10() {
		return targetValue10;
	}

	public void setTargetValue10(BigDecimal targetValue10) {
		this.targetValue10 = targetValue10;
	}

	public BigDecimal getTargetValue11() {
		return targetValue11;
	}

	public void setTargetValue11(BigDecimal targetValue11) {
		this.targetValue11 = targetValue11;
	}

	public BigDecimal getTargetValue12() {
		return targetValue12;
	}

	public void setTargetValue12(BigDecimal targetValue12) {
		this.targetValue12 = targetValue12;
	}

	public BigDecimal getTargetValueSum() {
		return targetValueSum;
	}

	public void setTargetValueSum(BigDecimal targetValueSum) {
		this.targetValueSum = targetValueSum;
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

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

   

   
}