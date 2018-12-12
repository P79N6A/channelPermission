package com.haier.svc.api.form;


import com.haier.system.model.PermissionResource;

public class PermissionResourceForm extends PermissionResource {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3095980882785549241L;

    private boolean           leaf             = false;

    public boolean getLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean value) {
        this.leaf = value;
    }

    private boolean expanded = true;

    public boolean getExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean value) {
        this.expanded = value;
    }

    private String iconCls;

    public String getIconCls() {
        return this.iconCls;
    }

    public void setIconCls(String value) {
        this.iconCls = value;
    }
}