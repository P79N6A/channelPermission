package com.haier.svc.helper;

public interface Handler {

    boolean beforeProcess();

    boolean process() throws Exception;

    boolean afterProcess();

}
