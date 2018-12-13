package com.haier.stock.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceStatus;
import com.haier.eis.service.EisInterfaceStatusService;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.stock.helper.EAImdmHandler;
import com.haier.stock.service.ItemMdmService;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.util.Constants;

@Component
public class EisEaiMdmModel {

    private static Logger log = LoggerFactory.getLogger(EisEaiMdmModel.class);
    
    @Autowired
	private EAImdmHandler eaiHandler;
    @Autowired
    private EisInterfaceStatusService  eisInterfaceStatusService;
    @Autowired
    private ItemMdmService itemService;
    @Autowired
    private StockAgeService stockAgeService;
    
    public static int insertItemBase;
    public static int updateItemBase;
    public static int insertItemAttribute;
    public static int updateItemAttribute;

    /**
     * 定时增量更新产品属性/物料基本信息
     */
    public <E> void updateIncrementTargetFromMdm(Class<E> clazz) {
        if (clazz != ItemAttribute.class && clazz != ItemBase.class) {
            throw new RuntimeException("取得类型" + clazz.getName() + "指定错误");
        }

        EisInterfaceStatus eisInterfaceStatus = this.getEisInterfaceStatus(clazz);
        Date beginTime = eisInterfaceStatus.getLastTime();
        if (beginTime.after(new Date())) {
            if (log.isDebugEnabled())
                log.debug("结束时间已经大于当前时间，Job退出");
            return;
        }
        Date endTime = this.getEndTime(beginTime, clazz);
        List<E> list = eaiHandler.getTargetListFromMdm(beginTime, endTime, clazz);

        try {
            if (list != null && list.size() > 0) {
                for (E value : list) {
                    ServiceResult<Boolean> ret = null;
                    if (ItemAttribute.class.equals(clazz)) {
                        ret = itemService.saveItemAttribute((ItemAttribute) value);
                    } else if (ItemBase.class.equals(clazz)) {
                        ret = itemService.saveItemBase((ItemBase) value);
                    }
                    if (ret==null || !ret.getSuccess()) {
                        throw new RuntimeException(ret.getMessage());
                    }
                }
                log.info("共插入产品属性"+insertItemAttribute+"条，"
                		+ "共更新产品属性"+updateItemAttribute+"条，"
                	    + "共插入物料基本信息"+insertItemBase+"条"
                	    + "共更新物料基本信息"+updateItemBase+"条");
            }
            this.updateEisInterfaceStatus(eisInterfaceStatus, endTime);
        } catch (Exception e) {
            log.error("定时更新" + clazz.getSimpleName() + "时发生未知异常：" + e);
        }
    }

    private <E> Date getEndTime(Date beginTime, Class<E> clazz) {
        Calendar cald = Calendar.getInstance();
        cald.setTime(beginTime);
        cald.add(Calendar.HOUR_OF_DAY, 6);
        Date endTime = cald.getTime();
        Date now = new Date();
        if (endTime.after(now)) {
            cald.setTime(now);
            cald.add(Calendar.MINUTE, -30);
            endTime = cald.getTime();
        }
        return endTime;
    }

    private <E> EisInterfaceStatus getEisInterfaceStatus(Class<E> clazz) {
        String code = null;
        if (ItemAttribute.class.equals(clazz)) {
            code = Constants.EIS_INTERFACE_STATUS_INTERFACE_CODE_FOR_ITEM_ATTRIBUTE;
        } else if (ItemBase.class.equals(clazz)) {
            code = Constants.EIS_INTERFACE_STATUS_INTERFACE_CODE_FOR_ITEM_BASE;
        }
        EisInterfaceStatus eisInterfaceStatus = eisInterfaceStatusService.getByInterfaceCode(code);
        if (eisInterfaceStatus == null) {
            throw new RuntimeException("EIS接口状态表(eis_interface_status)中不存在code为" + code + "的记录");
        }
        return eisInterfaceStatus;
    }

    private void updateEisInterfaceStatus(EisInterfaceStatus eisInterfaceStatus, Date endTime) {
        eisInterfaceStatus.setLastTime(endTime);
        eisInterfaceStatus.setUpdateTime(new Date());
        eisInterfaceStatusService.update(eisInterfaceStatus);
    }

    /**
     * 更新信息不全的物料基本信息和库龄表的物料信息
     */
    public void updateMtlInfoBySku() {
        ServiceResult<List<ItemBase>> mtlRet = (ServiceResult<List<ItemBase>>) itemService.getIncompleteItemBaseList();
        if (!mtlRet.getSuccess()) {
            throw new RuntimeException("通过itemService取得信息不完整的物料基本信息时发生异常：" + mtlRet.getMessage());
        }
        List<ItemBase> mtls = mtlRet.getResult();
        List<String> skus = this.getIncompleteMtlSkus(mtls);
        mtls = eaiHandler.getMtlsBySkus(skus);
        if (mtls == null || mtls.size() == 0) {
            return;
        }
        for (ItemBase mtl : mtls) {
            ServiceResult<Integer> stockRet = stockAgeService.updateMtlInfoForStockAge(mtl);
            if (!stockRet.getSuccess()) {
                log.error("更新SkU为" + mtl.getMaterialCode()
                          + "的库龄表的物料信息(商品名称，品类，产品组，品牌)时发生异常,下次执行Job时会重新执行该操作");
                continue;
            }
            ServiceResult<Boolean> itemRet = itemService.saveItemBase(mtl);
            if (!itemRet.getSuccess()) {
                log.error("更新SkU为" + mtl.getMaterialCode() + "的物料基本信息时发生异常,下次执行Job时会重新执行该操作");
            }
        }
    }

    /**
     * 手动同步MDM信息
     */
    public boolean manualSyncMdmBySku(String sku, String modifier) {
        List<String> skus = new ArrayList<String>();
        skus.add(sku);
        List<ItemBase> mtls = eaiHandler.getMtlsBySkus(skus);
        if (mtls == null || mtls.size() == 0) {
            return false;
        }
        for (ItemBase mtl : mtls) {
            ServiceResult<Boolean> itemRet = itemService.manualSyncMdmBySku(mtl, modifier);
            if (!itemRet.getSuccess() && !itemRet.getResult()) {
                log.error("手动同步SkU为" + mtl.getMaterialCode() + "的物料基本信息时发生异常");
            } else if (itemRet.getSuccess() && !itemRet.getResult()) {
                return false;
            }
        }
        return true;
    }

    private List<String> getIncompleteMtlSkus(List<ItemBase> mtls) {
        List<String> skus = null;
        if (mtls != null && mtls.size() > 0) {
            skus = new ArrayList<String>();
            for (ItemBase mtl : mtls) {
                skus.add(mtl.getMaterialCode());
            }
        }
        return skus;
    }

}

