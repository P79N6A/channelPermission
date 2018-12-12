package com.haier.purchase.data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 *                       
 * @Filename: productLimitGate.java
 * @Version: 1.0
 * @Author: naxiang 那响
 * @Email: xiang.na@dhc.com.cn
 *
 */
public class ProductLimitGate implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8207134953576558636L;

    private Integer           id;

    private String            channel_code;                           //渠道编号

    private String            group_type;                             //产品组编码

    private String            product_type;                           //品类

    private String            model_type;                             //型号

    private int               type;                                   //闸口类型（0下市，1上单）

    private Integer           order_num;                              //上单适量

    private Date              start_time;                             //起始时间

    private Date              end_time;                               //截止时间

    private String            create_user;                            //创建者

    private Date              create_time;                            //创建时间

    private String            update_user;                            //修改者

    private Date              update_time;                            //修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel_code() {
        return channel_code;
    }

    public void setChannel_code(String channel_code) {
        this.channel_code = channel_code;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getOrder_num() {
        return order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "[id=" + id + ", channel_code=" + channel_code + ", group_type="
               + group_type + ", product_type=" + product_type + ", model_type=" + model_type
               + ", type=" + type + ", order_num=" + order_num + ", start_time="
               + sdf.format(start_time) + ", end_time=" + sdf.format(end_time) + "]";
    }

}