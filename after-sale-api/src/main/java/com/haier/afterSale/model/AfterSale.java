package com.haier.afterSale.model;

import java.io.Serializable;


public class AfterSale implements Serializable  {
    private static final long serialVersionUID = -3117914630224183116L;
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
     }

    public void setName(String name) {
        this.name = name;
    }
}
