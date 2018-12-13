package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.model.ProductToIndustry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderrepairHPrecordsWriteDao {

    int insert(OrderrepairHPrecords orderrepairHPrecords);//插入
    
    int findBadProductCount(@Param("begintime")String begintime,@Param("endtime")String endtime);//查询不良品的数量

	int findIndustryCount(Integer attrCateId);

	int findSortCount(Integer attrCateId);

	List<ProductToIndustry> findreject();

	List<ProductToIndustry> findsign();
   
	int updataRepaiHp(OrderrepairHPrecords orderrepairHPrecords);
	
	 /**
     * 更改生成出入库单状态
     * @param OutOfStorageFlag
     * @param id
     * @return
     */
    int updataOutOfStorage(@Param("OutOfStorageFlag")String OutOfStorageFlag ,@Param("id")String id);
    /**
     *更改虚入虚出状态
     * @param id
     * @param virtualEntryState
     * @return
     */
    int UpdaVirtualEntryState(@Param("id")String id,@Param("virtualEntryState")String virtualEntryState);
    
    int updataPushRejects(String id);//更改不良品推送HP状态
}
