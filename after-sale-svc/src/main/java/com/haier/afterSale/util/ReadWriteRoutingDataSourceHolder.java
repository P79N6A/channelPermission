package com.haier.afterSale.util;

public class ReadWriteRoutingDataSourceHolder {
    private static final ThreadLocal<String> holder       = new ThreadLocal<String>();
    private static final ThreadLocal<Boolean> masterHolder = new ThreadLocal<Boolean>();

    public static void put(String value) {
        holder.set(value);
    }

    public static String get() {
        if (null != masterHolder.get() && masterHolder.get()) {
            return "";
        }
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }

    /**
     * 设置是否需要整个事务都需要主库
     * @param isAlwaysMaster
     */
    public static void setIsAlwaysMaster(Boolean isAlwaysMaster) {
        masterHolder.set(isAlwaysMaster);
    }

    /**
     * 清除整个事务需要主库设置
     */
    public static void clearIsAlwaysMaster() {
        masterHolder.remove();
    }
}
