package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 李超 on 2018/3/3.
 */
public class InterfaceLog implements Serializable{

    private static final long serialVersionUID = -373769501370067857L;

    private Integer rowId;

    private Date interfaceDate;

    private String interfaceName;

    private String interfaceMessage;

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Date getInterfaceDate() {
        return interfaceDate;
    }

    public void setInterfaceDate(Date interfaceDate) {
        this.interfaceDate = interfaceDate;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceMessage() {
        return interfaceMessage;
    }

    public void setInterfaceMessage(String interfaceMessage) {
        this.interfaceMessage = interfaceMessage;
    }
}
