package com.haier.eis.model;

import java.io.Serializable;

public class APIResult implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String            out;
    private Integer           pageCount;
    private Integer           currentPage;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
