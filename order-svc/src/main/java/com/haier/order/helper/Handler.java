package com.haier.order.helper;

public interface Handler {

    boolean beforeProcess();

    boolean process() throws Exception;

    boolean afterProcess();

}
