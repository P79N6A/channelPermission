package com.haier.stock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.haier.common.ServiceResult;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.EisEaiMdmModel;
import com.haier.stock.service.ItemBaseFromMdmService;

@Service
public class ItemBaseFromMdmServiceImpl  implements ItemBaseFromMdmService{
	
	private static Logger log = LoggerFactory.getLogger(ItemBaseFromMdmServiceImpl.class);
	
	@Autowired
	private EisEaiMdmModel eisEaiMdmModel;
	  /**
     * 定时更新物料基本信息接口(Job触发)
     * @return
     */
    @Override
    public ServiceResult<Boolean> updateItemBaseFromMdm() {
        return this.updateIncrementTargetFromMdm(ItemBase.class);
    }

    private <E> ServiceResult<Boolean> updateIncrementTargetFromMdm(Class<E> clazz) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            eisEaiMdmModel.updateIncrementTargetFromMdm(clazz);
            result.setSuccess(true);
            result.setResult(true);
            result.setMessage("更新物料基本信息成功"+"共插入产品属性"+EisEaiMdmModel.insertItemAttribute+"条，"
            		+ "共更新产品属性"+EisEaiMdmModel.updateItemAttribute+"条，"
            	    + "共插入物料基本信息"+EisEaiMdmModel.insertItemBase+"条"
            	    + "共更新物料基本信息"+EisEaiMdmModel.updateItemBase+"条");
        } catch (Exception e) {
            log.error("定时更新" + clazz.getSimpleName() + "时发生未知异常：" + e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("定时更新" + clazz.getSimpleName() + "时发生未知异常");
        }
        return result;
    }

}
