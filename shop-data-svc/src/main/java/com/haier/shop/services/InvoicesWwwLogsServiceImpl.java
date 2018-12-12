package com.haier.shop.services;

import com.haier.common.BusinessException;
import com.haier.shop.dao.shopread.InvoicesWwwLogsReadDao;
import com.haier.shop.dao.shopread.OrderProductsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceQueueWriteDao;
import com.haier.shop.dao.shopwrite.InvoicesWwwLogsWriteDao;
import com.haier.shop.dao.shopwrite.OrderOperateLogsWriteDao;
import com.haier.shop.dao.shopwrite.OrderProductsWriteDao;
import com.haier.shop.model.*;
import com.haier.shop.service.InvoicesWwwLogsService;
import com.haier.shop.util.InvoiceServiceUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoicesWwwLogsServiceImpl implements InvoicesWwwLogsService {
    @Autowired
    InvoicesWwwLogsReadDao invoicesWwwLogsReadDao;
    @Autowired
    InvoicesWwwLogsWriteDao invoicesWwwLogsWriteDao;
    @Autowired
    OrderProductsReadDao orderProductsReadDao;
    @Autowired
    OrderProductsWriteDao orderProductsWriteDao;
    @Autowired
    InvoiceQueueWriteDao invoiceQueueWriteDao;
    @Autowired
    OrderOperateLogsWriteDao orderOperateLogsWriteDao;

    private static final Logger logger = LogManager.getLogger(InvoicesWwwLogsServiceImpl.class);

    public InvoicesWwwLogs get(Integer orderProductId){
        return invoicesWwwLogsReadDao.get(orderProductId);
    }

    @Override
    public List<InvoicesWwwLogs> getByOrderId(Integer orderId) {
        return invoicesWwwLogsReadDao.getByOrderId(orderId);
    }

    public int insert(InvoicesWwwLogs invoicesWwwLogs){
        return invoicesWwwLogsWriteDao.insert(invoicesWwwLogs);
    }

    @Override
    public List<InvoicesWwwLogDispItem> findInvoiceWwwLogList(Map<String, Object> paramMap) {
        return invoicesWwwLogsReadDao.findInvoiceWwwLogList(paramMap);
    }

    @Override
    public Map<String, Object> findInvoiceWwwLogPage(Map<String, Object> paramMap) {

        List<InvoicesWwwLogDispItem> result = invoicesWwwLogsReadDao.findInvoiceWwwLogList(paramMap);
        int resultcount = invoicesWwwLogsReadDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);
        return retMap;
    }

    @Override
    public int updateInvoiceWwwLogs(InvoicesWwwLogs invoicesWwwLogs) {
        return invoicesWwwLogsWriteDao.updateInvoiceWwwLogs(invoicesWwwLogs);
    }

    @Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public String billElecInvoice(Orders orders, InvoicesWwwLogs invoicesWwwLogs, Integer orderProductId, String auditor) throws BusinessException {
        boolean beSuccess = true;
        try{
            OrderProducts tempOrderProduct = orderProductsReadDao.get(invoicesWwwLogs.getOrderProductId());
            if (null == tempOrderProduct){
                return "对不起，您请求的数据不存在！";
            }
            String outping = tempOrderProduct.getOutping().trim();
            if(null != outping && !outping.equals(" ") && !outping.equals("0")){
                invoicesWwwLogs.setSuccess(1);
                invoicesWwwLogs.setFlag(2);
                invoicesWwwLogs.setLastMessage("");
                invoicesWwwLogsWriteDao.updateInvoiceWwwLogs(invoicesWwwLogs);
                tempOrderProduct.setMakeReceiptType(InvoiceConst.MR_TYPE_HOUSE); //库房开票
                orderProductsWriteDao.updateMakeReceiptType(tempOrderProduct);
                invoiceQueueWriteDao.insertInvoiceQueue(tempOrderProduct.getId()); //加入开票队列
                //生成订单操作日志
                String operator = null != auditor && "".equals(auditor) ? auditor : "系统";
                OrderOperateLogs orderOperateLogs =
                        InvoiceServiceUtil.assemblyOrderOperateLog(orders,tempOrderProduct,"写入发票队列成功等待系统开票","创建发票队列",operator);
                orderOperateLogsWriteDao.insert(orderOperateLogs); //保存订单操作日志
            }else{
                beSuccess = false;
            }
        }catch(Exception e){
            logger.error("通过3w发票待人工处理界面开电子发票发送异常，调用参数：" + orderProductId + "，异常信息：" + e.getMessage());
            throw new BusinessException("通过3w发票待人工处理界面开电子发票发生异常");
        }
        if (beSuccess){
            return "";
        }else{
            return "对不起，订单商品未出库,或已开票不能开票。";
        }
    }
}
