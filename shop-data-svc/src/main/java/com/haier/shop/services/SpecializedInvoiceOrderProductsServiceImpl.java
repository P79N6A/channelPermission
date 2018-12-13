package com.haier.shop.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.shopread.SpecializedInvoiceOrderProductsReadDao;
import com.haier.shop.dao.shopwrite.OrderProductsWriteDao;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.service.SpecializedInvoiceOrderProductsService;
import com.haier.stock.model.OrderProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/6 15:23
 */
@Service
public class SpecializedInvoiceOrderProductsServiceImpl implements SpecializedInvoiceOrderProductsService {

    @Autowired
    private SpecializedInvoiceOrderProductsReadDao specializedInvoiceOrderProductsReadDao;
    @Autowired
    private OrderProductsWriteDao orderProductsWriteDao;



    /**
     * 分页查询网单列表
     * @param params
     * @return
     */
    @Override
    public ServiceResult<List<OrderProductsVo>> getSpecializedInvoiceOrderProductsList(OrderProductsVo params) {

        ServiceResult<List<OrderProductsVo>> result = new ServiceResult<List<OrderProductsVo>>();
        List<OrderProductsVo>  list =specializedInvoiceOrderProductsReadDao.queryOderProductList(params);
        for(int i=0;i<list.size();i++){
            String ss ="";
            if(null!=list.get(i).getStatus()&&!"".equals(list.get(i).getStatus())){
                ss= OrderProductStatus.getByCode(Integer.parseInt(list.get(i).getStatus().toString())).getName();
            }
            list.get(i).setStatusTs(ss);
        }
        result.setResult(list);
        PagerInfo pi = new PagerInfo();
        int row = specializedInvoiceOrderProductsReadDao.findOrderProductCNT(params);
        pi.setRowsCount(row);
        result.setPager(pi);
        return result;

    }

    @Override
    public List<Map<String, Object>> getListByParams(OrderProductsVo params) {
        return specializedInvoiceOrderProductsReadDao.getListByParams(params);
    }

    /**
     * 修改网单单价和金额
     * @param params
     * @return
     */
    @Override
    public Integer updateOrderProductsPriceAndProductAmount(Map<String, Object> params) {
        return orderProductsWriteDao.updateOrderProductsPriceAndProductAmount(params);
    }

    /**
     * 查询网单详情
     * @param cOrderSn
     * @return
     */
    @Override
    public OrderProductsVo getOrderOroductsDetail(String cOrderSn) {
        OrderProductsVo orderOroductsDetail = specializedInvoiceOrderProductsReadDao.getOrderOroductsDetail(cOrderSn);
        if(orderOroductsDetail != null){
            String ss ="";
           if(orderOroductsDetail.getStatus() !=null && !"".equals(orderOroductsDetail.getStatus())){
               ss = OrderProductStatus.getByCode(Integer.parseInt(orderOroductsDetail.getStatus().toString())).getName();
           }
            orderOroductsDetail.setStatusTs(ss);
        }
        return orderOroductsDetail;
    }


}
