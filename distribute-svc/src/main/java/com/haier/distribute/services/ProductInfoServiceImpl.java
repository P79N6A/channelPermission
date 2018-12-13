package com.haier.distribute.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.model.ProductFeaturesDTO;
import com.haier.distribute.data.model.ProductGatefoldsDTO;
import com.haier.distribute.data.model.ProductOuterPackingBoxesDTO;
import com.haier.distribute.data.model.ProductPicturesDTO;
import com.haier.distribute.data.model.ProductSpecificationsDTO;
import com.haier.distribute.data.service.PopProductService;
import com.haier.distribute.data.service.ProductCenterService;
import com.haier.distribute.data.service.ProductFeatureService;
import com.haier.distribute.data.service.ProductGatefoldService;
import com.haier.distribute.data.service.ProductOuterPackingBoxeService;
import com.haier.distribute.data.service.ProductPictureService;
import com.haier.distribute.data.service.ProductSpecificationService;
import com.haier.distribute.service.ProductInfoService;
import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.ProductMaterial;
import com.haier.link.upper.model.ProductSpecification;
import com.haier.link.upper.model.ResponseOther;
import com.haier.link.upper.model.Sign;
import com.haier.link.upper.model.SimpleProduct;
import com.haier.link.upper.service.LinkProductUpperReadService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
	private static org.slf4j.Logger log = LoggerFactory.getLogger(ProductInfoServiceImpl.class);
	
    @Autowired
    private LinkProductUpperReadService linkProductUpperReadService;
    @Resource
    private ProductCenterService productCenterService;
    //产品图
    @Autowired
    private ProductPictureService productPictureService;
    //拉页图
    @Autowired
    private ProductGatefoldService productGatefoldService;
    //特征图
    @Autowired
    private ProductFeatureService productFeatureService;
    @Autowired
    private ProductOuterPackingBoxeService productOuterPackingBoxeService;
    //规格参数
    @Autowired
    private ProductSpecificationService productSpecificationService;
    @Autowired
    private PopProductService popProductService;

    //@Scheduled(cron = "0 19 09 * * ?")
    public JSONObject abcProductInfoByCode() {
    	JSONObject result=new JSONObject();
        List<String> skuList = popProductService.seletSkuAll();
        Sign sign = new Sign();
        sign.setSecret("158c2911156904f6373c40514043855c");
        long key = 1000152;
        sign.setKey(key);
        int i=0;
        for (String code : skuList) {
        ResponseOther<ProductInfo> responseOther = linkProductUpperReadService.getProductInfoByCode(code, sign);
        if (responseOther.isSuccess()) {
            ProductInfo productInfo = responseOther.getResult();
            SimpleProduct simpleProduct = productInfo.getSimpleProduct();
            ProductCenterDTO dto = productCenterService.selectByCode(simpleProduct.getCode());
            //根据code查询t_products表  如果对象不存在 执行insert操作
            if (dto == null) {
                ProductCenterDTO productCenterDTO = new ProductCenterDTO();
                productCenterDTO.setModel(simpleProduct.getModel());
                productCenterDTO.setName(simpleProduct.getName());
                productCenterDTO.setCode(simpleProduct.getCode());
                productCenterDTO.setBrandId(simpleProduct.getBrandId() == null ? 0 : Integer.parseInt(simpleProduct.getBrandId().toString()));
                productCenterDTO.setBrandName(simpleProduct.getBrandName());
                productCenterDTO.setDateOffMarket(simpleProduct.getDateOffMarket() == null ? 0 : simpleProduct.getDateOffMarket().getTime());
                productCenterDTO.setDateToMarket(simpleProduct.getDateToMarket() == null ? 0 : simpleProduct.getDateToMarket().getTime());
                productCenterDTO.setInternationalCode(simpleProduct.getInternationalCode());
                productCenterDTO.setProductGroupCode(simpleProduct.getProductGroupCode());
                productCenterDTO.setProductGroupName(simpleProduct.getProductGroupName());
                productCenterDTO.setSalePoint(simpleProduct.getSalePoint());
                productCenterDTO.setSeries(simpleProduct.getSeries());
                productCenterDTO.setStatus(simpleProduct.getStatus());
                productCenterDTO.setRemark(simpleProduct.getRemark());
                //新增t-product表
                productCenterDTO = productCenterService.insertProductInfo(productCenterDTO);
                i++;
                if (productInfo.getProductPictures() != null || productInfo.getProductPictures().size() != 0) {
                    List<ProductPicturesDTO> productPicturesDTOList = new ArrayList<ProductPicturesDTO>();
                    List<ProductMaterial> list = productInfo.getProductPictures();
                    for (ProductMaterial productMaterial : list) {
                        ProductPicturesDTO productPicturesDTO = new ProductPicturesDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productPicturesDTOList.add(productPicturesDTO);
                    }
                    if (productPicturesDTOList.size() != 0) {
                        //新增t_product_pictures(产品图表)
                        productPictureService.insertBath(productPicturesDTOList);
                    }
                }
                if (productInfo.getProductFeatures() != null || productInfo.getProductFeatures().size() != 0) {
                    List<ProductFeaturesDTO> productFeaturesDTOArrayList = new ArrayList<ProductFeaturesDTO>();
                    List<ProductMaterial> list = productInfo.getProductFeatures();
                    for (ProductMaterial productMaterial : list) {
                        ProductFeaturesDTO productPicturesDTO = new ProductFeaturesDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        //新增t_product_features(特征图)
                        productFeatureService.insertBath(productFeaturesDTOArrayList);
                    }
                }
                if (productInfo.getProductGatefolds() != null || productInfo.getProductGatefolds().size() != 0) {
                    List<ProductGatefoldsDTO> productFeaturesDTOArrayList = new ArrayList<ProductGatefoldsDTO>();
                    List<ProductMaterial> list = productInfo.getProductGatefolds();
                    for (ProductMaterial productMaterial : list) {
                        ProductGatefoldsDTO productPicturesDTO = new ProductGatefoldsDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        //新增t_product_gatefolds(拉页图)
                        productGatefoldService.insertBath(productFeaturesDTOArrayList);
                    }
                }
                if (productInfo.getProductOuterPackingBoxes() != null || productInfo.getProductOuterPackingBoxes().size() != 0) {
                    List<ProductOuterPackingBoxesDTO> productFeaturesDTOArrayList = new ArrayList<ProductOuterPackingBoxesDTO>();
                    List<ProductMaterial> list = productInfo.getProductOuterPackingBoxes();
                    for (ProductMaterial productMaterial : list) {
                        ProductOuterPackingBoxesDTO productPicturesDTO = new ProductOuterPackingBoxesDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        //新增t_product_outer_packing_boxes(外包箱图)
                        productOuterPackingBoxeService.insertBath(productFeaturesDTOArrayList);
                    }
                }
                if (productInfo.getProductSpecifications() != null || productInfo.getProductSpecifications().size() != 0) {
                    List<ProductSpecificationsDTO> productFeaturesDTOArrayList = new ArrayList<ProductSpecificationsDTO>();
                    List<ProductSpecification> list = productInfo.getProductSpecifications();
                    for (ProductSpecification productMaterial : list) {
                        ProductSpecificationsDTO productPicturesDTO = new ProductSpecificationsDTO();
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setTag(productMaterial.getTag());
                        productPicturesDTO.setValue(productMaterial.getValue());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        //新增t_product_specifications表(规格参数)
                        productSpecificationService.insertBath(productFeaturesDTOArrayList);
                    }
                }
            }
            //如果不等于空说明该code对象在t_product表中已经存在  需要执行更新操作
            if (dto != null) {
                ProductCenterDTO productCenterDTO = new ProductCenterDTO();
                productCenterDTO.setModel(simpleProduct.getModel());
                productCenterDTO.setName(simpleProduct.getName());
                productCenterDTO.setCode(simpleProduct.getCode());
                productCenterDTO.setBrandId(simpleProduct.getBrandId() == null ? 0 : Integer.parseInt(simpleProduct.getBrandId().toString()));
                productCenterDTO.setBrandName(simpleProduct.getBrandName());
                productCenterDTO.setDateOffMarket(simpleProduct.getDateOffMarket() == null ? 0 : simpleProduct.getDateOffMarket().getTime());
                productCenterDTO.setDateToMarket(simpleProduct.getDateToMarket() == null ? 0 : simpleProduct.getDateToMarket().getTime());
                productCenterDTO.setInternationalCode(simpleProduct.getInternationalCode());
                productCenterDTO.setProductGroupCode(simpleProduct.getProductGroupCode());
                productCenterDTO.setProductGroupName(simpleProduct.getProductGroupName());
                productCenterDTO.setSalePoint(simpleProduct.getSalePoint());
                productCenterDTO.setSeries(simpleProduct.getSeries());
                productCenterDTO.setStatus(simpleProduct.getStatus());
                productCenterDTO.setRemark(simpleProduct.getRemark());
                productCenterDTO.setId(dto.getId());
                //更新t_product表
                productCenterService.updateById(productCenterDTO);
                if (productInfo.getProductPictures() != null || productInfo.getProductPictures().size() != 0) {
                    List<ProductPicturesDTO> productPicturesDTOList = new ArrayList<ProductPicturesDTO>();
                    List<ProductMaterial> list = productInfo.getProductPictures();
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productPictureService.deleteById(id);
                    for (ProductMaterial productMaterial : list) {
                        ProductPicturesDTO productPicturesDTO = new ProductPicturesDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productPicturesDTOList.add(productPicturesDTO);
                    }
                    //根据dto的id全插
                    if (productPicturesDTOList.size() != 0) {
                        productPictureService.insertBath(productPicturesDTOList);
                    }
                }
                //如果此次没有
                if (productInfo.getProductPictures() == null || productInfo.getProductPictures().size() == 0) {
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productPictureService.deleteById(id);
                }


                if (productInfo.getProductFeatures() != null || productInfo.getProductFeatures().size() != 0) {
                    List<ProductFeaturesDTO> productFeaturesDTOArrayList = new ArrayList<ProductFeaturesDTO>();
                    List<ProductMaterial> list = productInfo.getProductFeatures();
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productFeatureService.deleteById(id);
                    for (ProductMaterial productMaterial : list) {
                        ProductFeaturesDTO productPicturesDTO = new ProductFeaturesDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        productFeatureService.insertBath(productFeaturesDTOArrayList);
                    }
                }
                if (productInfo.getProductFeatures() == null || productInfo.getProductFeatures().size() == 0) {
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productFeatureService.deleteById(id);
                }


                if (productInfo.getProductGatefolds() != null || productInfo.getProductGatefolds().size() != 0) {
                    List<ProductGatefoldsDTO> productFeaturesDTOArrayList = new ArrayList<ProductGatefoldsDTO>();
                    List<ProductMaterial> list = productInfo.getProductGatefolds();
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productGatefoldService.deleteById(id);
                    for (ProductMaterial productMaterial : list) {
                        ProductGatefoldsDTO productPicturesDTO = new ProductGatefoldsDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        productGatefoldService.insertBath(productFeaturesDTOArrayList);
                    }
                }
                if (productInfo.getProductGatefolds() == null || productInfo.getProductGatefolds().size() == 0) {
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productGatefoldService.deleteById(id);
                }


                if (productInfo.getProductOuterPackingBoxes() != null || productInfo.getProductOuterPackingBoxes().size() != 0) {
                    List<ProductOuterPackingBoxesDTO> productFeaturesDTOArrayList = new ArrayList<ProductOuterPackingBoxesDTO>();
                    List<ProductMaterial> list = productInfo.getProductOuterPackingBoxes();
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productOuterPackingBoxeService.deleteById(id);
                    for (ProductMaterial productMaterial : list) {
                        ProductOuterPackingBoxesDTO productPicturesDTO = new ProductOuterPackingBoxesDTO();
                        productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                        productPicturesDTO.setFileId(productMaterial.getFileId());
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setUrl(productMaterial.getUrl());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        productOuterPackingBoxeService.insertBath(productFeaturesDTOArrayList);
                    }
                }
                if (productInfo.getProductOuterPackingBoxes() == null || productInfo.getProductOuterPackingBoxes().size() == 0) {
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productOuterPackingBoxeService.deleteById(id);
                }


                if (productInfo.getProductSpecifications() != null || productInfo.getProductSpecifications().size() != 0) {
                    List<ProductSpecificationsDTO> productFeaturesDTOArrayList = new ArrayList<ProductSpecificationsDTO>();
                    List<ProductSpecification> list = productInfo.getProductSpecifications();
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productSpecificationService.deleteById(id);
                    for (ProductSpecification productMaterial : list) {
                        ProductSpecificationsDTO productPicturesDTO = new ProductSpecificationsDTO();
                        productPicturesDTO.setName(productMaterial.getName());
                        productPicturesDTO.setProductId(productCenterDTO.getId());
                        productPicturesDTO.setTag(productMaterial.getTag());
                        productPicturesDTO.setValue(productMaterial.getValue());
                        productFeaturesDTOArrayList.add(productPicturesDTO);
                    }
                    if (productFeaturesDTOArrayList.size() != 0) {
                        productSpecificationService.insertBath(productFeaturesDTOArrayList);
                    }
                }
                if (productInfo.getProductSpecifications() == null || productInfo.getProductSpecifications().size() == 0) {
                    Integer id = dto.getId();
                    //根据dto的id全删
                    productSpecificationService.deleteById(id);
                }
                result.put("msg", "数据同步成功");
                log.info("共插入数据"+i+"条");
                result.put("isSuccess", true);
            }
        }else {
        	log.info("未查询到的商品信息"+code);
        }
    }
		return result;
}
}
