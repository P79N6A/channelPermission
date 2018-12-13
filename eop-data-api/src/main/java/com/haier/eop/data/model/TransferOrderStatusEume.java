package com.haier.eop.data.model;

/**
 * 调拨单状态
 *
 * 菜鸟状态
 * orderStatus='0' ACCEPT  接收
 * orderStatus='10' PARTTRANSFEROUT  部分出库
 * orderStatus='20' PARTTRANSFEROUTANDIN 部分出库部分入库
 * orderStatus='30' ALLTRANSFEROUT  全部出库
 * orderStatus='40' ALLTRANSFEROUTANDPARTIN 全部出库部分入库
 * orderStatus='100' TRANSFERCONFIRMED 全部入库
 *
 * 海尔处理流程  1. 全部出库/全部出库部分入库->全部入库->出库推SAP->入库推SAP(关闭)
 *             2. 全部入库->出库推SAP->入库推SAP(关闭)
 */
public enum TransferOrderStatusEume {
    ACCEPT(0),
    PARTTRANSFEROUT(10),
    PARTTRANSFEROUTANDIN(20),
    ALLTRANSFEROUT(30),
    ALLTRANSFEROUTANDPARTIN(40),
    TRANSFERCONFIRMED(100),     //已推送SAP，关闭;
    NONEEDAPPLYTOSAP(101),      //无需推送SAP，关闭;(同仓位调拨)
    CANCELED(-100),             //已取消
    MANUALINTERVENTION(110),    //人工介入状态
    NOAPPLYFORCAINIAO(120),     //创建未请求菜鸟接口
    TRANSFERINOUTNOAPPLYTOSAP(140), //全部入库，调拨完成，出库入库未推送SAP
    TRANSFERINNOAPPLYTOSAP(150); //调拨完成，入库未推送SAP(出库已推送)

    private int status;
    private TransferOrderStatusEume(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
