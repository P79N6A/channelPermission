package com.haier.stock.model;

import java.io.Serializable;

public class InvDbmBase implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            estorge_id;            //电商库位码
    private String            base_code;             //发运基地码
    private String            base_name;             //发运基地名称
    private String            transport_prescription; //运输周期
    private String            create_user;
    private String            create_time;
    private String            update_user;
    private String            update_time;

    /**
     * @return Returns the estorge_id
     */
    public String getEstorge_id() {
        return estorge_id;
    }

    /**
     * @param estorge_id
     * The estorge_id to set.
     */
    public void setEstorge_id(String estorge_id) {
        this.estorge_id = estorge_id;
    }

    /**
     * @return Returns the base_code
     */
    public String getBase_code() {
        return base_code;
    }

    /**
     * @param base_code
     * The base_code to set.
     */
    public void setBase_code(String base_code) {
        this.base_code = base_code;
    }

    /**
     * @return Returns the base_name
     */
    public String getBase_name() {
        return base_name;
    }

    /**
     * @param base_name
     * The base_name to set.
     */
    public void setBase_name(String base_name) {
        this.base_name = base_name;
    }

    /**
     * @return Returns the transport_prescription
     */
    public String getTransport_prescription() {
        return transport_prescription;
    }

    /**
     * @param transport_prescription
     * The transport_prescription to set.
     */
    public void setTransport_prescription(String transport_prescription) {
        this.transport_prescription = transport_prescription;
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