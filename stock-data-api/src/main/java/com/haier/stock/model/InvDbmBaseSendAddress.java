package com.haier.stock.model;

import java.io.Serializable;

public class InvDbmBaseSendAddress implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            id;
    private String            CORPORATION_ID;       //销售公司编码 
    private String            UNIT_CODE;            //法人客户编码
    private String            UNIT_NAME;            //法人客户名称
    private String            CUST_UNIT_CODE;       //业务客户代码
    private String            CUST_UNIT_NAME;       //业务客户名称
    private String            ADDRESS_ID;           //地址5码
    private String            ZIP_CODE;             //邮编
    private String            ADDRESS_NAME;         //地址简称
    private String            storage_id;           //电商库位码
    private String            create_user;
    private String            create_time;
    private String            update_user;
    private String            update_time;

    /**
     * @return Returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     * The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the cORPORATION_ID
     */
    public String getCORPORATION_ID() {
        return CORPORATION_ID;
    }

    /**
     * @param cORPORATION_ID
     * The cORPORATION_ID to set.
     */
    public void setCORPORATION_ID(String cORPORATION_ID) {
        CORPORATION_ID = cORPORATION_ID;
    }

    /**
     * @return Returns the uNIT_CODE
     */
    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    /**
     * @param uNIT_CODE
     * The uNIT_CODE to set.
     */
    public void setUNIT_CODE(String uNIT_CODE) {
        UNIT_CODE = uNIT_CODE;
    }

    /**
     * @return Returns the uNIT_NAME
     */
    public String getUNIT_NAME() {
        return UNIT_NAME;
    }

    /**
     * @param uNIT_NAME
     * The uNIT_NAME to set.
     */
    public void setUNIT_NAME(String uNIT_NAME) {
        UNIT_NAME = uNIT_NAME;
    }

    /**
     * @return Returns the cUST_UNIT_CODE
     */
    public String getCUST_UNIT_CODE() {
        return CUST_UNIT_CODE;
    }

    /**
     * @param cUST_UNIT_CODE
     * The cUST_UNIT_CODE to set.
     */
    public void setCUST_UNIT_CODE(String cUST_UNIT_CODE) {
        CUST_UNIT_CODE = cUST_UNIT_CODE;
    }

    /**
     * @return Returns the cUST_UNIT_NAME
     */
    public String getCUST_UNIT_NAME() {
        return CUST_UNIT_NAME;
    }

    /**
     * @param cUST_UNIT_NAME
     * The cUST_UNIT_NAME to set.
     */
    public void setCUST_UNIT_NAME(String cUST_UNIT_NAME) {
        CUST_UNIT_NAME = cUST_UNIT_NAME;
    }

    /**
     * @return Returns the aDDRESS_ID
     */
    public String getADDRESS_ID() {
        return ADDRESS_ID;
    }

    /**
     * @param aDDRESS_ID
     * The aDDRESS_ID to set.
     */
    public void setADDRESS_ID(String aDDRESS_ID) {
        ADDRESS_ID = aDDRESS_ID;
    }

    /**
     * @return Returns the zIP_CODE
     */
    public String getZIP_CODE() {
        return ZIP_CODE;
    }

    /**
     * @param zIP_CODE
     * The zIP_CODE to set.
     */
    public void setZIP_CODE(String zIP_CODE) {
        ZIP_CODE = zIP_CODE;
    }

    /**
     * @return Returns the aDDRESS_NAME
     */
    public String getADDRESS_NAME() {
        return ADDRESS_NAME;
    }

    /**
     * @param aDDRESS_NAME
     * The aDDRESS_NAME to set.
     */
    public void setADDRESS_NAME(String aDDRESS_NAME) {
        ADDRESS_NAME = aDDRESS_NAME;
    }

    /**
     * @return Returns the storage_id
     */
    public String getStorage_id() {
        return storage_id;
    }

    /**
     * @param storage_id
     * The storage_id to set.
     */
    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    /**
     * @return Returns the create_user
     */
    public String getCreate_user() {
        return create_user;
    }

    /**
     * @param create_user
     * The create_user to set.
     */
    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    /**
     * @return Returns the create_time
     */
    public String getCreate_time() {
        return create_time;
    }

    /**
     * @param create_time
     * The create_time to set.
     */
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    /**
     * @return Returns the update_user
     */
    public String getUpdate_user() {
        return update_user;
    }

    /**
     * @param update_user
     * The update_user to set.
     */
    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    /**
     * @return Returns the update_time
     */
    public String getUpdate_time() {
        return update_time;
    }

    /**
     * @param update_time
     * The update_time to set.
     */
    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

}