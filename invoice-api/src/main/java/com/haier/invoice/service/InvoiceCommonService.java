package com.haier.invoice.service;


import com.haier.common.ServiceResult;
import com.haier.invoice.model.eai.InvoiceEntity;
import com.haier.invoice.model.eai.QueryInvoiceInputType;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.MemberInvoicesDispItem;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-06
 **/
public interface InvoiceCommonService {

    /**
     * 查询导出发票信息
     * @param param
     * @return
     */
    List<Map<String, Object>> getExportInvoiceMakeOutList(Map<String, Object> param);

    /**
     *获取电子发票日志信息
     * @param queryMap
     * @return
     */
    Map<String, Object> findElecInvoiceLogsService(Map<String, Object> queryMap);

    /**
     * 获取开单列表
     * @param params
     * @return
     */
    Map<String, Object> findInvoiceMakeOutList(Map<String, Object> params);

    /**
     * 获得金税实时开票数据
     * @param inputType
     * @return
     */
    ServiceResult<InvoiceEntity> getTaxInvoicesInfo(QueryInvoiceInputType inputType);

    /**
     * 查询电子发票信息
     * @param corderSn
     * @return
     */
    Map<String, String> queryElecInvoice(String corderSn);

    /**
     * SAP接口监控查询
     * @param cOrderSn
     * @param params
     * @return
     */
    Map<String, Object> findInvoiceSapLogList(String cOrderSn, Map<String, Object> params);

    /**
     * 更新推送SAP日志，重置推送SAP次数
     * @param id
     */
    void updatePushAgainInvoiceSapCount(Integer id);

    /**
     * 获取开票日志列表
     * @param paramMap
     * @return
     */
    Map<String, Object> findMakeInvoiceApiLogs(Map<String, Object> paramMap);

    /**
     * 获取订单发票列表
     * @param paramMap
     * @return
     */
    Map<String, Object> findMemberInvoiceList(Map<String, Object> paramMap);

    /**
     * 获取3w待人工处理发票列表
     * @param paramMap
     * @return
     */
    Map<String, Object> findInvoiceWwwLogPage(Map<String, Object> paramMap);

    /**
     * 获取3w待人工处理发票列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> findInvoiceWwwLogList(Map<String, Object> paramMap);

    /**
     * 获取导出订单发票列表
     * @param paramMap
     * @return
     */
    List<MemberInvoicesDispItem> getExportMemberInvoicesList(Map<String, Object> paramMap);

    /**
     * 修改用户发票信息
     * @param memberInvoiceId
     * @param modifyFlg
     * @param orderSn
     * @return
     */
    Map<String, Object> modifyMemberInvoices(Integer memberInvoiceId, String modifyFlg, String orderSn);

    /**
     * 修改用户发票信息 3W发票待人工处理界面使用
     * @param orderId
     * @param modifyFlg
     * @param orderSn
     * @return
     */
    Map<String, Object> modifyMemberInvoicesBy3wInvoiceLogs(Integer orderId, String modifyFlg, String orderSn);

    /**
     * 根据抬头获取用户发票信息
     * @param invoiceTitle
     * @return
     */
    MemberInvoices getMemberInvoiceByInvoiceTitle(String invoiceTitle);

    /**
     * 保存用户发票信息
     * @param id ：发票ID(MemberInvoice id)
     * @param invoiceType
     * @param invoiceTitle
     * @param taxPayerNumber
     * @param registerAddress
     * @param registerPhone
     * @param bankName
     * @param bankCardNumber
     * @param state
     * @param remark
     * @param auditor
     * @return
     */
    String saveMemberInvoices(Integer id, Integer invoiceType, String invoiceTitle,
                              String taxPayerNumber, String registerAddress, String registerPhone,
                              String bankName, String bankCardNumber, Integer state, String remark,
                              String auditor,String userName,String vatremark);

    /**
     * 解锁发票信息
     * @param id
     * @param userName
     * @return
     */
    String unlockMemberInvoices(Integer id, String userName);

    /**
     * 根据发票id查询相关发票信息，包含网单信息
     * @param id
     * @return
     */
    Map<String, Object> showInvoiceInfo(Integer id);

    /**
     * 批量查询网单数量，界面展示
     * @param paramMap
     * @return
     */
    Integer getOpListByCOrderSnCount(Map<String, Object> paramMap);

    /**
     *  获取网单信息列表List[普通网单]
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getOpListByCOrderSnSearch(Map<String, Object> paramMap);

    /**
     *  获取[导出网单信息]列表List[普通网单]
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getOpListByCOrderSn( Map<String, Object> paramMap);

    /**
     * 获取渠道代码与名称对应列表
     * @return
     */
    List<Map<String, Object>> getChannelNames();

    /**
     * 获取渠道编码
     * @param source
     * @return
     */
    String getSapChannelCode(String cOrderSn,String source);

    /**
     * 发票批量修改
     * @param params
     * @return
     */
    int invoiceBatchModify(Map<String, Object> params);

    /**
     *
     * 3w发票待人工处理界面操作 （开赠票，开电子票，不开票）
     * @param flag  1：开增票,单独处理  2：开电子票  3：不开票
     * @param orderId
     * @param orderProductId
     * @param auditor
     * @return
     */
    String invoiceWwwLogsOperate(String flag, String orderId, String orderProductId, String auditor);

    /**
     //TODO 批量审核订单发票 
     * @param cOrderSns
     * @param modelMap
     * @return
     */
	HttpJsonResult<Map<String, Object>> doBatchAuditingOrderInvoice(String cOrderSns, Map<String, Object> modelMap,String userName);

	/**
	 //TODO 批量共享开票
	 * @param cOrderSns
	 * @param modelMap
	 * @return
	 */
	HttpJsonResult<Map<String, Object>> doBatchShareInvoice(String cOrderSns, Map<String, Object> modelMap);

    /**
     *天猫税控码查询(开票列表)
     * @param params
     * @return
     */
    Map<String,Object> getTianMaoFiscalCodeListByPage(Map<String,Object> params);

    /**
     * 查询导出发票信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getExportTianMaoFiscalCodeList(Map<String,Object> params);

    /**
     * 税控码查询
     * @param params
     * @return
     */
    Map<String,Object> getFiscalCodeListByPage(Map<String,Object> params);

    /**
     * 批量推送共享优品发票
     * @param line_nums
     * @return
     */
    HttpJsonResult<Map<String,Object>> doBatchPushShareYoupinInvoice(String line_nums);
}
