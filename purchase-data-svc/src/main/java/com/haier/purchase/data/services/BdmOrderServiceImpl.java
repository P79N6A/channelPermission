//package com.haier.purchase.data.services;
//
//import java.util.List;
//import java.util.Map;
//
//import com.haier.cbs.purchase.model.BdmOrderModel;
//import com.haier.cbs.purchase.model.LogAuditModel;
//import com.haier.common.PagerInfo;
//import com.haier.common.ServiceResult;
//import com.haier.purchase.data.model.BdmOrder;
//import com.haier.purchase.data.service.BdmOrderService;
//
//public class BdmOrderServiceImpl implements BdmOrderService {
//    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
//                                                   .getLogger(BdmOrderServiceImpl.class);
//
//    private BdmOrderModel                  bdmOrderModel;
//    private LogAuditModel                  logAuditModel;
//
//    public void setBdmOrderModel(BdmOrderModel bdmOrderModel) {
//        this.bdmOrderModel = bdmOrderModel;
//    }
//
//    public void setLogAuditModel(LogAuditModel logAuditModel) {
//        this.logAuditModel = logAuditModel;
//    }
//
//    /**
//     * 查询数据
//     * 
//     * @param params
//     * @return
//     * @see com.haier.cbs.purchase.service.BdmOrderService#findBdmOrder(java.util.Map)
//     */
//    @Override
//    public ServiceResult<List<BdmOrder>> findBdmOrder(Map<String, Object> params) {
//        // TODO Auto-generated method stub
//        ServiceResult<List<BdmOrder>> result = new ServiceResult<List<BdmOrder>>();
//        try {
//            result.setResult(bdmOrderModel.findBdmOrder(params));
//            PagerInfo pi = new PagerInfo();
//            pi.setRowsCount(bdmOrderModel.findBdmOrderCNT(params));
//            result.setPager(pi);
//        } catch (Exception ex) {
//            result.setSuccess(false);
//            result.setMessage(ex.getMessage());
//            log.error("获取BDM样表信息失败：", ex);
//        }
//        return result;
//
//    }
//
//    /**
//     * 创建bdm样表数据
//     * @param params
//     * @return
//     * @see com.haier.cbs.purchase.service.BdmOrderService#insertBdmOrder(java.util.Map)
//     */
//    @Override
//    public int insertBdmOrder(Map<String, Object> params) {
//        // TODO Auto-generated method stub
//        return bdmOrderModel.insertBdmOrder(params);
//    }
//
//    /**
//     * 删除数据
//     * @param params
//     * @return
//     * @see com.haier.cbs.purchase.service.BdmOrderService#deleteBdmOrder(java.util.Map)
//     */
//    @Override
//    public int deleteBdmOrder(Map<String, Object> params) {
//        // TODO Auto-generated method stub
//        return bdmOrderModel.deleteBdmOrder(params);
//    }
//}
