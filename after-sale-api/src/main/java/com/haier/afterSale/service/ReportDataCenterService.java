package com.haier.afterSale.service;



import java.util.List;

import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.shop.model.BadProductInStorageAnaly;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderRepairHPRecordsNew;
import com.haier.shop.model.ProductCates;
import com.haier.shop.model.ProductToIndustry;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.PutAway;
import com.haier.stock.model.InvChannel2OrderSource;


public interface ReportDataCenterService {
    /**
     * 获取HP退货单
     * @param orderRepairId 退货单id
     * @param checkType 质检类型
     * @return
     */
    OrderRepairHPRecordsNew getByRepairIdAndCheckType( Integer orderRepairId,
                                                       Integer checkType);

    /**
     * 根据OrderRepairId获取HPRecords
     * @param repairId
     * @return
     */
    List<OrderRepairHPRecordsNew> getByRepairId( int orderRepairId);
    
    int findBadProductCount(String begintime, String endtime);//查询当月不良品的数量
    int findOrderNum(String begintime, String endtime);//查询订单的数量
    
	List<ProductTypesNew> findsort();

	List<ProductCates> findIndustry(String typeName);//查询产业根绝name

	List<ProductCates> findindustry();
	
	List<ProductCates> findSortCount();

	List<OrderProducts> findIndustryCount();

	List<OrderProducts> findsCode(Integer attrCateId);


	List<PutAway> findbox();

	List<PutAway> findmach();

	List<VomInOutStoreOrder> findInTime();

	List<ProductToIndustry> findsign();

	List<ProductToIndustry> findreject();

	List<PutAway> findnotinstock();

	String findoutnum();

	List<PutAway> findNotFit();

	List<PutAway> findNoChangeWarning();

	int notfitcount();

	List<PutAway> findNotInStockWarning();

	List<InvChannel2OrderSource> getAllOrder2ChannelSource();

	List<BadProductInStorageAnaly> HasInStock();

	List<BadProductInStorageAnaly> NotInStock();


	

	

	
}
