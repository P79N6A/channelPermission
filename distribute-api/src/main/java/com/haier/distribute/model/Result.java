package com.haier.distribute.model;

import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.model.ProductFeaturesDTO;
import com.haier.distribute.data.model.ProductGatefoldsDTO;
import com.haier.distribute.data.model.ProductOuterPackingBoxesDTO;
import com.haier.distribute.data.model.ProductPicturesDTO;
import com.haier.distribute.data.model.ProductSpecificationsDTO;

import java.util.List;

public class Result {
    private ProductCenterDTO productCenterDTO;

    public void setProductCenterDTO(ProductCenterDTO productCenterDTO) {
        this.productCenterDTO = productCenterDTO;
    }

    public void setProductPicturesDTOList(List<ProductPicturesDTO> productPicturesDTOList) {
        this.productPicturesDTOList = productPicturesDTOList;
    }

    public void setProductGatefoldsDTOList(List<ProductGatefoldsDTO> productGatefoldsDTOList) {
        this.productGatefoldsDTOList = productGatefoldsDTOList;
    }

    public void setProductFeaturesDTOList(List<ProductFeaturesDTO> productFeaturesDTOList) {
        this.productFeaturesDTOList = productFeaturesDTOList;
    }

    public void setProductOuterPackingBoxesDTOList(List<ProductOuterPackingBoxesDTO> productOuterPackingBoxesDTOList) {
        this.productOuterPackingBoxesDTOList = productOuterPackingBoxesDTOList;
    }

    public void setProductSpecificationsDTOList(List<ProductSpecificationsDTO> productSpecificationsDTOList) {
        this.productSpecificationsDTOList = productSpecificationsDTOList;
    }

    private List<ProductPicturesDTO> productPicturesDTOList;
    private List<ProductGatefoldsDTO> productGatefoldsDTOList;
    private List<ProductFeaturesDTO> productFeaturesDTOList;

    public ProductCenterDTO getProductCenterDTO() {
        return productCenterDTO;
    }

    public List<ProductPicturesDTO> getProductPicturesDTOList() {
        return productPicturesDTOList;
    }

    public List<ProductGatefoldsDTO> getProductGatefoldsDTOList() {
        return productGatefoldsDTOList;
    }

    public List<ProductFeaturesDTO> getProductFeaturesDTOList() {
        return productFeaturesDTOList;
    }

    public List<ProductOuterPackingBoxesDTO> getProductOuterPackingBoxesDTOList() {
        return productOuterPackingBoxesDTOList;
    }

    public List<ProductSpecificationsDTO> getProductSpecificationsDTOList() {
        return productSpecificationsDTOList;
    }

    private List<ProductOuterPackingBoxesDTO> productOuterPackingBoxesDTOList;
    private List<ProductSpecificationsDTO> productSpecificationsDTOList;
}
