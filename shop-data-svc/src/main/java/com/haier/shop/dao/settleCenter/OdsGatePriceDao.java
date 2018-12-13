package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.Brands;
import com.haier.shop.model.GatePrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OdsGatePriceDao {

    /**
     * 分页查询
     * @param params
     */
    List<GatePrice> queryGatePriceList(Map<String, Object> params);

    /**
     * 获取总数
     * @param params
     * @return
     */
    int queryGatePriceCount(Map<String, Object> params);

    List<GatePrice> getExportGatePriceList(Map<String, Object> paramMap);

    int createOdsGatePrice(GatePrice gatePrice);

    GatePrice queryOdsGatePrice(GatePrice gatePrice);

    int updateOdsGatePrice(GatePrice gatePrice);

    GatePrice getBySku(String sku);

    GatePrice queryOdsGatePriceById(String id);

    int updateLockGatePrice(GatePrice gatePrice);

    List<String> selectBrandList();

    List<String> selectCategoryList();

    GatePrice getOdsGatePriceBySku(String sku);
}
