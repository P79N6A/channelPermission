package com.haier.stock.services.Helper;

public interface Handler {

    boolean beforeProcess();

    boolean process() throws Exception;

    boolean afterProcess();

}
