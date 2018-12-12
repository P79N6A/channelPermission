package com.haier.distribute.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.Brands;
import com.haier.distribute.data.model.Products;
import com.haier.distribute.data.model.Producttypes;
import com.haier.distribute.data.model.CommissionProduct;
import com.haier.distribute.data.service.BrandsService;
import com.haier.distribute.data.service.ChannelsService;
import com.haier.distribute.data.service.CommissionProductService;
import com.haier.distribute.data.service.DistributionInfoService;
import com.haier.distribute.data.service.ProductTypesService;
import com.haier.distribute.data.service.ProductsService;
import com.haier.distribute.service.DistributeCenterCommissionProductService;
/**
 * 可售商品
 * 孙玉凯 2017-11-06
 * @author sunyukai
 *
 */


/***
 * 主表分页查询
 */
@Service
public class DistributeCenterCommissionProductServiceImpl implements DistributeCenterCommissionProductService {
	
	@Autowired
	private CommissionProductService commissionProductService;

	@Autowired
	private ProductTypesService productTypesService;
	
	@Autowired
	private BrandsService brandsService;

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private DistributionInfoService distributionInfoService;
	@Autowired
	private ChannelsService channelsService;


    /**
     * 分页查询.
     *
     * @param condition
     * @return List<ProductDTO>
     */
    public JSONObject CommissionListf(PagerInfo pager, CommissionProduct condition){


    	List<CommissionProduct> list = commissionProductService.selectCommission_productListf(condition
    			,pager.getStart(), pager.getPageSize());
    	int total = commissionProductService.getPagerCountS(condition);
        JSONArray res = new JSONArray();
        for (Object o : list) {
        	CommissionProduct dto = (CommissionProduct)o;
            JSONObject json = new JSONObject();
            if(condition.getChannelType().equals("1")){
           	 json.put("channelName",dto.getDistributionName());
           }else{
           	 json.put("channelName",dto.getChannelName());
           }
            json.put("id", dto.getId());
            json.put("brandId",dto.getBrandId());
            json.put("brandName",dto.getBrandName());
            json.put("categoryId",dto.getCategoryId());
            json.put("categoryName",dto.getCategoryName());
            json.put("sku",dto.getSku());
            json.put("productName",dto.getProductName());
            json.put("monthPolicy",dto.getMonthPolicy());
            json.put("createTime",dto.getCreateTime());
            json.put("updateTime",dto.getUpdateTime());
            json.put("remark", dto.getRemark());
            json.put("channelType", dto.getChannelType());
    		json.put("channelId", dto.getChannelId());
    		json.put("policyYear", dto.getPolicyYear());

            res.add(json);
        }
        return jsonResult(res,total);

}

    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }

	public int addCommission(CommissionProduct commission) {
		commission.setCreateTime(new Date());
		commissionProductService.insert(commission);
		return 1;
	}

	public int updateCommission(CommissionProduct commission) {
		commission.setUpdateTime(new Date());
		commissionProductService.updateByPrimaryKey(commission);
		return 1;
}

	public int deleteCommission(int id) {
		commissionProductService.deleteByPrimaryKey(id);
		return 1;
}
	public List<Products> importCommission_productList(String  sku,String productname) {
		List<Products> list = productsService.selectProducts1(sku,productname);
		 JSONArray res = new JSONArray();
	     for (Object o : list) {
	    	 Products dto = (Products)o;
	         JSONObject json = new JSONObject();
	         json.put("brandId", dto.getBrandId());
	         json.put("productCateId", dto.getProductTypeId());
	         json.put("productName", dto.getProductName());
	         res.add(json);
	     }
		return list;

	}
	public List<CommissionProduct> selectexportCommission_productListf(CommissionProduct commission_product) {
		return commissionProductService.selectexportCommission_productListf(commission_product);

	}

	public String Distribution_infoList(String code) {
		List<Integer>  dis = distributionInfoService.selectId(code);
		List<Integer>  chan=channelsService.selectId(code);
		if(dis.size() > 0 && chan.size() >0){
			return "q";//返回错误信息渠道编码2张表重复
		}
		if(dis.size() == 0  && chan.size() ==0){
			return "w";//返回错误信息渠道编码录入错误
		}
		if(dis.size() >1 || chan.size() >1){
			return "e";//返回错误信息数据库信息错误请联系管理员
		}
		if(dis.size() == 1){
			return "1"+dis.get(0);//返回渠道
		}
		if(chan.size() ==1 ){
			return "2"+chan.get(0).toString();//返回来分销
		}
		return "";
}

	public int skuAll(String sku,int channtype,int policyYear ) {
		CommissionProduct commission_product =new CommissionProduct();
		commission_product.setSku(sku);
		List<CommissionProduct> list = commissionProductService.skuAll(sku,channtype,policyYear);
		if(list.size() >= 1){
			return list.get(0).getId();
		}
		return 0;
}

	public CommissionProduct AllName(Integer brandId, Integer productCateId) {
		List<Producttypes> CateName = productTypesService.selectByProducttypesSku(productCateId);
		List<Brands> BrandName = brandsService.selectBrandsIdList(brandId);
		CommissionProduct cp = new CommissionProduct();
		cp.setBrandId(brandId);
		cp.setBrandName(BrandName.get(0).getBrandName());
		cp.setCategoryId(productCateId);
		cp.setCategoryName(CateName.get(0).getTypeName());
		return cp;
}

	public int addCommission_product(CommissionProduct commission_product) {
		commission_product.setCreateTime(new Date());
		commissionProductService.insert(commission_product);
		return 1;
}

	public int updateCommission_product(CommissionProduct commission_product) {
		commission_product.setUpdateTime(new Date());
		commissionProductService.updateByPrimaryKey(commission_product);
		return 1;
}
	public Boolean jiaoyanCommission(CommissionProduct commission) {
		List<CommissionProduct> list = commissionProductService.jiaoyan(commission);
		if(list.size() >0){
			return false;
		}
		return true;
	}

	public List<CommissionProduct> selectCommission_productListf(CommissionProduct entity,int start,int rows){
		return commissionProductService.selectCommission_productListf(entity, start, rows);
	}

}
