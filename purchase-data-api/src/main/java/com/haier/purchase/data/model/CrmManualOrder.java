package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * Created by 李超 on 2018/3/13.
 */
public class CrmManualOrder implements Serializable{

    private static final long serialVersionUID = 8759882440708452328L;

    private CrmOrderManualItem       crmOrderManualItem;

    private CrmOrderManualDetailItem crmOrderManualDetailItem;

    /**
     * @return Returns the crmOrderManualItem
     */
    public CrmOrderManualItem getCrmOrderManualItem() {
        return crmOrderManualItem;
    }

    /**
     * @param crmOrderManualItem
     * The crmOrderManualItem to set.
     */
    public void setCrmOrderManualItem(CrmOrderManualItem crmOrderManualItem) {
        this.crmOrderManualItem = crmOrderManualItem;
    }

    /**
     * @return Returns the crmOrderManualDetailItem
     */
    public CrmOrderManualDetailItem getCrmOrderManualDetailItem() {
        return crmOrderManualDetailItem;
    }

    /**
     * @param crmOrderManualDetailItem
     * The crmOrderManualDetailItem to set.
     */
    public void setCrmOrderManualDetailItem(CrmOrderManualDetailItem crmOrderManualDetailItem) {
        this.crmOrderManualDetailItem = crmOrderManualDetailItem;
    }
}
