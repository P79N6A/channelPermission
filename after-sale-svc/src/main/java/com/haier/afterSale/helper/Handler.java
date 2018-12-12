package com.haier.afterSale.helper;

public interface Handler {

    boolean beforeProcess();

    boolean process() throws Exception;

    boolean afterProcess();

}
