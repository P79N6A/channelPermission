package com.haier.svc.api.form;

import java.util.List;

public class MenuTreeRoot {
    private String name = ".";

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    private List<MenuItem> children;

    public List<MenuItem> getChildren() {
        return this.children;
    }

    public void setChildren(List<MenuItem> value) {
        this.children = value;
    }

    private boolean expanded = true;

    public boolean getExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean value) {
        this.expanded = value;
    }
}