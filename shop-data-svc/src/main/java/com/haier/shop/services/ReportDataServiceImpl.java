package com.haier.shop.services;



import java.util.List;

import com.haier.shop.dao.shopread.OrderProductsReadDao;
import com.haier.shop.dao.shopread.OrderhpRejectionLogsReadDao;
import com.haier.shop.dao.shopread.ProductCatesReadDao;
import com.haier.shop.dao.shopread.ProductTypesReadDao;
import com.haier.shop.dao.shopwrite.OrderrepairHPrecordsWriteDao;
import com.haier.shop.dao.shopwrite.OrdersWriteDao;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderRepairHPRecordsNew;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.ProductCates;
import com.haier.shop.model.ProductToIndustry;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.PutAway;
import com.haier.shop.service.ReportDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportDataServiceImpl implements ReportDataService {
    @Autowired
    OrderrepairHPrecordsWriteDao orderrepairHPrecordsWriteDao;
    @Autowired
    OrdersWriteDao ordersWriteDao;
    @Autowired
    ProductTypesReadDao productTypesReadDao;
    @Autowired
    ProductCatesReadDao productCatesReadDao;
    @Autowired
    OrderProductsReadDao orderProductsReadDao;
    @Autowired
    OrderhpRejectionLogsReadDao  orderhpRejectionLogsReadDao;
	@Override
	public int findBadProductCount(String begintime, String endtime) {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsWriteDao.findBadProductCount(begintime,endtime);
	}


	@Override
	public OrderRepairHPRecordsNew getByRepairIdAndCheckType(Integer orderRepairId, Integer checkType) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<OrderRepairHPRecordsNew> getByRepairId(int orderRepairId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int findOrderNum(String begintime, String endtime) {
		// TODO Auto-generated method stub
		return ordersWriteDao.findOrderNum(begintime,endtime);
	}


	@Override
	public List<ProductTypesNew> findsort() {
		// TODO Auto-generated method stub
		return productTypesReadDao.findsort();
	}





	@Override
	public List<ProductCates> findIndustry(String typeName) {
		// TODO Auto-generated method stub
		return productCatesReadDao.findIndustry(typeName);
	}
	
	@Override
	public List<ProductCates> findindustry() {
		// TODO Auto-generated method stub
		return productCatesReadDao.findindustry();
	}


	@Override
	public List<ProductCates> findSortCount() {
		// TODO Auto-generated method stub
		return productCatesReadDao.findSortCount();
	}


	@Override
	public List<OrderProducts> findIndustryCount() {
		// TODO Auto-generated method stub
		return orderProductsReadDao.findIndustryCount();
	}


	@Override
	public List<OrderProducts> findsCode(Integer attrCateId) {
		// TODO Auto-generated method stub
		return orderProductsReadDao.findsCode(attrCateId);
	}


	@Override
	public List<PutAway> findbox() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.findbox();
	}

	@Override
	public List<PutAway> findmach() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.findmach();
	}

	@Override
	public List<ProductToIndustry> findreject() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsWriteDao.findreject();
	}
	@Override
	public List<ProductToIndustry> findsign() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsWriteDao.findsign();
	}


	@Override
	public List<PutAway> findnotinstock() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.findnotinstock();
	}


	@Override
	public String findoutnum() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.findoutnum();
	}


	@Override
	public List<PutAway> findNotFit() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.findNotFit();
	}


	@Override
	public List<PutAway> findNoChangeWarning() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.findNoChangeWarning();
	}


	@Override
	public int notfitcount() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.notfitcount();
	}


	@Override
	public List<PutAway> findNotInStockWarning() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.findNotInStockWarning();
	}




}
