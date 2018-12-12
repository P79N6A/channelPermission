package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * HP回传信息解析之后的 表 
 * 吴坤洋 2017-11-03
 * @author wukunyang
 *
 */
public class OrderrepairHPrecords implements Serializable{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		/***/
	    private Integer id;

	    /***/
	    private Integer siteId;

	    /**创建时间*/
	    private Integer addTime;

	    /**网单ID*/
	    private Integer orderProductId;

	    /**退货申请单ID*/
	    private Integer orderRepairId;

	    /**网点代码*/
	    private String netPointCode;

	    /**检验结果，符合条件；不符合条件*/
	    private Byte checkResult;

	    /**质检结果，正品；开箱正品；不良品*/
	    private Byte quality;

	    /**机编代码*/
	    private String machineNum;

	    /**质检员*/
	    private String inspector;

	    /**质检时间*/
	    private String inspectTime;

	    /**是否成功*/
	    private Byte success;

	    /**质检类型，一次/二次*/
	    private Byte checkType;

	    /**包装结果*/
	    private Byte packResult;

	    /**非正品买单方*/
	    private Byte response;

	    /**生成工单是否失败，无值代表成功*/
	    private Byte hpOrderFail;

	    /***/
	    private String source;

	    /**工单号*/
	    private String woId;

	    /**备注*/
	    private String hpOrderRemark;
	    
	    private String threeAppraisal;//是否已经成功发起三次鉴定。1是 0否s
	    
	    private String threeAppraisalDate;//发起三次鉴时间
	    
	    private String addTimeTs;//用来接收String类型的时间
	    
	    private int  repairsHPLogsId; //OrderRepairsHPLogs原始数据关联ID
	    
	    private String invoIceId; //发票id
	    private String twoAppraisal;//是否已经成功发起二次鉴定。1是 0否
	    private Date twoAppraisalDate;//发起二次鉴时间
	    private String cOrderSn;//扩展字段
	    
	    
	    
	    public String getcOrderSn() {
			return cOrderSn;
		}

		public void setcOrderSn(String cOrderSn) {
			this.cOrderSn = cOrderSn;
		}

		public String getTwoAppraisal() {
			return twoAppraisal;
		}

		public void setTwoAppraisal(String twoAppraisal) {
			this.twoAppraisal = twoAppraisal;
		}

		public Date getTwoAppraisalDate() {
			return twoAppraisalDate;
		}

		public void setTwoAppraisalDate(Date twoAppraisalDate) {
			this.twoAppraisalDate = twoAppraisalDate;
		}

		public String getThreeAppraisal() {
			return threeAppraisal;
		}

		public void setThreeAppraisal(String threeAppraisal) {
			this.threeAppraisal = threeAppraisal;
		}

		public String getThreeAppraisalDate() {
			return threeAppraisalDate;
		}

		public void setThreeAppraisalDate(String threeAppraisalDate) {
			this.threeAppraisalDate = threeAppraisalDate;
		}

		public String getInvoIceId() {
			return invoIceId;
		}

		public void setInvoIceId(String invoIceId) {
			this.invoIceId = invoIceId;
		}

		public int getRepairsHPLogsId() {
			return repairsHPLogsId;
		}

		public void setRepairsHPLogsId(int repairsHPLogsId) {
			this.repairsHPLogsId = repairsHPLogsId;
		}

		public String getWoId() {
			return woId;
		}

		public void setWoId(String woId) {
			this.woId = woId;
		}

		public String getAddTimeTs() {
			return addTimeTs;
		}

		public void setAddTimeTs(String addTimeTs) {
			this.addTimeTs = addTimeTs;
		}

		public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

		public Integer getSiteId() {
			return siteId;
		}

		public void setSiteId(Integer siteId) {
			this.siteId = siteId;
		}

		public Integer getAddTime() {
			return addTime;
		}

		public void setAddTime(Integer addTime) {
			this.addTime = addTime;
		}

		public Integer getOrderProductId() {
			return orderProductId;
		}

		public void setOrderProductId(Integer orderProductId) {
			this.orderProductId = orderProductId;
		}

		public Integer getOrderRepairId() {
			return orderRepairId;
		}

		public void setOrderRepairId(Integer orderRepairId) {
			this.orderRepairId = orderRepairId;
		}

		public String getNetPointCode() {
			return netPointCode;
		}

		public void setNetPointCode(String netPointCode) {
			this.netPointCode = netPointCode;
		}

		public Byte getCheckResult() {
			return checkResult;
		}

		public void setCheckResult(Byte checkResult) {
			this.checkResult = checkResult;
		}

		public Byte getQuality() {
			return quality;
		}

		public void setQuality(Byte quality) {
			this.quality = quality;
		}

		public String getMachineNum() {
			return machineNum;
		}

		public void setMachineNum(String machineNum) {
			this.machineNum = machineNum;
		}

		public String getInspector() {
			return inspector;
		}

		public void setInspector(String inspector) {
			this.inspector = inspector;
		}

		public String getInspectTime() {
			return inspectTime;
		}

		public void setInspectTime(String inspectTime) {
			this.inspectTime = inspectTime;
		}

		public Byte getSuccess() {
			return success;
		}

		public void setSuccess(Byte success) {
			this.success = success;
		}

		public Byte getCheckType() {
			return checkType;
		}

		public void setCheckType(Byte checkType) {
			this.checkType = checkType;
		}

		public Byte getPackResult() {
			return packResult;
		}

		public void setPackResult(Byte packResult) {
			this.packResult = packResult;
		}

		public Byte getResponse() {
			return response;
		}

		public void setResponse(Byte response) {
			this.response = response;
		}

		public Byte getHpOrderFail() {
			return hpOrderFail;
		}

		public void setHpOrderFail(Byte hpOrderFail) {
			this.hpOrderFail = hpOrderFail;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}


		public String getHpOrderRemark() {
			return hpOrderRemark;
		}

		public void setHpOrderRemark(String hpOrderRemark) {
			this.hpOrderRemark = hpOrderRemark;
		}


}
