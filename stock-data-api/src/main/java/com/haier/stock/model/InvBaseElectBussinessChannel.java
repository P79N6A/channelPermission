package com.haier.stock.model;

import java.io.Serializable;

/**
 * 采购项目
 */
public class InvBaseElectBussinessChannel implements Serializable {
    private String sap_channel_code;
    private String description;
    private String cbs_channel_code;

    /**
     * @return Returns the sap_channel_code
     */
    public String getSap_channel_code() {
        return sap_channel_code;
    }

    /**
     * @param sap_channel_code
     * The sap_channel_code to set.
     */
    public void setSap_channel_code(String sap_channel_code) {
        this.sap_channel_code = sap_channel_code;
    }

    /**
     * @return Returns the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     * The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the cbs_channel_code
     */
    public String getCbs_channel_code() {
        return cbs_channel_code;
    }

    /**
     * @param cbs_channel_code
     * The cbs_channel_code to set.
     */
    public void setCbs_channel_code(String cbs_channel_code) {
        this.cbs_channel_code = cbs_channel_code;
    }

}
