package com.haier.stock.model;

import java.io.Serializable;

public class StockTransactionExist implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4528172982272229671L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer lesStockTransQueueId;

    public Integer getLesStockTransQueueId() {
        return lesStockTransQueueId;
    }

    public void setLesStockTransQueueId(Integer lesStockTransQueueId) {
        this.lesStockTransQueueId = lesStockTransQueueId;
    }
}