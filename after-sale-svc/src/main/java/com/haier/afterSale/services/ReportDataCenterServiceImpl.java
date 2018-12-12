package com.haier.afterSale.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.service.ReportDataCenterService;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.shop.model.BadProductInStorageAnaly;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderRepairHPRecordsNew;
import com.haier.shop.model.ProductCates;
import com.haier.shop.model.ProductToIndustry;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.PutAway;
import com.haier.shop.service.ReportDataService;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.service.StockInvChannel2OrderSourceService;

@Service
public class ReportDataCenterServiceImpl implements ReportDataCenterService {
 

	@Autowired
    ReportDataService reportDataService;
	@Autowired
    private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;
   
	@Override
	public int findBadProductCount(String begintime, String endtime) {
		// TODO Auto-generated method stub
		return reportDataService.findBadProductCount(begintime,endtime);
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
		return reportDataService.findOrderNum(begintime,endtime);
	}


	@Override
	public List<ProductTypesNew> findsort() {
		// TODO Auto-generated method stub
		return reportDataService.findsort();
	}







	@Override
	public List<ProductCates> findIndustry(String typeName) {
		// TODO Auto-generated method stub
		return reportDataService.findIndustry(typeName);
	}
	
	@Override
	public List<ProductCates> findindustry() {
		// TODO Auto-generated method stub
		return reportDataService.findindustry();
	}


	@Override
	public List<ProductCates> findSortCount() {
		// TODO Auto-generated method stub
		return reportDataService.findSortCount();
	}


	@Override
	public List<OrderProducts> findIndustryCount() {
		// TODO Auto-generated method stub
		return reportDataService.findIndustryCount();
	}


	@Override
	public List<OrderProducts> findsCode(Integer attrCateId) {
		// TODO Auto-generated method stub
		return reportDataService.findsCode(attrCateId);
	}


	@Override
	public List<PutAway> findbox() {
		// TODO Auto-generated method stub
		return reportDataService.findbox();
	}

	@Override
	public List<PutAway> findmach() {
		// TODO Auto-generated method stub
		return reportDataService.findmach();
	}


	@Override
	public List<VomInOutStoreOrder> findInTime() {
		// TODO Auto-generated method stub
		return eisVomInOutStoreOrderService.findInTime();
	}



	@Override
	public List<ProductToIndustry> findsign() {
		// TODO Auto-generated method stub
		return reportDataService.findsign();
	}


	@Override
	public List<ProductToIndustry> findreject() {
		// TODO Auto-generated method stub
		return reportDataService.findreject();
	}


	@Override
	public List<PutAway> findnotinstock() {
		// TODO Auto-generated method stub
		return reportDataService.findnotinstock();
	}


	@Override
	public String findoutnum() {
		// TODO Auto-generated method stub
		return reportDataService.findoutnum();
	}


	@Override
	public List<PutAway> findNotFit() {
		// TODO Auto-generated method stub
		return reportDataService.findNotFit();
	}


	@Override
	public List<PutAway> findNoChangeWarning() {
		// TODO Auto-generated method stub
		return reportDataService.findNoChangeWarning();
	}


	@Override
	public int notfitcount() {
		// TODO Auto-generated method stub
		return reportDataService.notfitcount();
	}


	@Override
	public List<PutAway> findNotInStockWarning() {
		// TODO Auto-generated method stub
		return reportDataService.findNotInStockWarning();
	}


	@Override
	public List<InvChannel2OrderSource> getAllOrder2ChannelSource() {
		// TODO Auto-generated method stub
		return stockInvChannel2OrderSourceService.getAllOrder2ChannelSource();
	}


	@Override
	public List<BadProductInStorageAnaly> HasInStock() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<BadProductInStorageAnaly> NotInStock() {
		// TODO Auto-generated method stub
		return null;
	}
  








}
