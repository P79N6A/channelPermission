package com.haier.order.services;

import com.haier.order.model.ItemModel;
import com.haier.shop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.common.ServiceResult;
import com.haier.eis.model.EisExternalSku;

@Service
public class OrderCenterItemServiceImplByHwl {
    private static Logger log = LoggerFactory.getLogger(OrderCenterItemServiceImplByHwl.class);
    @Autowired
    private ItemModel itemModel;

    public void hello() {
        log.info(">>> hello job scheduler"); //test
    }

    public ServiceResult<ProductsNew> getProductBySku(String sku) {
        ServiceResult<ProductsNew> result = new ServiceResult<ProductsNew>();
        try {
            result.setResult(itemModel.getProductsBySku(sku));
        } catch (Exception e) {
            log.error("根据sku(" + sku + ")获取产品时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<Brands> getBrands(Integer brandId) {
        ServiceResult<Brands> result = new ServiceResult<Brands>();
        try {
            result.setResult(itemModel.getBrandsById(brandId));
        } catch (Exception e) {
            log.error("根据id(" + brandId + ")获取品牌时，发生未知异常：", e);
            result.setSuccess(false);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
        }
        return result;
    }

    //1
    public ServiceResult<Payments> getPaymentByCode(String paymentCode) {
        ServiceResult<Payments> result = new ServiceResult<Payments>();
        try {
            result.setResult(itemModel.getPaymentByCode(paymentCode));
        } catch (Exception e) {
            log.error("根据code查询付款方式时，发生未知异常：", e);
            result.setMessage("根据code查询付款方式发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<NetPoints> getNetPoint(Integer id) {
        ServiceResult<NetPoints> result = new ServiceResult<NetPoints>();
        try {
            result.setResult(itemModel.getNetPoint(id));
        } catch (Exception e) {
            log.error("根据id查询网点时，发生未知异常：", e);
            result.setMessage("根据id查询网点发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<EisExternalSku> getBySkuType(String sku, String type) {
        ServiceResult<EisExternalSku> result = new ServiceResult<EisExternalSku>();
        try {
            result.setResult(itemModel.getBySkuType(sku, type));
        } catch (Exception e) {
            log.error("根据sku和type查询物料对照信息时，发生未知异常：", e);
            result.setMessage("根据sku和type查询物料对照信息发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<ProductsNew> getProductById(Integer id) {
        ServiceResult<ProductsNew> result = new ServiceResult<ProductsNew>();
        try {
            result.setResult(itemModel.getProductsById(id));
        } catch (Exception e) {
            log.error("根据id(" + id + ")获取产品时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<ProductTypesNew> getProductType(Integer typeId) {
        ServiceResult<ProductTypesNew> result = new ServiceResult<ProductTypesNew>();
        try {
            result.setResult(itemModel.getProductType(typeId));
        } catch (Exception e) {
            log.error("根据id(" + typeId + ")获取产品类型时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}