package com.haier.order.services;

import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.*;
import com.haier.link.upper.service.LinkProductUpperReadService;
import com.haier.order.service.ProductsManageService;
import com.haier.order.service.ShopProductInfoService;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopProductInfoServiceImpl implements ShopProductInfoService {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private LinkProductUpperReadService linkProductUpperReadService;
    @Autowired
    private ShopProductPictureDataService shopProductPictureDataService;
    @Autowired
    private ShopProductFeatureDataService shopProductFeatureDataService;
    @Autowired
    private ShopProductGatefoldDataService shopProductGatefoldDataService;
    @Autowired
    private ShopProductOuterPackingBoxeDataService shopProductOuterPackingBoxeDataService;
    @Autowired
    private ShopProductSpecificationDataService shopProductSpecificationDataService;
    @Override
    public void shopProductInfoByCode() {

        List<String> skuList = productsService.seletSkuAll();
        Sign sign = new Sign();
        sign.setSecret("158c2911156904f6373c40514043855c");
        long key = 1000152;
        sign.setKey(key);
        for (String code : skuList) {
            ResponseOther<ProductInfo> responseOther = linkProductUpperReadService.getProductInfoByCode(code, sign);
            if (responseOther.isSuccess()) {
                ProductInfo productInfo = responseOther.getResult();
                SimpleProduct simpleProduct = productInfo.getSimpleProduct();
                Products dto = productsService.selectBySku(simpleProduct.getCode());
                //根据code查询t_products表  如果对象不存在 执行insert操作
                if (dto.getIsSynch()==0) {
                    int updateTotal=productsService.updateProductBySku(simpleProduct.getCode());
                    if(updateTotal==0){
                        continue;
                    }
                    if (productInfo.getProductPictures() != null || productInfo.getProductPictures().size() != 0) {
                        List<ProductPicturesDTO> productPicturesDTOList = new ArrayList<ProductPicturesDTO>();
                        List<ProductMaterial> list = productInfo.getProductPictures();
                        for (ProductMaterial productMaterial : list) {
                            ProductPicturesDTO productPicturesDTO = new ProductPicturesDTO();
                            productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                            productPicturesDTO.setFileId(productMaterial.getFileId());
                            productPicturesDTO.setName(productMaterial.getName());
                            productPicturesDTO.setProductId(dto.getId());
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productPicturesDTOList.add(productPicturesDTO);
                        }
                        if (productPicturesDTOList.size() != 0) {
                            //新增t_product_pictures(产品图表)
                            shopProductPictureDataService.insertBath(productPicturesDTOList);
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
                            productPicturesDTO.setProductId(dto.getId());
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            //新增t_product_features(特征图)
                            shopProductFeatureDataService.insertBath(productFeaturesDTOArrayList);
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
                            productPicturesDTO.setProductId(dto.getId());
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            //新增t_product_gatefolds(拉页图)
                            shopProductGatefoldDataService.insertBath(productFeaturesDTOArrayList);
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
                            productPicturesDTO.setProductId(dto.getId());
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            //新增t_product_outer_packing_boxes(外包箱图)
                            shopProductOuterPackingBoxeDataService.insertBath(productFeaturesDTOArrayList);
                        }
                    }
                    if (productInfo.getProductSpecifications() != null || productInfo.getProductSpecifications().size() != 0) {
                        List<ProductSpecificationsDTO> productFeaturesDTOArrayList = new ArrayList<ProductSpecificationsDTO>();
                        List<ProductSpecification> list = productInfo.getProductSpecifications();
                        for (ProductSpecification productMaterial : list) {
                            ProductSpecificationsDTO productPicturesDTO = new ProductSpecificationsDTO();
                            productPicturesDTO.setName(productMaterial.getName());
                            productPicturesDTO.setProductId(dto.getId());
                            productPicturesDTO.setTag(productMaterial.getTag());
                            productPicturesDTO.setValue(productMaterial.getValue());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            //新增t_product_specifications表(规格参数)
                            shopProductSpecificationDataService.insertBath(productFeaturesDTOArrayList);
                        }
                    }
                }
                //如果不等于空说明该code对象在t_product表中已经存在  需要执行更新操作
                if (dto.getIsSynch()==1) {
                    //更新t_product表
                    if (productInfo.getProductPictures() != null || productInfo.getProductPictures().size() != 0) {
                        List<ProductPicturesDTO> productPicturesDTOList = new ArrayList<ProductPicturesDTO>();
                        List<ProductMaterial> list = productInfo.getProductPictures();
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductPictureDataService.deleteById(id);
                        for (ProductMaterial productMaterial : list) {
                            ProductPicturesDTO productPicturesDTO = new ProductPicturesDTO();
                            productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                            productPicturesDTO.setFileId(productMaterial.getFileId());
                            productPicturesDTO.setName(productMaterial.getName());
                            productPicturesDTO.setProductId(id);
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productPicturesDTOList.add(productPicturesDTO);
                        }
                        //根据dto的id全插
                        if (productPicturesDTOList.size() != 0) {
                            shopProductPictureDataService.insertBath(productPicturesDTOList);
                        }
                    }
                    //如果此次没有
                    if (productInfo.getProductPictures() == null || productInfo.getProductPictures().size() == 0) {
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductPictureDataService.deleteById(id);
                    }


                    if (productInfo.getProductFeatures() != null || productInfo.getProductFeatures().size() != 0) {
                        List<ProductFeaturesDTO> productFeaturesDTOArrayList = new ArrayList<ProductFeaturesDTO>();
                        List<ProductMaterial> list = productInfo.getProductFeatures();
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductFeatureDataService.deleteById(id);
                        for (ProductMaterial productMaterial : list) {
                            ProductFeaturesDTO productPicturesDTO = new ProductFeaturesDTO();
                            productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                            productPicturesDTO.setFileId(productMaterial.getFileId());
                            productPicturesDTO.setName(productMaterial.getName());
                            productPicturesDTO.setProductId(id);
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            shopProductFeatureDataService.insertBath(productFeaturesDTOArrayList);
                        }
                    }
                    if (productInfo.getProductFeatures() == null || productInfo.getProductFeatures().size() == 0) {
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductFeatureDataService.deleteById(id);
                    }


                    if (productInfo.getProductGatefolds() != null || productInfo.getProductGatefolds().size() != 0) {
                        List<ProductGatefoldsDTO> productFeaturesDTOArrayList = new ArrayList<ProductGatefoldsDTO>();
                        List<ProductMaterial> list = productInfo.getProductGatefolds();
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductGatefoldDataService.deleteById(id);
                        for (ProductMaterial productMaterial : list) {
                            ProductGatefoldsDTO productPicturesDTO = new ProductGatefoldsDTO();
                            productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                            productPicturesDTO.setFileId(productMaterial.getFileId());
                            productPicturesDTO.setName(productMaterial.getName());
                            productPicturesDTO.setProductId(id);
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            shopProductGatefoldDataService.insertBath(productFeaturesDTOArrayList);
                        }
                    }
                    if (productInfo.getProductGatefolds() == null || productInfo.getProductGatefolds().size() == 0) {
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductGatefoldDataService.deleteById(id);
                    }


                    if (productInfo.getProductOuterPackingBoxes() != null || productInfo.getProductOuterPackingBoxes().size() != 0) {
                        List<ProductOuterPackingBoxesDTO> productFeaturesDTOArrayList = new ArrayList<ProductOuterPackingBoxesDTO>();
                        List<ProductMaterial> list = productInfo.getProductOuterPackingBoxes();
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductOuterPackingBoxeDataService.deleteById(id);
                        for (ProductMaterial productMaterial : list) {
                            ProductOuterPackingBoxesDTO productPicturesDTO = new ProductOuterPackingBoxesDTO();
                            productPicturesDTO.setDisplayName(productMaterial.getDisplayName());
                            productPicturesDTO.setFileId(productMaterial.getFileId());
                            productPicturesDTO.setName(productMaterial.getName());
                            productPicturesDTO.setProductId(id);
                            productPicturesDTO.setUrl(productMaterial.getUrl());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            shopProductOuterPackingBoxeDataService.insertBath(productFeaturesDTOArrayList);
                        }
                    }
                    if (productInfo.getProductOuterPackingBoxes() == null || productInfo.getProductOuterPackingBoxes().size() == 0) {
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductOuterPackingBoxeDataService.deleteById(id);
                    }


                    if (productInfo.getProductSpecifications() != null || productInfo.getProductSpecifications().size() != 0) {
                        List<ProductSpecificationsDTO> productFeaturesDTOArrayList = new ArrayList<ProductSpecificationsDTO>();
                        List<ProductSpecification> list = productInfo.getProductSpecifications();
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductSpecificationDataService.deleteById(id);
                        for (ProductSpecification productMaterial : list) {
                            ProductSpecificationsDTO productPicturesDTO = new ProductSpecificationsDTO();
                            productPicturesDTO.setName(productMaterial.getName());
                            productPicturesDTO.setProductId(id);
                            productPicturesDTO.setTag(productMaterial.getTag());
                            productPicturesDTO.setValue(productMaterial.getValue());
                            productFeaturesDTOArrayList.add(productPicturesDTO);
                        }
                        if (productFeaturesDTOArrayList.size() != 0) {
                            shopProductSpecificationDataService.insertBath(productFeaturesDTOArrayList);
                        }
                    }
                    if (productInfo.getProductSpecifications() == null || productInfo.getProductSpecifications().size() == 0) {
                        Integer id = dto.getId();
                        //根据dto的id全删
                        shopProductSpecificationDataService.deleteById(id);
                    }
                }
            }
        }
    }
}
