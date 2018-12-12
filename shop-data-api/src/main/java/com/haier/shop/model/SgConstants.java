package com.haier.shop.model;
/**
 * 常量类
 *                   
 * @Filename: 
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public class SgConstants {

	 /** 库存锁定类型 */
    public static interface STOCK_LOCK_TYPE {
    	/**WA*/
    	public static final Integer WA=1;
    	/**EC*/
    	public static final Integer EC=2;
    	/**SG*/
    	public static final Integer SG=3;
    }
    /** 库存变动类型 */
    public static interface STOCK_CHANGE_TYPE {
    	/** 入库 */
    	public static final Integer IN=1;
    	/** 出库 */
    	public static final Integer OUT=2;
    }
    
    public static interface ITEM_PROPERTY{
    	  /**
         * 正品库
         */
        public static String      W10                  = "10";

        /**
         * 非卖品
         */
        public static String      W90                  = "90";
        /**
         * 不良品库
         */
        public static String      W21                  = "21";
        /**
         * 开箱正品
         */
        public static String      W22                  = "22";

        /**
         * 样品机库
         */
        public static String      W40                  = "40";
    }
    /** 库存log库存变动类型*/
    public static interface STOCK_CHANGE_TYPE_LOG{
    	/** 入库 */
    	public static final String IN="S";
    	/** 出库 */
    	public static final String OUT="H";
    }
    
    /** 商品类型*/
    public static interface PRODUCT_O2O_TYPE{
    	/** 商城专供型号 */
    	public static final Integer WA=1;
    	/** O2O共享型号 */
    	public static final Integer O2O_SHARE=2;
    	/** O2O引流型号 */
    	public static final Integer O2O_GUIDE=3;
    }
    /** 交易类型 */
    public static interface STOCK_BILL_TYPE{
    	/** 店铺入库 */
    	public static final String STORE_IN="1";
    	/** 销售占库 */
    	public static final String SALE_LOCK="2";
    	/** 释放出库 */
    	public static final String RELEASE_OUT="3";
    	/** 退货入库 */
    	public static final String RETURN_GOODS_IN="4";
    }
  
}
