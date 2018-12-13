package com.haier.shop.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.shop.dao.shopread.OperationAreaReadDao;
import com.haier.shop.dao.shopread.OrderProductsReadDao;
import com.haier.shop.dao.shopread.ZfbOrdersDetailsMatchingReadDao;
import com.haier.shop.dao.shopread.ZfbOrdersDetailsReadDao;
import com.haier.shop.dto.ZfbOrderMatchingDto;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.ZfbOrdersDetails;
import com.haier.shop.model.ZfbOrdersDetailsMatching;
import com.haier.shop.service.ShopZfbOrdersService;


@Service
public class ShopZfbOrdersServiceImpl implements ShopZfbOrdersService {

    private static final Logger logger = LogManager.getLogger(ShopZfbOrdersServiceImpl.class);
    @Autowired
    private ZfbOrdersDetailsMatchingReadDao  zfbOrdersDetailsMatchingReadDao;
    
    @Autowired
    private ZfbOrdersDetailsReadDao  zfbOrdersDetailsReadDao;
    @Autowired
    private OperationAreaReadDao operationAreaReadDao; 
	@Override
	public List<ZfbOrderMatchingDto> getFindQueryOrderList(QueryZFBOrderParameter queryOrder) {
		List<ZfbOrderMatchingDto> dtos=zfbOrdersDetailsMatchingReadDao.getFindQueryOrderList(queryOrder);
		if(!dtos.isEmpty()) {
			 List<Integer> oIds=dtos.stream().map(ZfbOrderMatchingDto::getoId).collect(Collectors.toList());
			 List<Map<String, Object>> maps= operationAreaReadDao.queryDataSumByOrderIds(oIds);
			 if(!maps.isEmpty()) {
				 for (Map<String, Object> map : maps) {
					for (ZfbOrderMatchingDto dto : dtos) {
						if(map.get("id").toString().equals(dto.getoId().toString())) {
							dto.setNumber(Integer.parseInt(map.get("number").toString()));
							dto.setCouponAmount(map.get("couponAmount").toString());
							dto.setEsAmount(map.get("esAmount").toString());
							dto.setItemShareAmount(map.get("itemShareAmount").toString());
							dto.setPoints(map.get("points").toString());
							dto.setJfbAmount(map.get("jfbAmount").toString());
							dto.setDjAmount(map.get("djAmount").toString());
							dto.setHbAmount(map.get("hbAmount").toString());
							dto.setProductAmount(map.get("productAmount").toString());
							dto.setShippingAmount(map.get("shippingAmount").toString());
							dto.setTotalEsAmount(map.get("totalEsAmount").toString());
							dto.setProductAmounto(map.get("productAmounto").toString());
						}
					}
				}
			 }
			 
		}
		return dtos; 
	}
	@Override
	public Integer getFindQueryOrderListCount(QueryZFBOrderParameter queryOrder) {
		return zfbOrdersDetailsMatchingReadDao.getFindQueryOrderListCount(queryOrder);
	}
	@Override
	public List<ZfbOrdersDetails> getFindQueryZfbOrderList(QueryZFBOrderParameter queryOrder) {
		return zfbOrdersDetailsReadDao.getFindQueryOrderList(queryOrder);
	}
	@Override
	public Integer getFindQueryZfbOrderListCount(QueryZFBOrderParameter queryOrder) {
		return zfbOrdersDetailsReadDao.getFindQueryOrderListCount(queryOrder);
	}
	
	@Override
	public List<ZfbOrdersDetails> getReportFormList(QueryZFBOrderParameter queryOrder) {
		return zfbOrdersDetailsReadDao.getReportFormList(queryOrder);
	}
	@Override
	public Integer getReportFormListCount(QueryZFBOrderParameter queryOrder) {
		return zfbOrdersDetailsReadDao.getReportFormListCount(queryOrder);
	}
   
}
