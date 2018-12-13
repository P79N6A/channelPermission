package com.haier.afterSale.service;

import java.net.MalformedURLException;
import java.text.ParseException;

public interface HandleDefectiveService {
    public void treatmentforDefective();
    public void returnWarehouse() throws ParseException;//下退仓
    public void pushVom();//推送vom
    public void pushOutSAP()throws MalformedURLException;//推送出库sap
    public void pushInSAP()throws MalformedURLException;//推送入库sap
    public void cannelReturnWarehouse();//取消退仓
    public void InitiateQualityInspection()throws ParseException, MalformedURLException;
}
