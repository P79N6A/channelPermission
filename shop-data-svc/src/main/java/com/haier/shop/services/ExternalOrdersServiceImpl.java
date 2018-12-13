package com.haier.shop.services;

import com.haier.shop.dao.shopread.ExternalOrdersReadDao;
import com.haier.shop.dao.shopwrite.ExternalOrdersWriteDao;
import com.haier.shop.model.ExternalOrders;
import com.haier.shop.model.ExternalOrdersVo;
import com.haier.shop.service.ExternalOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExternalOrdersServiceImpl implements ExternalOrdersService {

    @Autowired
    private ExternalOrdersReadDao externalOrdersReadDao;
    @Autowired
    private ExternalOrdersWriteDao externalOrdersWriteDao;



   /* @Override
    public ServiceResult<String> sendForwardReverseToAccount(String foreignKey, String content) {
        // TODO Auto-generated method stub
        return externalOrdersDao.sendForwardReverseToAccount(foreignKey, content);
    }*/


    @Override
    public Integer updateAtferShipped(Integer orderId) {
        return externalOrdersWriteDao.updateAtferShipped(orderId);
    }

    /**
     * 根据原订单号获取外部订单对象
     * @param sourceOrderSn
     * @return
     */
    @Override
    public ExternalOrders getExternalOrdersBySourceOrderSn(String sourceOrderSn) {
        return externalOrdersReadDao.getBySourceOrderSn(sourceOrderSn);
    }



    /**
     * 分页查询错误来源订单
     * @param params
     * @return
     */
    @Override
    public List<ExternalOrdersVo> getExternalOrdersList(Map<String, Object> params) {

        /**
         * 根据条件查询
         */
        List<ExternalOrdersVo> externalOrdersList =externalOrdersReadDao.getExternalOrdersList(params);

        //查询总条数
        return externalOrdersList;
    }

    /**
     * 添加
     * @param extOrder
     * @return
     */
    @Override
    public int insertExternalOrdersInfo(ExternalOrders extOrder) {
        return externalOrdersWriteDao.insertExternalOrdersInfo(extOrder);
    }

    /**
     *修改
     * @param sourceOrderSn
     * @param orderState
     * @param errorLog
     * @return
     */
    @Override
    public Integer updateShopOrdersInfo(String sourceOrderSn, Integer orderState, String errorLog) {
        return externalOrdersWriteDao.updateExternalOrdersInfo(sourceOrderSn,orderState,errorLog);
    }

    @Override
    public Integer findExternalOrdersCNT(Map<String, Object> params) {
        //查询总条数
        Integer resultCount = externalOrdersReadDao.findExternalOrdersCNT(params);
        if(resultCount == null){
            resultCount =0;
        }
        return resultCount;
    }
}
