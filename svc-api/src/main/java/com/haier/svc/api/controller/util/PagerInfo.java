package com.haier.svc.api.controller.util;

import com.haier.common.ArgumentException;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

public class PagerInfo implements Serializable {
    private static final long serialVersionUID = 506758020097476778L;
    /**
     * 页数
     */
    private int pageIndex = 1;
    /**
     * 行数
     */
    private int pageSize = 20;
    /**
     * 总条数
     */
    private int rowsCount = 0;

    /**
     * 页面偏移
     */
    private long pageOffset;
    /**
     * 排序字段
     */
    private String sort;
    /**
     * 顺序方式
     */
    private String order;

    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 起始页索引
     */
    private long startPageIndex;
    /**
     * 结束页索引
     */
    private long endPageIndex;
    private long pageCode = 5;
    private long previewPage = 1;
    private long nextPage = 1;

    public PagerInfo() {
    }

    public PagerInfo(int page) {
        this.setPage(page);
        this.pageSize = 20;
    }
    public void setPage(int page) {
        if (page > 0) {
            this.pageIndex = page;
        }

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
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }

    }
    public int getRowsCount() {
        return this.rowsCount;
    }

    public void setRowsCount(int totalCount) {
        this.rowsCount = totalCount;
        this.setTotalPage(
            totalCount % this.pageSize == 0 ? totalCount / this.pageSize : totalCount / this.pageSize + 1);
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        this.startPageIndex =
            this.pageIndex - (this.pageCode % 2 == 0 ? this.pageCode / 2 - 1 : this.pageCode / 2);
        this.endPageIndex = this.pageIndex + this.pageCode / 2;
        if (this.startPageIndex < 1) {
            this.startPageIndex = 1;
            if (totalPage >= this.pageCode) {
                this.endPageIndex = this.pageCode;
            } else {
                this.endPageIndex = totalPage;
            }
        }

        if (this.endPageIndex > totalPage) {
            this.endPageIndex = totalPage;
            if (this.endPageIndex - this.pageCode > 0) {
                this.startPageIndex = this.endPageIndex - this.pageCode + 1;
            } else {
                this.startPageIndex = 1;
            }
        }

        if (this.endPageIndex <= 1) {
            this.endPageIndex = 1;
        }

        this.previewPage = this.pageIndex - 1;
        this.nextPage = this.pageIndex + 1;
        if (this.pageIndex <= 1) {
            this.previewPage = 1;
        }

        if (this.pageIndex >= this.totalPage) {
            this.nextPage = this.totalPage;
        }

    }


    public long getPageOffset() {
        this.pageOffset = (this.pageIndex - 1) * this.pageSize;
        return this.pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public String getSort() {
        return camelToUnderline(this.sort);
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    private String camelToUnderline(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append('_');
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public long getTotalPage() {
        return this.totalPage;
    }

    public long getPreviewPage() {
        return this.previewPage;
    }

    public void setPreviewPage(long previewPage) {
        this.previewPage = previewPage;
    }

    public long getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(long nextPage) {
        this.nextPage = nextPage;
    }
}
