package com.haier.stock.model;

import com.haier.common.util.StringUtil;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.service.ShopItemAttributeService;
import com.haier.shop.service.ShopItemBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service("itemModelnew")
public class ItemModel {
    @Autowired
    private ShopItemBaseService itemBaseDao;
    @Autowired
    private ShopItemAttributeService itemAttributeDao;
    public ItemBase getItemBaseBySku(String sku) {
        ItemBase param = new ItemBase();
        param.setMaterialCode(sku);
        param.setDeleteFlag(0);
        List<ItemBase> list = itemBaseDao.getBySku(param);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public void saveItemBase(ItemBase itemBase) {
        ItemBase tmpItemBase = null;
        //设置查询为非删除数据,有可能更新为已删除
        Integer newDeleteFlag = itemBase.getDeleteFlag();
        itemBase.setDeleteFlag(0);
        List<ItemBase> mtlList = itemBaseDao.getBySku(itemBase);
        itemBase.setDeleteFlag(newDeleteFlag);
        if (mtlList != null && mtlList.size() > 0) {
            tmpItemBase = mtlList.get(0);
            //如果设置了不自动更新,不更新
            if (tmpItemBase.getIsAutoUpdate() != null && tmpItemBase.getIsAutoUpdate() == 0) {
                return;
            }
            itemBase.setId(tmpItemBase.getId());
        }

        if (tmpItemBase != null) {
            if (null == tmpItemBase.getIsAutoUpdate()) {
                itemBase.setIsAutoUpdate(1);
            } else {
                itemBase.setIsAutoUpdate(tmpItemBase.getIsAutoUpdate());
            }
            if (null == tmpItemBase.getStatus()) {
                itemBase.setStatus(0);
            } else {
                itemBase.setStatus(tmpItemBase.getStatus());
            }
            if (null == itemBase.getModifier()) {
                itemBase.setModifier("system");
            }
            itemBase.setLastUpd(new Date());
            itemBaseDao.update(itemBase);
        } else {
            itemBase.setIsAutoUpdate(1);
            itemBase.setStatus(0);
            if (!StringUtil.isEmpty(itemBase.getSaleChar())
                    && itemBase.getSaleChar().length() > 1) {
                itemBase.setSaleChar(itemBase.getSaleChar().trim().substring(0, 1));
            }
            itemBaseDao.insert(itemBase);
        }
    }
    public ItemAttribute getItemAttributeByValueSetIdAndValue(String valueSetId, String value) {
        ItemAttribute param = new ItemAttribute();
        param.setValue(value);
        param.setValueSetId(valueSetId);
        param.setDeleteFlag(0);
        List<ItemAttribute> list = itemAttributeDao.getByValueAndValueSetId(param);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
