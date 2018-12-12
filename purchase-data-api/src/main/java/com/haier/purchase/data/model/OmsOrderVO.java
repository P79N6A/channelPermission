package com.haier.purchase.data.model;

import java.io.Serializable;

import com.haier.common.util.StringUtil;

public class OmsOrderVO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class QueryCondition implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String exclude_flag;
        private int    min_flow_flag;
        private int    max_flow_flag;
        private String orderSoCode;
        private String md5;
        private String addition_contidition;

        public String getExclude_flag() {
            return exclude_flag;
        }

        public void setExclude_flag(String exclude_flag) {
            this.exclude_flag = exclude_flag;
        }

        public int getMin_flow_flag() {
            return min_flow_flag;
        }

        public void setMin_flow_flag(int min_flow_flag) {
            this.min_flow_flag = min_flow_flag;
        }

        public int getMax_flow_flag() {
            return max_flow_flag;
        }

        public void setMax_flow_flag(int max_flow_flag) {
            this.max_flow_flag = max_flow_flag;
        }

        public String getOrderSoCode() {
            return orderSoCode;
        }

        public void setOrderSoCode(String orderSoCode) {
            this.orderSoCode = orderSoCode;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getAddition_contidition() {
            return addition_contidition;
        }

        public void setAddition_contidition(String addition_contidition) {
            this.addition_contidition = addition_contidition;
        }

    }

    /**
     * 当前页页码
     */
    private String  curPageNo;
    /**
     * 总页数
     */
    private String  totalPage;
    /**
     * OMS订单号
     */
    private String  orderSoCode;
    /**
     * 预测内外的标记（CBS应该用不到）
     */
    private String  forecastFlang;
    /**
     * 订单状态
     */
    private String  orderState;
    /**
     * 最晚到货时间
     */
    private String  latestArrivalTime;
    /**
     * 计划发货日期
     */
    private String  plannedShipDate;
    /**
     * 承诺到货日期
     */
    private String  promisedArrivalDate;
    /**
     * 实际发货日期
     */
    private String  actualShipDate;
    /**
     * DN创建日期
     */
    private String  reqArrivalDate;
    /**
     * 工贸收货日期
     */
    private String  transitArrivalDate;
    /**
     * 工贸发货日期
     */
    private String  tradeSendDate;
    /**
     * 客户签收日期
     */
    private String  signDate;
    /**
     * 返单日期
     */
    private String  podDate;
    /**
     * 预计到货日期
     */
    private String  oesPredictRevDate;
    /**
     * 签收数量
     */
    private String  custRevQty;
    /**
     * 不通过原因
     */
    private String  cancelReason;
    /**
     * GVS订单号
     */
    private String  gvsOrderCode;
    /**
     * DN号
     */
    private String  gvsDn;
    /**
     * 电商采购单号
     */
    private String  custOrderCode;
    /**
     * 确认订购时间
     */
    private String  confirmDate;
    /**
     * 提交时间
     */
    private String  submitDate;
    /**
     * 电商渠道
     */
    private String  eChannel;
    /**
     * 最晚离基地日期
     */
    private String  latest_leave_base_date;
    /**
     * 运单号
     */
    private String  transit_code;
    /**
     * 事业部发货工厂名称
     */
    private String  made_fectory_name;
    /**
     * 事业部发货工厂编码
     */
    private String  made_fectory_code;
    /**
     * 订单类型
     */
    private String  order_type_name;
    /**
     * OMS订单状态 00 创建中 01 草稿 02 待审核 03 已冻结 04 待评审 05 待确认 06 等待发货 07 事业部已发货 08
     * 等待工贸发货 09 工贸已发货 10 到达客户 11 已签收 12 已返单F 13 已开发票 14 发票已打印 15 票据已签收 16
     * 已撤销待确认 17 已关闭 18 已取消 20 订单未满足 21 已撤消 22 工贸库存满足 23 直发库存作废 80 已提交 81 已审核 82
     * 24小时承诺 83 基地整车直发到客户在途 84 产品总监待审核 85 供应链待审核
     */
    private String  status;
    /**
     * CBS流程标识
     */
    private Integer flow_flag;
    /**
     * 可变化字段的Hash值
     */
    private String  md5;
    /**
     * 系列
     */
    private String  prodseriescode;
    /**
     * J单号
     */
    private String  custpodetailcode;

    /**
     * 电商采购单WP单号(与custOrderCode同)
     */
    private String  wp_order_id;

    public String getCurPageNo() {
        return curPageNo;
    }

    public void setCurPageNo(String curPageNo) {
        this.curPageNo = curPageNo;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getOrderSoCode() {
        return orderSoCode;
    }

    public void setOrderSoCode(String orderSoCode) {
        this.orderSoCode = orderSoCode;
    }

    public String getForecastFlang() {
        return forecastFlang;
    }

    public void setForecastFlang(String forecastFlang) {
        this.forecastFlang = forecastFlang;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getLatestArrivalTime() {
        return latestArrivalTime;
    }

    public void setLatestArrivalTime(String latestArrivalTime) {
        this.latestArrivalTime = latestArrivalTime;
    }

    public String getPlannedShipDate() {
        return plannedShipDate;
    }

    public void setPlannedShipDate(String plannedShipDate) {
        this.plannedShipDate = plannedShipDate;
    }

    public String getPromisedArrivalDate() {
        return promisedArrivalDate;
    }

    public void setPromisedArrivalDate(String promisedArrivalDate) {
        this.promisedArrivalDate = promisedArrivalDate;
    }

    public String getActualShipDate() {
        return actualShipDate;
    }

    public void setActualShipDate(String actualShipDate) {
        this.actualShipDate = actualShipDate;
    }

    public String getReqArrivalDate() {
        return reqArrivalDate;
    }

    public void setReqArrivalDate(String reqArrivalDate) {
        this.reqArrivalDate = reqArrivalDate;
    }

    public String getTransitArrivalDate() {
        return transitArrivalDate;
    }

    public void setTransitArrivalDate(String transitArrivalDate) {
        this.transitArrivalDate = transitArrivalDate;
    }

    public String getTradeSendDate() {
        return tradeSendDate;
    }

    public void setTradeSendDate(String tradeSendDate) {
        this.tradeSendDate = tradeSendDate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getPodDate() {
        return podDate;
    }

    public void setPodDate(String podDate) {
        this.podDate = podDate;
    }

    public String getOesPredictRevDate() {
        return oesPredictRevDate;
    }

    public void setOesPredictRevDate(String oesPredictRevDate) {
        this.oesPredictRevDate = oesPredictRevDate;
    }

    public String getCustRevQty() {
        return custRevQty;
    }

    public void setCustRevQty(String custRevQty) {
        this.custRevQty = custRevQty;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getGvsOrderCode() {
        return gvsOrderCode;
    }

    public void setGvsOrderCode(String gvsOrderCode) {
        this.gvsOrderCode = gvsOrderCode;
    }

    public String getGvsDn() {
        return gvsDn;
    }

    public void setGvsDn(String gvsDn) {
        this.gvsDn = gvsDn;
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String geteChannel() {
        return eChannel;
    }

    public void seteChannel(String eChannel) {
        this.eChannel = eChannel;
    }

    public String getLatest_leave_base_date() {
        return latest_leave_base_date;
    }

    public void setLatest_leave_base_date(String latest_leave_base_date) {
        this.latest_leave_base_date = latest_leave_base_date;
    }

    public String getTransit_code() {
        return transit_code;
    }

    public void setTransit_code(String transit_code) {
        this.transit_code = transit_code;
    }

    public String getMade_fectory_name() {
        return made_fectory_name;
    }

    public void setMade_fectory_name(String made_fectory_name) {
        this.made_fectory_name = made_fectory_name;
    }

    public String getMade_fectory_code() {
        return made_fectory_code;
    }

    public void setMade_fectory_code(String made_fectory_code) {
        this.made_fectory_code = made_fectory_code;
    }

    public String getOrder_type_name() {
        return order_type_name;
    }

    public void setOrder_type_name(String order_type_name) {
        this.order_type_name = order_type_name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWp_order_id() {
        return wp_order_id;
    }

    public void setWp_order_id(String wp_order_id) {
        this.wp_order_id = wp_order_id;
    }

    public Integer getFlow_flag() {
        return flow_flag;
    }

    public String getProdseriescode() {
        return prodseriescode;
    }

    public void setProdseriescode(String prodseriescode) {
        this.prodseriescode = prodseriescode;
    }

    public String getCustpodetailcode() {
        return custpodetailcode;
    }

    public void setCustpodetailcode(String custpodetailcode) {
        this.custpodetailcode = custpodetailcode;
    }

    @Override
    public String toString() {
        String s = "";
        s += "os:" + orderState;
        s += " lat:" + latestArrivalTime;
        s += " psd:" + plannedShipDate;
        s += " pad:" + promisedArrivalDate;
        s += " asd:" + actualShipDate;
        s += " rad:" + reqArrivalDate;
        s += " tad:" + transitArrivalDate;
        s += " tsd:" + tradeSendDate;
        s += " sd:" + signDate;
        s += " pd:" + podDate;
        s += " opr:" + oesPredictRevDate;
        s += " crq:" + custRevQty;
        s += " cr:" + cancelReason;
        s += " goc:" + gvsOrderCode;
        s += " gdn:" + gvsDn;
        s += " cd:" + confirmDate;
        s += " smd:" + submitDate;
        s += " ec:" + eChannel;
        s += " llbd:" + latest_leave_base_date;
        s += " tc:" + transit_code;
        s += " mfn:" + made_fectory_name;
        s += " mfc:" + made_fectory_code;
        s += " otn:" + order_type_name;
        s += " prodseriescode:" + prodseriescode;
        s += " custpodetailcode:" + custpodetailcode;
        return s;
    }

    public void setFlow_flag(Integer flow_flag) {
        this.flow_flag = flow_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OmsOrderVO) {
            OmsOrderVO ov = (OmsOrderVO) obj;
            if ((StringUtil.isEmpty(ov.orderState) && !StringUtil.isEmpty(orderState))
                || (!StringUtil.isEmpty(ov.orderState) && StringUtil.isEmpty(orderState))
                || (!StringUtil.isEmpty(ov.orderState) && !ov.orderState.equals(orderState))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.latestArrivalTime) && !StringUtil
                .isEmpty(latestArrivalTime))
                       || (!StringUtil.isEmpty(ov.latestArrivalTime) && StringUtil
                           .isEmpty(latestArrivalTime))
                       || (!StringUtil.isEmpty(ov.latestArrivalTime) && !ov.latestArrivalTime
                           .equals(latestArrivalTime))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.plannedShipDate) && !StringUtil
                .isEmpty(plannedShipDate))
                       || (!StringUtil.isEmpty(ov.plannedShipDate) && StringUtil
                           .isEmpty(plannedShipDate))
                       || (!StringUtil.isEmpty(ov.plannedShipDate) && !ov.plannedShipDate
                           .equals(plannedShipDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.promisedArrivalDate) && !StringUtil
                .isEmpty(promisedArrivalDate))
                       || (!StringUtil.isEmpty(ov.promisedArrivalDate) && StringUtil
                           .isEmpty(promisedArrivalDate))
                       || (!StringUtil.isEmpty(ov.promisedArrivalDate) && !ov.promisedArrivalDate
                           .equals(promisedArrivalDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.actualShipDate) && !StringUtil
                .isEmpty(actualShipDate))
                       || (!StringUtil.isEmpty(ov.actualShipDate) && StringUtil
                           .isEmpty(actualShipDate))
                       || (!StringUtil.isEmpty(ov.actualShipDate) && !ov.actualShipDate
                           .equals(actualShipDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.reqArrivalDate) && !StringUtil
                .isEmpty(reqArrivalDate))
                       || (!StringUtil.isEmpty(ov.reqArrivalDate) && StringUtil
                           .isEmpty(reqArrivalDate))
                       || (!StringUtil.isEmpty(ov.reqArrivalDate) && !ov.reqArrivalDate
                           .equals(reqArrivalDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.transitArrivalDate) && !StringUtil
                .isEmpty(transitArrivalDate))
                       || (!StringUtil.isEmpty(ov.transitArrivalDate) && StringUtil
                           .isEmpty(transitArrivalDate))
                       || (!StringUtil.isEmpty(ov.transitArrivalDate) && !ov.transitArrivalDate
                           .equals(transitArrivalDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.tradeSendDate) && !StringUtil.isEmpty(tradeSendDate))
                       || (!StringUtil.isEmpty(ov.tradeSendDate) && StringUtil
                           .isEmpty(tradeSendDate))
                       || (!StringUtil.isEmpty(ov.tradeSendDate) && !ov.tradeSendDate
                           .equals(tradeSendDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.signDate) && !StringUtil.isEmpty(signDate))
                       || (!StringUtil.isEmpty(ov.signDate) && StringUtil.isEmpty(signDate))
                       || (!StringUtil.isEmpty(ov.signDate) && !ov.signDate.equals(signDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.podDate) && !StringUtil.isEmpty(podDate))
                       || (!StringUtil.isEmpty(ov.podDate) && StringUtil.isEmpty(podDate))
                       || (!StringUtil.isEmpty(ov.podDate) && !ov.podDate.equals(podDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.oesPredictRevDate) && !StringUtil
                .isEmpty(oesPredictRevDate))
                       || (!StringUtil.isEmpty(ov.oesPredictRevDate) && StringUtil
                           .isEmpty(oesPredictRevDate))
                       || (!StringUtil.isEmpty(ov.oesPredictRevDate) && !ov.oesPredictRevDate
                           .equals(oesPredictRevDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.custRevQty) && !StringUtil.isEmpty(custRevQty))
                       || (!StringUtil.isEmpty(ov.custRevQty) && StringUtil.isEmpty(custRevQty))
                       || (!StringUtil.isEmpty(ov.custRevQty) && Double.parseDouble(ov.custRevQty) != Double
                           .parseDouble(custRevQty))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.cancelReason) && !StringUtil.isEmpty(cancelReason))
                       || (!StringUtil.isEmpty(ov.cancelReason) && StringUtil.isEmpty(cancelReason))
                       || (!StringUtil.isEmpty(ov.cancelReason) && !ov.cancelReason
                           .equals(cancelReason))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.gvsOrderCode) && !StringUtil.isEmpty(gvsOrderCode))
                       || (!StringUtil.isEmpty(ov.gvsOrderCode) && StringUtil.isEmpty(gvsOrderCode))
                       || (!StringUtil.isEmpty(ov.gvsOrderCode) && !ov.gvsOrderCode
                           .equals(gvsOrderCode))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.gvsDn) && !StringUtil.isEmpty(gvsDn))
                       || (!StringUtil.isEmpty(ov.gvsDn) && StringUtil.isEmpty(gvsDn))
                       || (!StringUtil.isEmpty(ov.gvsDn) && !ov.gvsDn.equals(gvsDn))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.confirmDate) && !StringUtil.isEmpty(confirmDate))
                       || (!StringUtil.isEmpty(ov.confirmDate) && StringUtil.isEmpty(confirmDate))
                       || (!StringUtil.isEmpty(ov.confirmDate) && !ov.confirmDate
                           .equals(confirmDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.submitDate) && !StringUtil.isEmpty(submitDate))
                       || (!StringUtil.isEmpty(ov.submitDate) && StringUtil.isEmpty(submitDate))
                       || (!StringUtil.isEmpty(ov.submitDate) && !ov.submitDate.equals(submitDate))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.eChannel) && !StringUtil.isEmpty(eChannel))
                       || (!StringUtil.isEmpty(ov.eChannel) && StringUtil.isEmpty(eChannel))
                       || (!StringUtil.isEmpty(ov.eChannel) && !ov.eChannel.equals(eChannel))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.latest_leave_base_date) && !StringUtil
                .isEmpty(latest_leave_base_date))
                       || (!StringUtil.isEmpty(ov.latest_leave_base_date) && StringUtil
                           .isEmpty(latest_leave_base_date))
                       || (!StringUtil.isEmpty(ov.latest_leave_base_date) && !ov.latest_leave_base_date
                           .equals(latest_leave_base_date))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.transit_code) && !StringUtil.isEmpty(transit_code))
                       || (!StringUtil.isEmpty(ov.transit_code) && StringUtil.isEmpty(transit_code))
                       || (!StringUtil.isEmpty(ov.transit_code) && !ov.transit_code
                           .equals(transit_code))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.made_fectory_name) && !StringUtil
                .isEmpty(made_fectory_name))
                       || (!StringUtil.isEmpty(ov.made_fectory_name) && StringUtil
                           .isEmpty(made_fectory_name))
                       || (!StringUtil.isEmpty(ov.made_fectory_name) && !ov.made_fectory_name
                           .equals(made_fectory_name))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.made_fectory_code) && !StringUtil
                .isEmpty(made_fectory_code))
                       || (!StringUtil.isEmpty(ov.made_fectory_code) && StringUtil
                           .isEmpty(made_fectory_code))
                       || (!StringUtil.isEmpty(ov.made_fectory_code) && !ov.made_fectory_code
                           .equals(made_fectory_code))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.order_type_name) && !StringUtil
                .isEmpty(order_type_name))
                       || (!StringUtil.isEmpty(ov.order_type_name) && StringUtil
                           .isEmpty(order_type_name))
                       || (!StringUtil.isEmpty(ov.order_type_name) && !ov.order_type_name
                           .equals(order_type_name))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.prodseriescode) && !StringUtil
                .isEmpty(prodseriescode))
                       || (!StringUtil.isEmpty(ov.prodseriescode) && StringUtil
                           .isEmpty(prodseriescode))
                       || (!StringUtil.isEmpty(ov.prodseriescode) && !ov.prodseriescode
                           .equals(prodseriescode))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.custpodetailcode) && !StringUtil
                .isEmpty(custpodetailcode))
                       || (!StringUtil.isEmpty(ov.custpodetailcode) && StringUtil
                           .isEmpty(custpodetailcode))
                       || (!StringUtil.isEmpty(ov.custpodetailcode) && !ov.custpodetailcode
                           .equals(custpodetailcode))) {
                return false;
            } else if ((StringUtil.isEmpty(ov.status) && !StringUtil.isEmpty(status))
                       || (!StringUtil.isEmpty(ov.status) && StringUtil.isEmpty(status))
                       || (!StringUtil.isEmpty(ov.status) && !ov.status.equals(status))) {
                return false;
            } else if (flow_flag != null && flow_flag != ov.flow_flag) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return -1;
    }

}
