package com.haier.svc.api.controller.util;

import com.haier.common.ArgumentException;

import java.io.Serializable;

public class PagerInfo implements Serializable {
    private static final long serialVersionUID = 506758020097476778L;
    private int pageIndex = 1;
    private int pageSize = 20;
    private int rowsCount = 0;
    private int allpage;

    public PagerInfo() {
    }

    public PagerInfo(int pageSize, int pageIndex) {
        if (pageIndex <= 0) {
            throw new ArgumentException("分页信息错误，page index必须从1开始递增");
        } else if (pageSize <= 0) {
            throw new ArgumentException("分页信息错误，page size必须大于0");
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getStart() {
        return (this.pageIndex - 1) * this.pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getRowsCount() {
        return this.rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public String toString() {
        return "{ pageIndex:" + this.pageIndex + ", pageSize:" + this.pageSize + ", rowsCount:" + this.rowsCount + " }";
    }
    public int setAllpage() {
        return this.pageIndex;
    }

    public int getAllpage() {
        return this.pageIndex;
    }
}
