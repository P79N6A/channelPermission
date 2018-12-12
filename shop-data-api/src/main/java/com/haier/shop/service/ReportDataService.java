package com.haier.shop.service;



import java.util.List;

import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderRepairHPRecordsNew;
import com.haier.shop.model.ProductCates;
import com.haier.shop.model.ProductToIndustry;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.PutAway;


public interface ReportDataService {
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
    
    int findBadProductCount(String begintime, String endtime);//查询不良品的数量
    int findOrderNum(String begintime, String endtime);//查询订单的数量

	List<ProductTypesNew> findsort();

	List<ProductCates> findIndustry(String typeName);
	
	
	List<ProductCates> findindustry();

	List<ProductCates> findSortCount();


	List<OrderProducts> findsCode(Integer attrCateId);

	List<PutAway> findbox();

	List<PutAway> findmach();

	List<ProductToIndustry> findsign();

	List<ProductToIndustry> findreject();

	List<OrderProducts> findIndustryCount();

	List<PutAway> findnotinstock();

	String findoutnum();

	List<PutAway> findNotFit();

	List<PutAway> findNoChangeWarning();

	int notfitcount();

	List<PutAway> findNotInStockWarning();

}
