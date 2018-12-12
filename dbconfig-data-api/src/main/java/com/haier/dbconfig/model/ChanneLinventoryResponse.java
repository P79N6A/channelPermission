package com.haier.dbconfig.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class ChanneLinventoryResponse {

    /**
     * <?xml version="1.0" encoding="utf-8"?>
     * <response>
     * <flag>success|failure</flag>
     * <code>响应码</code>
     * <message>响应信息</message>
     * <itemInventories>
     * <itemInventory>
     * <itemCode>菜鸟商品编码,  string (50)，必填</itemCode>
     * <warehouseCode>仓库编码, string (50)，必填</warehouseCode>
     * <channelCode>渠道编码，String(50) ，必填</channelCodes>
     * <quantity>商品数量，int，必填</quantity>
     * <lockQuantity>锁库存数量，int，必填</lockQuantity >
     * </itemInventory>
     * </itemInventories>
     * </response>
     */

    private String flag;
    private String code;
    private String message;
    private ItemInventories itemInventories;

    public static class ItemInventories {

        private List<itemInventory> itemInventory;

        public List<ItemInventories.itemInventory> getItemInventory() {
            return itemInventory;
        }

        public void setItemInventory(List<ItemInventories.itemInventory> itemInventory) {
            this.itemInventory = itemInventory;
        }

        public static class itemInventory {

            private String itemCode;
            private String warehouseCode;
            private String channelCode;
            private String quantity;
            private String lockQuantity;

            public String getItemCode() {
                return itemCode;
            }

            public void setItemCode(String itemCode) {
                this.itemCode = itemCode;
            }

            public String getWarehouseCode() {
                return warehouseCode;
            }

            public void setWarehouseCode(String warehouseCode) {
                this.warehouseCode = warehouseCode;
            }

            public String getChannelCode() {
                return channelCode;
            }

            public void setChannelCode(String channelCode) {
                this.channelCode = channelCode;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getLockQuantity() {
                return lockQuantity;
            }

            public void setLockQuantity(String lockQuantity) {
                this.lockQuantity = lockQuantity;
            }

        }
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ItemInventories getItemInventories() {
        return itemInventories;
    }

    public void setItemInventories(ItemInventories itemInventories) {
        this.itemInventories = itemInventories;
    }
}
