package com.haier.purchase.data.model;

import java.io.Serializable;

public class GateItemForTransfer implements Serializable {

    private static final long serialVersionUID = 7861792198454611276L;
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private GateItem[]        gateItemData;
    private String            user;

    /**
     * @return Returns the gateItemData
     */
    public GateItem[] getGateItemData() {
        return gateItemData;
    }

    /**
     * @param gateItemData
     * The gateItemData to set.
     */
    public void setGateItemData(GateItem[] gateItemData) {
        this.gateItemData = gateItemData;
    }

    /**
     * @return Returns the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     * The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }
}