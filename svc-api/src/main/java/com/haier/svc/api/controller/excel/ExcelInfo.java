package com.haier.svc.api.controller.excel;

import java.util.List;

/**
 * Created by jtbshan on 2018/5/24.
 */
public class ExcelInfo {
    private List<String> titleInfo;
    private List<List<String>> bobyInfo;

    public List<String> getTitleInfo() {
        return titleInfo;
    }

    public void setTitleInfo(List<String> titleInfo) {
        this.titleInfo = titleInfo;
    }

    public List<List<String>> getBobyInfo() {
        return bobyInfo;
    }

    public void setBobyInfo(List<List<String>> bobyInfo) {
        this.bobyInfo = bobyInfo;
    }
}
