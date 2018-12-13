package com.haier.invoice.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.ZfbOrderDetailsService;
import com.haier.shop.dto.ZfbOrderMatchingDto;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.ZfbOrdersDetails;
import com.haier.shop.service.ShopZfbOrdersService;


@Service
public class ZfbOrderDetailsServiceImpl implements ZfbOrderDetailsService {

    private static Logger logger = LogManager.getLogger(ZfbOrderDetailsServiceImpl.class);

    @Autowired
    private ShopZfbOrdersService shopZfbOrdersService; 
    

    /**
     //TODO 支付宝订单流水匹配orders后
     */
    
	@Override
	public ServiceResult<List<ZfbOrderMatchingDto>> selectAllByParam(QueryZFBOrderParameter queryOrder) {
		//ServiceResult<List<ZfbOrdersDetailsMatching>> result=shopZfbOrdersService.selectAllByParam(queryOrder);
		
		ServiceResult<List<ZfbOrderMatchingDto>> result = new ServiceResult<List<ZfbOrderMatchingDto>>();
        try {
            if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【selectAllByParam】获取支付宝流水订单信息服务参数并为null");
                logger.error("【selectAllByParam】获取支付宝流水订单信息服务参数并为null");
                return result;
            }
            List<ZfbOrderMatchingDto> matchings=shopZfbOrdersService.getFindQueryOrderList(queryOrder);
            Integer count = shopZfbOrdersService.getFindQueryOrderListCount(queryOrder);
            result.setResult(matchings);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count != null ? count : 0);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("获取订单信息list失败：", e);
        }
        return result;
	}

	
	/**
	 //TODO 支付宝流水
	 */
	@Override
	public ServiceResult<List<ZfbOrdersDetails>> selectZfbOrdersByParam(QueryZFBOrderParameter queryOrder) {
		ServiceResult<List<ZfbOrdersDetails>> result = new ServiceResult<List<ZfbOrdersDetails>>();
        try {
            if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【selectZfbOrdersByParam】获取支付宝流水订单信息服务参数并为null");
                logger.error("【selectZfbOrdersByParam】获取支付宝流水订单信息服务参数并为null");
                return result;
            }
            List<ZfbOrdersDetails> matchings=shopZfbOrdersService.getFindQueryZfbOrderList(queryOrder);
            Integer count = shopZfbOrdersService.getFindQueryZfbOrderListCount(queryOrder);
            result.setResult(matchings);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count != null ? count : 0);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("获取订单信息list失败：", e);
        }
        return result;
	}

	/**
	 //TODO 支付宝匹配数据导出
	 */
	@Override
	public ServiceResult<List<ZfbOrderMatchingDto>> exportMatchingOrderList(QueryZFBOrderParameter queryOrder) {
		ServiceResult<List<ZfbOrderMatchingDto>> result = new ServiceResult<List<ZfbOrderMatchingDto>>();
		try {
			if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【exportMatchingOrderList】获取导出订单信息服务参数并为null");
                logger.error("【exportMatchingOrderList】获取支付宝匹配数据导出订单信息服务参数并为null");
                return result;
            }
			Integer count = shopZfbOrdersService.getFindQueryOrderListCount(queryOrder);
			//导出条数大于1000则只导出前1000条
	        if(count > 1000){
	            if(queryOrder.getPage() == null){
	                queryOrder.setPage(1);
	            }
	            if (queryOrder.getRows() == null) {
	                queryOrder.setRows(1000);
	            }
	            if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
	                queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
	            }

	        }
	        
	        List<ZfbOrderMatchingDto> matchings=shopZfbOrdersService.getFindQueryOrderList(queryOrder);
	        result.setResult(matchings);
			return result;
		} catch (Exception e) {
			 result.setSuccess(false);
	         result.setMessage(e.getMessage());
	         logger.error("获取支付宝匹配数据导出订单信息list失败：", e);
		}
		return result;
	}
	
	/**
	 //TODO 支付宝报表
	 */
	@Override
	public ServiceResult<List<ZfbOrdersDetails>> selectReportFormByParam(QueryZFBOrderParameter queryOrder) {
		ServiceResult<List<ZfbOrdersDetails>> result = new ServiceResult<List<ZfbOrdersDetails>>();
       try {
           if (queryOrder == null) {
               result.setSuccess(false);
               result.setMessage("【selectZfbOrdersByParam】获取支付宝流水订单信息服务参数并为null");
               logger.error("【selectZfbOrdersByParam】获取支付宝流水订单信息服务参数并为null");
               return result;
           }
           List<ZfbOrdersDetails> matchings=shopZfbOrdersService.getReportFormList(queryOrder);
           Integer count = shopZfbOrdersService.getReportFormListCount(queryOrder);
           result.setResult(matchings);
           PagerInfo pi = new PagerInfo();
           pi.setRowsCount(count != null ? count : 0);
           result.setPager(pi);
       } catch (Exception e) {
           result.setSuccess(false);
           result.setMessage(e.getMessage());
           logger.error("获取订单信息list失败：", e);
       }
       return result;
	}
}
