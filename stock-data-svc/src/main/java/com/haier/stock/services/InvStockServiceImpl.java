package com.haier.stock.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.haier.stock.dao.stock.InvStockDao;
import com.haier.stock.model.InvStock;
import com.haier.stock.service.InvStockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvStockServiceImpl implements InvStockService {
    @Autowired
    InvStockDao invStockDao;

    @Override
    public Integer insert(InvStock stock) {
        // TODO Auto-generated method stub
        return invStockDao.insert(stock);
    }

    @Override
    public Integer updateQty(InvStock stock) {
        // TODO Auto-generated method stub
        return invStockDao.updateQty(stock);
    }

    @Override
    public Integer addSaleSubMachines(String sku) {
        // TODO Auto-generated method stub
        return invStockDao.addSaleSubMachines(sku);
    }

    @Override
    public Integer deleteSaleSubMachines(String sku) {
        // TODO Auto-generated method stub
        return invStockDao.deleteSaleSubMachines(sku);
    }

    @Override
    public List<InvStock> getChangedListBySkuWhcode(Date beginTime, String stockChannel, String channel) {
        // TODO Auto-generated method stub
        return invStockDao.getChangedListBySkuWhcode(beginTime, stockChannel, channel);
    }

    @Override
    public List<InvStock> getHAIPStockQty(Date beginTime, String stockChannel) {
        // TODO Auto-generated method stub
        return invStockDao.getHAIPStockQty(beginTime, stockChannel);
    }

    @Override
    public List<InvStock> getChangedStockQty(Date beginTime, String stockChannel, String channel) {
        // TODO Auto-generated method stub
        return invStockDao.getChangedStockQty(beginTime, stockChannel, channel);
    }

    @Override
    public List<InvStock> getStockQtyByWhCodesAndSkus(String skus, String secCodes, String channelCodes,
                                                      String channel) {
        // TODO Auto-generated method stub
        return invStockDao.getStockQtyByWhCodesAndSkus(skus, secCodes, channelCodes, channel);
    }

    @Override
    public List<InvStock> getStockQtyByItemProperty(String skus, String secCodes, String channelCodes,
                                                    Integer itemProperty) {
        // TODO Auto-generated method stub
        return invStockDao.getStockQtyByItemProperty(skus, secCodes, channelCodes, itemProperty);
    }

    @Override
    public List<InvStock> getReliableStockQtyByWhCodesAndSkus(String skus, String secCodes, String channelCodes,
                                                              String channel) {
        // TODO Auto-generated method stub
        return invStockDao.getReliableStockQtyByWhCodesAndSkus(skus, secCodes, channelCodes, channel);
    }

    @Override
    public List<InvStock> getReliableStockQtyByItemProperty(String skus, String secCodes, String channelCodes,
                                                            Integer itemProperty) {
        // TODO Auto-generated method stub
        return invStockDao.getReliableStockQtyByItemProperty(skus, secCodes, channelCodes, itemProperty);
    }

    @Override
    public List<InvStock> getValidStockQtyByWhCodesAndSkus(String skus, String secCodes, String channelCodes) {
        // TODO Auto-generated method stub
        return invStockDao.getValidStockQtyByWhCodesAndSkus(skus, secCodes, channelCodes);
    }

    @Override
    public List<InvStock> getValidStockQtyByItemProperty(String skus, String secCodes, String channelCodes,
                                                         Integer itemProperty) {
        // TODO Auto-generated method stub
        return invStockDao.getValidStockQtyByItemProperty(skus, secCodes, channelCodes, itemProperty);
    }

    @Override
    public InvStock getBySecCodeAndSku(String secCode, String sku) {
        // TODO Auto-generated method stub
        return invStockDao.getBySecCodeAndSku(secCode, sku);
    }

    @Override
    public InvStock getBySecCodeAndSkuForLock(String secCode, String sku) {
        // TODO Auto-generated method stub
        return invStockDao.getBySecCodeAndSkuForLock(secCode, sku);
    }

    @Override
    public List<InvStock> getBySku(String sku) {
        // TODO Auto-generated method stub
        return invStockDao.getBySku(sku);
    }

    @Override
    public List<InvStock> getBySkuAndWhCode(String sku, String whCode) {
        // TODO Auto-generated method stub
        return invStockDao.getBySkuAndWhCode(sku, whCode);
    }

    @Override
    public Integer releaseFrozenQty(InvStock stock) {
        // TODO Auto-generated method stub
        return invStockDao.releaseFrozenQty(stock);
    }

    @Override
    public Integer releaseCbsFrozenQty(Integer stoId, Integer releaseQty) {
        // TODO Auto-generated method stub
        return invStockDao.releaseCbsFrozenQty(stoId, releaseQty);
    }

    @Override
    public Integer frozenCrmFrozenQty(InvStock stock) {
        // TODO Auto-generated method stub
        return invStockDao.frozenCrmFrozenQty(stock);
    }

    @Override
    public Integer frozenStockQty(Integer id, Integer frozenQty) {
        // TODO Auto-generated method stub
        return invStockDao.frozenStockQty(id, frozenQty);
    }

    @Override
    public List<InvStock> getChanngedStockList(Date updateTime, int topX) {
        // TODO Auto-generated method stub
        return invStockDao.getChanngedStockList(updateTime, topX);
    }

    @Override
    public InvStock getMaxStock(String sku, Set<String> whCodes, Set<String> stockChannels) {
        // TODO Auto-generated method stub
        return invStockDao.getMaxStock(sku, whCodes, stockChannels);
    }

}
