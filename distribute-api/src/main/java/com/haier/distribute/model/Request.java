package com.haier.distribute.model;

import java.util.List;

public class Request {
    public void setList(List<Result> list) {
        this.list = list;
    }

    private List<Result> list;

    public List<Result> getList() {
        return list;
    }
}
