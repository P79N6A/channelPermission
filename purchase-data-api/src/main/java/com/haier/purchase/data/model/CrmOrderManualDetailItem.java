/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *                       
 * @Filename: CrmOrderManualDetailItem.java
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */
public class CrmOrderManualDetailItem implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7596896338273075916L;
    private String            wp_order_id;                             //销售单号
    private String            po_id;                                   //po号
    private String            materials_id;                            //物料编码
    private String            materials_desc;                          //型号
    private String            product_group_id;                        //产品组编码
    private String            ed_channel_id;                           //渠道
    private String            brand_id;                                //品牌编码
    private String            category_id;                             //品类
    private Integer           invState;                                //批次
    private Integer           qty;                                     //数量
    private float             unitPrice;                               //开票单价
    private float             sumMoney;                                //价税合计
    private float             stockPrice;                              //采购价
    private float             actPrice;                                //执行价
    private float             retailPrice;                             //供价
    private float             bateRate;                                //直扣
    private float             bateMoney;                               //台返金额
    private float             proLossMoney;                            //折价损失
    private float             lossRate;                                //折扣
    private String            isFL;                                    //是否返利
    private String            isKPO;                                   //是否商用空调
    private String            create_user;                             //创建人
    private Timestamp         create_time;                             //创建时间
    private String            modify_user;                             //修改人
    private Timestamp         modify_time;                             //修改时间
    private String            delete_user;                             //删除人
    private Timestamp         delete_time;                             //删除时间
    private Integer           delete_flag;                             //删除状态
    private String            audit_user;                              //审核人
    private Timestamp         audit_time;                              //审核时间
    private String            audit_remark;                            //审核意见
    private Integer           flow_flag;                               //流程标志
    private String            error_msg;                               //同步错误
    private Timestamp         rrs_in_time;                             //入日日顺库时间
    private Timestamp         wa_in_time;                              //入wa库时间
    private String            so_id;                                   //so单号
    private String            dn_id;                                   //dn单号
    private String            estorge_id;                              //WA库位码
    private String            estorge_name;                            //WA库位名
    private String            corpName;                                //分公司名称
    private String            industry_trade_desc;                     //工贸描述
    private String            payment_name;                            //付款方名称
    private String            esale_name;                              //送达方名称
    private String            rrs_distribution_name;                   //配送中心名称

    private String            ed_channel_name;                         //渠道名称
    private String            corpCode;                                //分公司编码
    private String            regionId;                                //工贸编码
    private String            billType;                                //订单类型
    private String            billTypeName;                            //订单类型名
    private Integer           sap_flow_num;                            //配送方式
    private String            sap_flow_num_name;                       //配送方式名
    private String            custCode;                                //付款方编码
    private String            destCode;                                //送达方编码
    private String            whCode;                                  //日日顺仓库编码
    private String            custMgr;                                 //客户经理
    private String            porMgr;                                  //产品经理
    private String            proDputy;                                //产品代表
    private Timestamp         planInDate;                              //预计到货日期
    private String            budgetSort;                              //预算种类
    private String            budgetOrg;                               //预算体
    private String            budgetOrgName;                           //预算体名称
    private String            destCenter;                              //配送中心编码
    private String            saleOrgCode;                             //销售组织编码
    private String            maker;                                   //开单人
    private String            releBillCode;                            //关联单号
    private String            product_group_name;                      //产品组名称
    private String            les_regionId;                            //LES工贸编码
    private String            planindate_display;                      //预计到货日期
    private String            brand_name;                              //品牌名称
    private String            isFLName;                                //是否返利
    private String            isKPOName;                               //是否商用空调
    private String            flow_flag_name;                          //状态名称
    private String            rrs_out_time_display;                    //已出日日顺库时间
    private String            bill_time_display;                       //提单日期
    private String            wa_in_time_display;                      //WA库时间
    private String            source_order_id;                         //来源单号
    private String            billOrderId;                             //销售单号
    private String            push_status;                      		//推送SAP状态
    private String            push_message;                         	//推送SAP消息
    private String            push_process_time;                             //推送SAP时间
    /**
     * @return Returns the wp_order_id
     */
    public String getWp_order_id() {
        return wp_order_id;
    }

    /**
     * @param wp_order_id
     * The wp_order_id to set.
     */
    public void setWp_order_id(String wp_order_id) {
        this.wp_order_id = wp_order_id;
    }

    /**
     * @return Returns the po_id
     */
    public String getPo_id() {
        return po_id;
    }

    /**
     * @param po_id
     * The po_id to set.
     */
    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    /**
     * @return Returns the materials_id
     */
    public String getMaterials_id() {
        return materials_id;
    }

    /**
     * @param materials_id
     * The materials_id to set.
     */
    public void setMaterials_id(String materials_id) {
        this.materials_id = materials_id;
    }

    /**
     * @return Returns the materials_desc
     */
    public String getMaterials_desc() {
        return materials_desc;
    }

    /**
     * @param materials_desc
     * The materials_desc to set.
     */
    public void setMaterials_desc(String materials_desc) {
        this.materials_desc = materials_desc;
    }

    /**
     * @return Returns the product_group_id
     */
    public String getProduct_group_id() {
        return product_group_id;
    }

    /**
     * @param product_group_id
     * The product_group_id to set.
     */
    public void setProduct_group_id(String product_group_id) {
        this.product_group_id = product_group_id;
    }

    /**
     * @return Returns the ed_channel_id
     */
    public String getEd_channel_id() {
        return ed_channel_id;
    }

    /**
     * @param ed_channel_id
     * The ed_channel_id to set.
     */
    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    /**
     * @return Returns the brand_id
     */
    public String getBrand_id() {
        return brand_id;
    }

    /**
     * @param brand_id
     * The brand_id to set.
     */
    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * @return Returns the category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id
     * The category_id to set.
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    /**
     * @return Returns the invState
     */
    public Integer getInvState() {
        return invState;
    }

    /**
     * @param invState
     * The invState to set.
     */
    public void setInvState(Integer invState) {
        this.invState = invState;
    }

    /**
     * @return Returns the qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * @param qty
     * The qty to set.
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * @return Returns the unitPrice
     */
    public float getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice
     * The unitPrice to set.
     */
    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return Returns the sumMoney
     */
    public float getSumMoney() {
        return sumMoney;
    }

    /**
     * @param sumMoney
     * The sumMoney to set.
     */
    public void setSumMoney(float sumMoney) {
        this.sumMoney = sumMoney;
    }

    /**
     * @return Returns the stockPrice
     */
    public float getStockPrice() {
        return stockPrice;
    }

    /**
     * @param stockPrice
     * The stockPrice to set.
     */
    public void setStockPrice(float stockPrice) {
        this.stockPrice = stockPrice;
    }

    /**
     * @return Returns the actPrice
     */
    public float getActPrice() {
        return actPrice;
    }

    /**
     * @param actPrice
     * The actPrice to set.
     */
    public void setActPrice(float actPrice) {
        this.actPrice = actPrice;
    }

    /**
     * @return Returns the retailPrice
     */
    public float getRetailPrice() {
        return retailPrice;
    }

    /**
     * @param retailPrice
     * The retailPrice to set.
     */
    public void setRetailPrice(float retailPrice) {
        this.retailPrice = retailPrice;
    }

    /**
     * @return Returns the bateRate
     */
    public float getBateRate() {
        return bateRate;
    }

    /**
     * @param bateRate
     * The bateRate to set.
     */
    public void setBateRate(float bateRate) {
        this.bateRate = bateRate;
    }

    /**
     * @return Returns the bateMoney
     */
    public float getBateMoney() {
        return bateMoney;
    }

    /**
     * @param bateMoney
     * The bateMoney to set.
     */
    public void setBateMoney(float bateMoney) {
        this.bateMoney = bateMoney;
    }

    /**
     * @return Returns the proLossMoney
     */
    public float getProLossMoney() {
        return proLossMoney;
    }

    /**
     * @param proLossMoney
     * The proLossMoney to set.
     */
    public void setProLossMoney(float proLossMoney) {
        this.proLossMoney = proLossMoney;
    }

    /**
     * @return Returns the lossRate
     */
    public float getLossRate() {
        return lossRate;
    }

    /**
     * @param lossRate
     * The lossRate to set.
     */
    public void setLossRate(float lossRate) {
        this.lossRate = lossRate;
    }

    /**
     * @return Returns the isFL
     */
    public String getIsFL() {
        return isFL;
    }

    /**
     * @param isFL
     * The isFL to set.
     */
    public void setIsFL(String isFL) {
        this.isFL = isFL;
    }

    /**
     * @return Returns the isKPO
     */
    public String getIsKPO() {
        return isKPO;
    }

    /**
     * @param isKPO
     * The isKPO to set.
     */
    public void setIsKPO(String isKPO) {
        this.isKPO = isKPO;
    }

    /**
     * @return Returns the create_user
     */
    public String getCreate_user() {
        return create_user;
    }

    /**
     * @param create_user
     * The create_user to set.
     */
    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    /**
     * @return Returns the create_time
     */
    public Timestamp getCreate_time() {
        return create_time;
    }

    /**
     * @param create_time
     * The create_time to set.
     */
    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    /**
     * @return Returns the modify_user
     */
    public String getModify_user() {
        return modify_user;
    }

    /**
     * @param modify_user
     * The modify_user to set.
     */
    public void setModify_user(String modify_user) {
        this.modify_user = modify_user;
    }

    /**
     * @return Returns the modify_time
     */
    public Timestamp getModify_time() {
        return modify_time;
    }

    /**
     * @param modify_time
     * The modify_time to set.
     */
    public void setModify_time(Timestamp modify_time) {
        this.modify_time = modify_time;
    }

    /**
     * @return Returns the delete_user
     */
    public String getDelete_user() {
        return delete_user;
    }

    /**
     * @param delete_user
     * The delete_user to set.
     */
    public void setDelete_user(String delete_user) {
        this.delete_user = delete_user;
    }

    /**
     * @return Returns the delete_time
     */
    public Timestamp getDelete_time() {
        return delete_time;
    }

    /**
     * @param delete_time
     * The delete_time to set.
     */
    public void setDelete_time(Timestamp delete_time) {
        this.delete_time = delete_time;
    }

    /**
     * @return Returns the delete_flag
     */
    public Integer getDelete_flag() {
        return delete_flag;
    }

    /**
     * @param delete_flag
     * The delete_flag to set.
     */
    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    /**
     * @return Returns the audit_user
     */
    public String getAudit_user() {
        return audit_user;
    }

    /**
     * @param audit_user
     * The audit_user to set.
     */
    public void setAudit_user(String audit_user) {
        this.audit_user = audit_user;
    }

    /**
     * @return Returns the audit_time
     */
    public Timestamp getAudit_time() {
        return audit_time;
    }

    /**
     * @param audit_time
     * The audit_time to set.
     */
    public void setAudit_time(Timestamp audit_time) {
        this.audit_time = audit_time;
    }

    /**
     * @return Returns the audit_remark
     */
    public String getAudit_remark() {
        return audit_remark;
    }

    /**
     * @param audit_remark
     * The audit_remark to set.
     */
    public void setAudit_remark(String audit_remark) {
        this.audit_remark = audit_remark;
    }

    /**
     * @return Returns the flow_flag
     */
    public Integer getFlow_flag() {
        return flow_flag;
    }

    /**
     * @param flow_flag
     * The flow_flag to set.
     */
    public void setFlow_flag(Integer flow_flag) {
        this.flow_flag = flow_flag;
    }

    /**
     * @return Returns the error_msg
     */
    public String getError_msg() {
        return error_msg;
    }

    /**
     * @param error_msg
     * The error_msg to set.
     */
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    /**
     * @return Returns the rrs_in_time
     */
    public Timestamp getRrs_in_time() {
        return rrs_in_time;
    }

    /**
     * @param rrs_in_time
     * The rrs_in_time to set.
     */
    public void setRrs_in_time(Timestamp rrs_in_time) {
        this.rrs_in_time = rrs_in_time;
    }

    /**
     * @return Returns the wa_in_time
     */
    public Timestamp getWa_in_time() {
        return wa_in_time;
    }

    /**
     * @param wa_in_time
     * The wa_in_time to set.
     */
    public void setWa_in_time(Timestamp wa_in_time) {
        this.wa_in_time = wa_in_time;
    }

    /**
     * @return Returns the so_id
     */
    public String getSo_id() {
        return so_id;
    }

    /**
     * @param so_id
     * The so_id to set.
     */
    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    /**
     * @return Returns the dn_id
     */
    public String getDn_id() {
        return dn_id;
    }

    /**
     * @param dn_id
     * The dn_id to set.
     */
    public void setDn_id(String dn_id) {
        this.dn_id = dn_id;
    }

    /**
     * @return Returns the estorge_id
     */
    public String getEstorge_id() {
        return estorge_id;
    }

    /**
     * @param estorge_id
     * The estorge_id to set.
     */
    public void setEstorge_id(String estorge_id) {
        this.estorge_id = estorge_id;
    }

    /**
     * @return Returns the estorge_name
     */
    public String getEstorge_name() {
        return estorge_name;
    }

    /**
     * @param estorge_name
     * The estorge_name to set.
     */
    public void setEstorge_name(String estorge_name) {
        this.estorge_name = estorge_name;
    }

    /**
     * @return Returns the corpName
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * @param corpName
     * The corpName to set.
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * @return Returns the industry_trade_desc
     */
    public String getIndustry_trade_desc() {
        return industry_trade_desc;
    }

    /**
     * @param industry_trade_desc
     * The industry_trade_desc to set.
     */
    public void setIndustry_trade_desc(String industry_trade_desc) {
        this.industry_trade_desc = industry_trade_desc;
    }

    /**
     * @return Returns the payment_name
     */
    public String getPayment_name() {
        return payment_name;
    }

    /**
     * @param payment_name
     * The payment_name to set.
     */
    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    /**
     * @return Returns the esale_name
     */
    public String getEsale_name() {
        return esale_name;
    }

    /**
     * @param esale_name
     * The esale_name to set.
     */
    public void setEsale_name(String esale_name) {
        this.esale_name = esale_name;
    }

    /**
     * @return Returns the rrs_distribution_name
     */
    public String getRrs_distribution_name() {
        return rrs_distribution_name;
    }

    /**
     * @param rrs_distribution_name
     * The rrs_distribution_name to set.
     */
    public void setRrs_distribution_name(String rrs_distribution_name) {
        this.rrs_distribution_name = rrs_distribution_name;
    }

    /**
     * @return Returns the ed_channel_name
     */
    public String getEd_channel_name() {
        return ed_channel_name;
    }

    /**
     * @param ed_channel_name
     * The ed_channel_name to set.
     */
    public void setEd_channel_name(String ed_channel_name) {
        this.ed_channel_name = ed_channel_name;
    }

    /**
     * @return Returns the corpCode
     */
    public String getCorpCode() {
        return corpCode;
    }

    /**
     * @param corpCode
     * The corpCode to set.
     */
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    /**
     * @return Returns the regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * @param regionId
     * The regionId to set.
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    /**
     * @return Returns the billType
     */
    public String getBillType() {
        return billType;
    }

    /**
     * @param billType
     * The billType to set.
     */
    public void setBillType(String billType) {
        this.billType = billType;
    }

    /**
     * @return Returns the billTypeName
     */
    public String getBillTypeName() {
        return billTypeName;
    }

    /**
     * @param billTypeName
     * The billTypeName to set.
     */
    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }

    /**
     * @return Returns the sap_flow_num
     */
    public Integer getSap_flow_num() {
        return sap_flow_num;
    }

    /**
     * @param sap_flow_num
     * The sap_flow_num to set.
     */
    public void setSap_flow_num(Integer sap_flow_num) {
        this.sap_flow_num = sap_flow_num;
    }

    /**
     * @return Returns the sap_flow_num_name
     */
    public String getSap_flow_num_name() {
        return sap_flow_num_name;
    }

    /**
     * @param sap_flow_num_name
     * The sap_flow_num_name to set.
     */
    public void setSap_flow_num_name(String sap_flow_num_name) {
        this.sap_flow_num_name = sap_flow_num_name;
    }

    /**
     * @return Returns the custCode
     */
    public String getCustCode() {
        return custCode;
    }

    /**
     * @param custCode
     * The custCode to set.
     */
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    /**
     * @return Returns the destCode
     */
    public String getDestCode() {
        return destCode;
    }

    /**
     * @param destCode
     * The destCode to set.
     */
    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    /**
     * @return Returns the whCode
     */
    public String getWhCode() {
        return whCode;
    }

    /**
     * @param whCode
     * The whCode to set.
     */
    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    /**
     * @return Returns the custMgr
     */
    public String getCustMgr() {
        return custMgr;
    }

    /**
     * @param custMgr
     * The custMgr to set.
     */
    public void setCustMgr(String custMgr) {
        this.custMgr = custMgr;
    }

    /**
     * @return Returns the porMgr
     */
    public String getPorMgr() {
        return porMgr;
    }

    /**
     * @param porMgr
     * The porMgr to set.
     */
    public void setPorMgr(String porMgr) {
        this.porMgr = porMgr;
    }

    /**
     * @return Returns the proDputy
     */
    public String getProDputy() {
        return proDputy;
    }

    /**
     * @param proDputy
     * The proDputy to set.
     */
    public void setProDputy(String proDputy) {
        this.proDputy = proDputy;
    }

    /**
     * @return Returns the planInDate
     */
    public Timestamp getPlanInDate() {
        return planInDate;
    }

    /**
     * @param planInDate
     * The planInDate to set.
     */
    public void setPlanInDate(Timestamp planInDate) {
        this.planInDate = planInDate;
    }

    /**
     * @return Returns the budgetSort
     */
    public String getBudgetSort() {
        return budgetSort;
    }

    /**
     * @param budgetSort
     * The budgetSort to set.
     */
    public void setBudgetSort(String budgetSort) {
        this.budgetSort = budgetSort;
    }

    /**
     * @return Returns the budgetOrg
     */
    public String getBudgetOrg() {
        return budgetOrg;
    }

    /**
     * @param budgetOrg
     * The budgetOrg to set.
     */
    public void setBudgetOrg(String budgetOrg) {
        this.budgetOrg = budgetOrg;
    }

    /**
     * @return Returns the budgetOrgName
     */
    public String getBudgetOrgName() {
        return budgetOrgName;
    }

    /**
     * @param budgetOrgName
     * The budgetOrgName to set.
     */
    public void setBudgetOrgName(String budgetOrgName) {
        this.budgetOrgName = budgetOrgName;
    }

    /**
     * @return Returns the destCenter
     */
    public String getDestCenter() {
        return destCenter;
    }

    /**
     * @param destCenter
     * The destCenter to set.
     */
    public void setDestCenter(String destCenter) {
        this.destCenter = destCenter;
    }

    /**
     * @return Returns the saleOrgCode
     */
    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    /**
     * @param saleOrgCode
     * The saleOrgCode to set.
     */
    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode;
    }

    /**
     * @return Returns the maker
     */
    public String getMaker() {
        return maker;
    }

    /**
     * @param maker
     * The maker to set.
     */
    public void setMaker(String maker) {
        this.maker = maker;
    }

    /**
     * @return Returns the releBillCode
     */
    public String getReleBillCode() {
        return releBillCode;
    }

    /**
     * @param releBillCode
     * The releBillCode to set.
     */
    public void setReleBillCode(String releBillCode) {
        this.releBillCode = releBillCode;
    }

    /**
     * @return Returns the product_group_name
     */
    public String getProduct_group_name() {
        return product_group_name;
    }

    /**
     * @param product_group_name
     * The product_group_name to set.
     */
    public void setProduct_group_name(String product_group_name) {
        this.product_group_name = product_group_name;
    }

    /**
     * @return Returns the les_regionId
     */
    public String getLes_regionId() {
        return les_regionId;
    }

    /**
     * @param les_regionId
     * The tax_inclusive_price to set.
     */
    public void setLes_regionId(String les_regionId) {
        this.les_regionId = les_regionId;
    }

    /**
     * @return Returns the planindate_display
     */
    public String getPlanindate_display() {
        return planindate_display;
    }

    /**
     * @param planindate_display
     * The planindate_display to set.
     */
    public void setPlanindate_display(String planindate_display) {
        this.planindate_display = planindate_display;
    }

    /**
     * @return Returns the brand_name
     */
    public String getBrand_name() {
        return brand_name;
    }

    /**
     * @param brand_name
     * The brand_name to set.
     */
    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return Returns the isFLName
     */
    public String getIsFLName() {
        return isFLName;
    }

    /**
     * @param isFLName
     * The isFLName to set.
     */
    public void setIsFLName(String isFLName) {
        this.isFLName = isFLName;
    }

    /**
     * @return Returns the isKPOName
     */
    public String getIsKPOName() {
        return isKPOName;
    }

    /**
     * @param isKPOName
     * The isKPOName to set.
     */
    public void setIsKPOName(String isKPOName) {
        this.isKPOName = isKPOName;
    }

    /**
     * @return Returns the flow_flag_name
     */
    public String getFlow_flag_name() {
        return flow_flag_name;
    }

    /**
     * @param flow_flag_name
     * The flow_flag_name to set.
     */
    public void setFlow_flag_name(String flow_flag_name) {
        this.flow_flag_name = flow_flag_name;
    }

    /**
     * @return Returns the rrs_out_time_display
     */
    public String getRrs_out_time_display() {
        return rrs_out_time_display;
    }

    /**
     * @param rrs_out_time_display
     * The rrs_out_time_display to set.
     */
    public void setRrs_out_time_display(String rrs_out_time_display) {
        this.rrs_out_time_display = rrs_out_time_display;
    }

    /**
     * @return Returns the wa_in_time_display
     */
    public String getWa_in_time_display() {
        return wa_in_time_display;
    }

    /**
     * @param wa_in_time_display
     * The wa_in_time_display to set.
     */
    public void setWa_in_time_display(String wa_in_time_display) {
        this.wa_in_time_display = wa_in_time_display;
    }

    /**
     * @return Returns the bill_time_display
     */
    public String getBill_time_display() {
        return bill_time_display;
    }

    /**
     * @param bill_time_display
     * The bill_time_display to set.
     */
    public void setBill_time_display(String bill_time_display) {
        this.bill_time_display = bill_time_display;
    }

    public String getSource_order_id() {
        return source_order_id;
    }

    public void setSource_order_id(String source_order_id) {
        this.source_order_id = source_order_id;
    }

    public String getBillOrderId() {
        return billOrderId;
    }

    public void setBillOrderId(String billOrderId) {
        this.billOrderId = billOrderId;
    }

	public String getPush_status() {
		return push_status;
	}

	public void setPush_status(String push_status) {
		this.push_status = push_status;
	}

	public String getPush_message() {
		return push_message;
	}

	public void setPush_message(String push_message) {
		this.push_message = push_message;
	}

	public String getPush_process_time() {
		return push_process_time;
	}

	public void setPush_process_time(String push_process_time) {
		this.push_process_time = push_process_time;
	}
}
