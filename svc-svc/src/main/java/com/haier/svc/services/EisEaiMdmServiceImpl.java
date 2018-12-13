package com.haier.svc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.haier.common.ServiceResult;
import com.haier.shop.model.ItemBase;
import com.haier.svc.model.EaiHandlerModel;
import com.haier.svc.model.ItemModel;
import com.haier.svc.service.EisEaiMdmService;

@Service
public class EisEaiMdmServiceImpl implements EisEaiMdmService{
	
	private static Logger log = LoggerFactory.getLogger(EisEaiMdmServiceImpl.class);
	
    @Autowired
    private ItemModel itemModel;
    @Autowired
    private EaiHandlerModel eaiHandlerModel;
    
    @Override
    public ServiceResult<Boolean> manualSyncMdmBySku(String sku, String modifier) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean flag = this.manualSyncMdmDataBySku(sku, modifier);
            if (flag) {
                result.setSuccess(true);
                result.setResult(true);
            } else {
                result.setSuccess(true);
                result.setResult(false);
                result.setMessage("MDM中没有此物料可以同步的信息！");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("手动同步MDM信息时发生未知异常");
            log.error("手动同步MDM信息时发生未知异常：" + e);
        }
        return result;
    }

    /**
     * 手动同步MDM信息
     */
    public boolean manualSyncMdmDataBySku(String sku, String modifier) {
        List<String> skus = new ArrayList<String>();
        skus.add(sku);
        List<ItemBase> mtls = eaiHandlerModel.getMtlsBySkus(skus);
        if (mtls == null || mtls.size() == 0) {
            return false;
        }
        for (ItemBase mtl : mtls) {
            ServiceResult<Boolean> itemRet = this.manualSyncMdmBySkuDate(mtl, modifier);
            if (!itemRet.getSuccess() && !itemRet.getResult()) {
                log.error("手动同步SkU为" + mtl.getMaterialCode() + "的物料基本信息时发生异常");
            } else if (itemRet.getSuccess() && !itemRet.getResult()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 手动同步物料基本信息
     * @param itemBase 物料信息
     * @param modifier 修改人
     * @return 修复结果
     */
    public ServiceResult<Boolean> manualSyncMdmBySkuDate(ItemBase itemBase, String modifier) {
        ServiceResult<Boolean> ret = new ServiceResult<Boolean>();
        try {
            int count = itemModel.manualSyncMdmBySku(itemBase, modifier);
            if (count == 0) {
                ret.setSuccess(true);
                ret.setResult(false);
                ret.setMessage("MDM中没有此物料可以同步的信息！");
            } else {
                ret.setSuccess(true);
                ret.setResult(true);
            }
        } catch (Throwable e) {
            ret.setSuccess(false);
            ret.setResult(false);
            ret.setMessage("手动同步物料基本信息时发生未知异常");
            log.error("手动同步物料基本信息时发生未知异常：", e);
        }
        return ret;
    }
  
}
