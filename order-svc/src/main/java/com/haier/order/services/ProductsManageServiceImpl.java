package com.haier.order.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.order.service.ProductsManageService;
import com.haier.shop.model.ProductCates;
import com.haier.shop.model.Products;
import com.haier.shop.service.BrandsService;
import com.haier.shop.service.ProductCatesService;
import com.haier.shop.service.ProductTypesService;
import com.haier.shop.service.ProductsService;

@Service
public class ProductsManageServiceImpl implements ProductsManageService{

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(ProductsManageServiceImpl.class);

    private static List<ProductCates> childMenu = new ArrayList<ProductCates>();
	
	@Autowired
	ProductsService  productsService;
	@Autowired
	ProductCatesService productCatesService;
	@Autowired
	ProductTypesService  productTypesService;
	@Autowired
	BrandsService  brandsService;
	
	/**
	 * 商品类型遍历
	 * @param id
	 * @return
	 */
	@Override
	public List<ProductCates> getProductCate(List<Map<String, Object>> returnList, Integer id){
		try {
			List<ProductCates> productCateList = productCatesService.getAllProductCates();
			System.out.println("数据量"+productCateList.size());
			List<ProductCates> productCateList2 = getProductCateLists(productCateList, id);
			
			
			return productCateList2;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[ProductsManageModel][getProductCate]商品分类遍历发生异常，e:"+e.getMessage());
			return null;
		}
	}
	
	
	private List<ProductCates> getProductCateLists(List<ProductCates> menuList, int pid){  
        for(ProductCates productCates : menuList){  
            //遍历出父id等于参数的id，add进子节点集合  
            if(productCates.getParentId() == pid){  
                //递归遍历下一级  
            	getProductCateLists(menuList,productCates.getParentId());  
            	childMenu.add(productCates);  
            }  
        }  
        return childMenu;  
	}

	
	@Override
	public ServiceResult<List<Map<String, Object>>> getProductType(){
		ServiceResult<List<Map<String,Object>>> serviceResult = new ServiceResult<List<Map<String, Object>>>();
		try {
			List<Map<String,Object>> productType = productTypesService.getProducttypesList();
			if(productType!=null){
				serviceResult.setSuccess(true);
				serviceResult.setResult(productType);
			}else{
				serviceResult.setSuccess(false);
			}
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][getProductType]商品类型遍历发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][getProductType]商品类型遍历发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}

	@Override
	public ServiceResult<Map<String, Object>> findProductBySku(String sku){
		ServiceResult<Map<String,Object>> serviceResult = new ServiceResult<Map<String, Object>>();
		try {
			if(sku==null || sku.equals("")){
				serviceResult.setSuccess(false);
				serviceResult.setMessage("[ProductsManageModel][findProductBySku]sku为空");
			}else{
				Map<String,Object> productType = productsService.findProductBySku(sku);
				if(productType!=null){
					serviceResult.setSuccess(true);
					serviceResult.setResult(productType);
				}else{
					serviceResult.setSuccess(false);
				}
			}
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][findProductBySku]商品类型遍历发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][findProductBySku]商品类型遍历发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}

	
	@Override
	public ServiceResult<List<Map<String, Object>>> getBrands(){
		ServiceResult<List<Map<String,Object>>> serviceResult = new ServiceResult<List<Map<String, Object>>>();
		try {
			List<Map<String,Object>> brands = brandsService.getBrands();
			if(brands!=null){
				serviceResult.setSuccess(true);
				serviceResult.setResult(brands);
			}else{
				serviceResult.setSuccess(false);
			}
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][getBrands]品牌遍历发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][getBrands]品牌遍历发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}

	@Override
	public ServiceResult<List<Map<String, Object>>> queryProductsManageList(Map<String,Object> map) {
		ServiceResult<List<Map<String,Object>>> serviceResult = new ServiceResult<List<Map<String, Object>>>();
		try {
			List<Map<String,Object>> productList = productsService.queryProductList(map);
			if(productList!=null){
				serviceResult.setSuccess(true);
				serviceResult.setResult(productList);
				Integer count = productsService.queryProductListCount(map);
				if(count!=null && count>0){
					PagerInfo pi = new PagerInfo();
					pi.setRowsCount(count !=null ? count : 0 );
					serviceResult.setPager(pi);
				}
			}else{
				serviceResult.setSuccess(false);
			}
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][queryProductsManageList]商品查询发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][getBrands]商品查询发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}

	@Override
	public ServiceResult<Products> findProductsById(Integer id) {
		ServiceResult<Products> serviceResult = new ServiceResult<Products>();
		try {
			if(id==null || id<=0){
				serviceResult.setSuccess(false);
				return serviceResult;
			}
			Products product = productsService.get(id);
			if(product!=null){
				serviceResult.setSuccess(true);
				serviceResult.setResult(product);
			}else{
				serviceResult.setSuccess(false);
			}
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][findProductsById]通过id查询商品发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][findProductsById]通过id查询商品发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}

	@Override
	public Boolean productAdd(Products product) {
		try {
			if(product!=null){
				Integer addProduct = productsService.addProduct(product);
				if(addProduct==1){
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			log.error("[ProductsManageModel][productAdd]添加商品发生异常，e:"+e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean productUpdate(Products product) {
		try {
			if(product!=null){
				Integer updateProduct = productsService.updateProduct(product);
				if(updateProduct==1){
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			log.error("[ProductsManageModel][productUpdate]编辑商品发生异常，e:"+e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean delProduct(Integer id) {
		try {
			if(id!=null && id>0){
				Integer delProduct = productsService.delProduct(id);
				if(delProduct==1){
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			log.error("[ProductsManageModel][delProduct]删除商品发生异常，e:"+e.getMessage());
			return false;
		}
	}

	@Override
	public ServiceResult<List<Map<String,Object>>> getOnSaleProductIds() {
		ServiceResult<List<Map<String,Object>>> serviceResult = new ServiceResult<List<Map<String, Object>>>();
		try {
			List<Map<String,Object>> OnSaleProductIds = productsService.getOnSaleProductIds();
			if(OnSaleProductIds!=null){
				serviceResult.setSuccess(true);
				serviceResult.setResult(OnSaleProductIds);
			}else{
				serviceResult.setSuccess(false);
			}
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][getOnSaleProductIds]上架商品查询发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][getOnSaleProductIds]上架商品查询发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}

	@Override
	public ServiceResult<Map<String, Object>> findProductByName(String productName) {
		ServiceResult<Map<String, Object>> serviceResult = new ServiceResult<Map<String, Object>>();
		try {
			if(productName==null || productName.equals("")){
				serviceResult.setSuccess(false);
				return serviceResult;
			}
			Map<String, Object> product = productsService.findProductByName(productName);
			if(product!=null){
				serviceResult.setSuccess(true);
				serviceResult.setResult(product);
			}else{
				serviceResult.setSuccess(false);
			}
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][findProductByName]通过商品名称查询商品发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][findProductByName]通过商品名称查询商品发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}


	//TODO 导出商品列表
	@Override
	public ServiceResult<List<Map<String, Object>>> exportProductsManageList(Map<String, Object> map) {
		ServiceResult<List<Map<String,Object>>> serviceResult = new ServiceResult<List<Map<String, Object>>>();
		try {
			Integer count = productsService.queryProductListCount(map);
			//导出条数大于1000则只导出前10000条
	        if(count > 10000){
	            if(map.get("page") == null){
	            	map.put("page", 1);
	            }
	            map.put("rows", 10000);
	            /*int pageInt=Integer.parseInt(map.get("page").toString());
	            if (pageInt!=0) {
	            	int size=(pageInt - 1) * 5000;
	            	map.put("rows", size);
	            }*/

	        }
			List<Map<String,Object>> productList = productsService.queryProductList(map);
			serviceResult.setResult(productList);
			return serviceResult;
		} catch (Exception e) {
			log.error("[ProductsManageModel][exportProductsManageList]商品导出发生异常，e:"+e.getMessage());
			serviceResult.setSuccess(false);
			serviceResult.setMessage("[ProductsManageModel][exportProductsManageList]商品导出发生异常，e:"+e.getMessage());
			return serviceResult;
		}
	}
}
