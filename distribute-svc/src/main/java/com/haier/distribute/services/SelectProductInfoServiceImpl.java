package com.haier.distribute.services;

import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.model.ProductFeaturesDTO;
import com.haier.distribute.data.model.ProductGatefoldsDTO;
import com.haier.distribute.data.model.ProductOuterPackingBoxesDTO;
import com.haier.distribute.data.model.ProductPicturesDTO;
import com.haier.distribute.data.model.ProductSpecificationsDTO;
import com.haier.distribute.data.service.ProductCenterService;
import com.haier.distribute.data.service.ProductFeatureService;
import com.haier.distribute.data.service.ProductGatefoldService;
import com.haier.distribute.data.service.ProductOuterPackingBoxeService;
import com.haier.distribute.data.service.ProductPictureService;
import com.haier.distribute.data.service.ProductSpecificationService;
import com.haier.distribute.model.Request;
import com.haier.distribute.model.Result;
import com.haier.distribute.service.SelectProductInfoService;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SelectProductInfoServiceImpl implements SelectProductInfoService {
    //商品基本信息
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
    private static final Logger logger = LogManager.getLogger(PushProductInfoServiceImpl.class);
    public String getProductByCode(String code){
        ProductCenterDTO productCenterDTO=productCenterService.selectByCode(code);
        if (productCenterDTO!=null){
            Integer id=productCenterDTO.getId();
            List<ProductPicturesDTO> productPicturesDTOList=productPictureService.selectByProductId(id);
            List<ProductGatefoldsDTO> productGatefoldsDTOList=productGatefoldService.selectByProductId(id);
            List<ProductFeaturesDTO> productFeaturesDTOList=productFeatureService.selectByProductId(id);
            List<ProductOuterPackingBoxesDTO> productOuterPackingBoxesDTOList=productOuterPackingBoxeService.selectByProductId(id);
            List<ProductSpecificationsDTO> productSpecificationsDTOList=productSpecificationService.selectByProductId(id);
            Result result =new Result();
            result.setProductCenterDTO(productCenterDTO);
            result.setProductFeaturesDTOList(productFeaturesDTOList);
            result.setProductGatefoldsDTOList(productGatefoldsDTOList);
            result.setProductOuterPackingBoxesDTOList(productOuterPackingBoxesDTOList);
            result.setProductPicturesDTOList(productPicturesDTOList);
            result.setProductSpecificationsDTOList(productSpecificationsDTOList);
            List<Result> results=new ArrayList<>();
            results.add(result);
            Request request=new Request();
            request.setList(results);
            XStream xstream = new XStream();
            xstream.alias("request", Request.class);
            xstream.alias("result", Result.class);
            xstream.alias("ProductFeaturesDTO", ProductFeaturesDTO.class);
            xstream.alias("productCenterDTO", ProductCenterDTO.class);
            xstream.alias("productPicturesDTO", com.haier.distribute.data.model.ProductPicturesDTO.class);
            xstream.alias("productGatefoldsDTO", ProductGatefoldsDTO.class);
            xstream.alias("productOuterPackingBoxesDTO", ProductOuterPackingBoxesDTO.class);
            xstream.alias("productSpecificationsDTO", ProductSpecificationsDTO.class);
            String xml = xstream.toXML(request);
            return xml;
        }
        if (productCenterDTO==null){
            XStream xstream = new XStream();
            Request request=new Request();
            String xml = xstream.toXML(request);
            return xml;
        }
        return "";
    }
}
