/**
 * Copyright (c) ehaier.com 2013 All Rights Reserved.
 */
package com.haier.stock.model;

/**
 *                       
 * @Filename: InventoryBusinessTypes.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 *
 */
public enum InventoryBusinessTypes {
    /** 销售出库 */
    OUT_SALE("ZBCC", "H"),
    /**存性变更出库*/
    OUT_CHANGE("YTIB", "H"),
    /** 调拨出库 */
    OUT_TRANSFER("ZGI6", "H"),
    /** 转运出库 */
    OUT_TRANSPORT("BRSI", "H"),
    /** 京东虚拟退货出库 */
    OUT_RETURNED_JD("ZBJT", "H"),
    /**采购入库 */
    IN_PURCHASE("ZBCR", "S"),
    /**存性变更入库*/
    IN_CHANGE("YTRB", "S"),
    /** 退货入库 */
    IN_RETURNED("ZBCT", "S"),
    /** 拒收入库 */
    IN_REFUSE("ZBCJ", "S"),
    /** 调拨入库 */
    IN_TRANSFER("ZGR6", "S"),
    /** 转运入库 */
    IN_TRANSPORT("ZRSR", "S"),

    /** 调拨冻结 */
    FROZEN_BY_TRANSFER("DBFF", "H"),
    /** 销售冻结 */
    FROZEN_BY_ZBCC("XSFF", "H"),
    /**手动冻结**/
    FROZEN_BY_MEN("SDFF", "H"),
    /** 取消调拨释放 */
    RELEASE_BY_TRANSFER("DBRR", "S"),
    /** 取消销售释放 */
    RELEASE_BY_ZBCC("XSRR", "S"),
    /** 取消手动释放**/
    RELEASE_BY_MEN("SDRR", "S"),
    /** 重新分配库位释放 */
    RELEASE_BY_REASSIGN_SECCODE("RARR", "S"),
    /**3W正品退仓(3W转仓的逆向 )*/
    RETURN_WAREHOUSE_3W("YTR1","S"),
    
    /** 同步库存*/
    SYNC_STOCK("SYNC", ""),

    /** 未定义的 */
    UNDEFINED("UN", "");
    

    /** 业务类型编码 */
    private final String code;
    /** 业务类型借贷方向 H-出 S-入*/
    private final String mark;

    private InventoryBusinessTypes(String code, String mark) {
        this.code = code;
        this.mark = mark;
    }

    public String getCode() {
        return code;
    }

    public String getMark() {
        return mark;
    }

    public static InventoryBusinessTypes getByCode(String code) {
        for (InventoryBusinessTypes type : values()) {
            if (type.getCode().equalsIgnoreCase(code))
                return type;
        }
        return UNDEFINED;
    }

}
