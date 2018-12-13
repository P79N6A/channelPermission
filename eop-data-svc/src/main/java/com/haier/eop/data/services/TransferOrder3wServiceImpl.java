package com.haier.eop.data.services;

import com.haier.eop.data.dao.TransferOrderDao;
import com.haier.eop.data.dao.TransferOrderLogDao;
import com.haier.eop.data.model.*;
import com.haier.eop.data.service.TransferOrder3wService;
import com.taobao.pac.sdk.cp.dataobject.response.TAOBAO_QIMEN_TRANSFERORDER_QUERY.Item;
import com.taobao.pac.sdk.cp.dataobject.response.TAOBAO_QIMEN_TRANSFERORDER_QUERY.TransferOrderDetail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransferOrder3wServiceImpl implements TransferOrder3wService {

    private static final Logger logger = LogManager.getLogger(TransferOrder3wServiceImpl.class);

    @Autowired
    private TransferOrderDao transferOrderDao;
    @Autowired
    private TransferOrderLogDao transferOrderLogDao;

    @Override
    public Map<String, Object> getReplOrdersByPage(Map<String, Object> paramMap) throws ParseException {
        //获取开单列表List
        List<TransferOrderDisplayItem> result = transferOrderDao.getOrders(paramMap);
        //获得条数
        int resultcount = transferOrderDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);
        return retMap;
    }

    @Override
    public List<TransferOrder> getByTransferOrderCode(String transferOrderCode) {
        return transferOrderDao.getByTransferOrderCode(transferOrderCode);
    }

    @Override
    public int getTransferOrderLogByName(String name) {
        return transferOrderLogDao.getByName(name);
    }

    @Override
    public void updateTransferLog(String name, String updateouttime, String updateintime, String querytime, String out_msg, String in_msg, String query_msg) {
        transferOrderLogDao.updateTransferLog(name, updateouttime, updateintime, querytime, out_msg, in_msg, query_msg);
    }

    @Override
    public void insertTransferLog(String name, String updateouttime, String updateintime, String querytime, String out_msg, String in_msg, String query_msg) {
        transferOrderLogDao.insertTransferLog(name, updateouttime, updateintime, querytime, out_msg, in_msg, query_msg);
    }

    @Override
    public void handleSyncUpdate(List<TransferOrder> oldTransferOrderList, TransferOrderDetail detail, boolean beSyncJob) {
        Map<String,TransferOrder> oldMap = new HashMap<String, TransferOrder>();
        for (TransferOrder temp : oldTransferOrderList){
            oldMap.put(temp.getScItemCode(), temp);
        }
        boolean beOutStock = false;
        if ("ALLTRANSFEROUT".equals(detail.getOrderStatus()) || "ALLTRANSFEROUTANDPARTIN".equals(detail.getOrderStatus())){
            beOutStock=true;
        }
        Map<String,TransferOrder> updateInMap = new HashMap<String, TransferOrder>();
        Map<String,TransferOrder> updateOutInMap = new HashMap<String, TransferOrder>();
        Map<String,TransferOrder> insertMap = new HashMap<String, TransferOrder>();
        //分析物料
        for (Item item : detail.getItems()){
            TransferOrder old = oldMap.get(item.getScItemCode());
            //出库的调拨单，只处理入库信息
            if (null != old && old.getOrderStatus() == TransferOrderStatusEume.ALLTRANSFEROUT.getStatus()){
                if (beOutStock){
                    continue;
                }
                if (Integer.parseInt(item.getInventoryType()) == GoodsStatusEume.GENUINE.getGoodsStatus()){
                    old.setInCount(item.getInCount());
                }else{
                    old.setRemnantNum(old.getRemnantNum() + item.getInCount());
                }
                if (!updateInMap.keySet().contains(item.getScItemCode())){
                    updateInMap.put(item.getScItemCode(),old);
                }
                //后面扩展同步海尔创建的调拨单使用，查询语句中没有打开
                //海尔申请创建的调拨单，出入库信息都接收
            }else if(null != old && old.getOrderStatus() == TransferOrderStatusEume.ACCEPT.getStatus()) {
                old.setOutCount(old.getOutCount() + item.getOutCount());
                if (!beOutStock) {
                    if (Integer.parseInt(item.getInventoryType()) == GoodsStatusEume.GENUINE.getGoodsStatus()) {
                        old.setInCount(item.getInCount());
                    } else {
                        old.setRemnantNum(old.getRemnantNum() + item.getInCount());
                    }
                }
                if(!updateOutInMap.keySet().contains(item.getScItemCode())){
                    updateOutInMap.put(item.getScItemCode(),old);
                }
                //同步信息产生新货品
            }else if(null == old){
                //haier调拨单仅支持单货品，不会出现新增货品
                if (oldTransferOrderList.get(0).getCreater().equalsIgnoreCase(TransferConstants.TRANSFERORDER_CREATER_HAIER)){
                    continue;
                }
                if (insertMap.keySet().contains(item.getScItemCode())){
                    continue;
                }
                handleNewSku(beOutStock, insertMap, item.getScItemCode(), detail);
                //数据库中调拨单为其他状态
            }else if (!beSyncJob && null != old && old.getOrderStatus()== TransferOrderStatusEume.MANUALINTERVENTION.getStatus()){
                if (updateInMap.keySet().contains(item.getScItemCode())){
                    continue;
                }
                handleAbnormalOrder(updateInMap, old, detail);
                StringBuffer sb = new StringBuffer();
                sb.append("手动同步调拨单信息，调用参数：").append(old.getTransferOrderCode());
                sb.append("_").append(item.getScItemCode()).append("，调拨单状态：").append(old.getOrderCode());
                logger.info(sb.toString());
                continue;
                //数据库中调拨单为其他状态
            }else{
                StringBuffer sb = new StringBuffer();
                sb.append("同步调拨单信息，调拨单不支持更新，调用参数：").append(old.getTransferOrderCode());
                sb.append("_").append(item.getScItemCode()).append("，调拨单状态：").append(old.getOrderCode());
                logger.warn(sb.toString());
                continue;
            }
        }
        //只处理入库
        if (updateInMap.size() != 0){
            for (TransferOrder temp : updateInMap.values()){
                temp.setConfirmInTime(detail.getConfirmInTime());
                if (!needManualIntervention(temp)){
                    temp.setOrderStatus(TransferOrderStatusEume.TRANSFERINOUTNOAPPLYTOSAP.getStatus());
                    temp.setErrorMessage("");
                }else {
                    temp.setOrderStatus(TransferOrderStatusEume.MANUALINTERVENTION.getStatus());  //出库未推送SAP后入库人工介入
                    generateErrMsg(temp);
                }
                transferOrderDao.syncUpdate(temp);
            }
        }
        //处理出库和入库
        if (updateOutInMap.size() != 0){
            for (TransferOrder temp : updateOutInMap.values()){
                temp.setTransferOutOrderCode(detail.getTransferOutOrderCode());
                temp.setTransferInOrderCode(detail.getTransferInOrderCode());
                temp.setConfirmOutTime(detail.getConfirmOutTime());
                if (!beOutStock){
                    temp.setConfirmInTime(detail.getConfirmInTime());
                    if (!needManualIntervention(temp)) {
                        temp.setOrderStatus(TransferOrderStatusEume.TRANSFERINOUTNOAPPLYTOSAP.getStatus());
                    } else {
                        temp.setOrderStatus(TransferOrderStatusEume.MANUALINTERVENTION.getStatus());
                        generateErrMsg(temp);
                    }
                }else {
                    temp.setOrderStatus(TransferOrderStatusEume.ALLTRANSFEROUT.getStatus());
                }
                transferOrderDao.update(temp);
            }
        }
        //处理新货品
        if (insertMap.size() != 0){
            for(TransferOrder transferOrder : insertMap.values()){
                transferOrderDao.insert(transferOrder);
            }
        }
    }

    @Override
    public Integer getManualOrderCount() {
        return transferOrderDao.getManualOrderCount();
    }

    @Override
    public List<String> getManualOrderCodes(Integer pageNo, Integer pageSize) {
        return transferOrderDao.getManualOrderCodes(pageNo, pageSize);
    }

    @Override
    public List<Map<String, Object>> getExportTransferOrderOutList(Map<String, Object> paramMap) {
        return transferOrderDao.getExportTransferOrderOutList(paramMap);
    }

    @Override
    public List<String> getExistTransferOrderCodes(Map<String, Object> orderCodesMap) {
        return transferOrderDao.getExistTransferOrderCodes(orderCodesMap);
    }

    /**
     * 创建调拨单
     * @param detail
     * @return
     */
    @Override
    public String createTransferOrder(TransferOrderDetail detail) {
        List<TransferOrder> resultLst = new ArrayList<TransferOrder>();
        Map<String, TransferOrder> resultMap = new HashMap<String, TransferOrder>();
        int num = detail.getItems().size();
        int itemCount = 0;
        boolean beOutStock = false;
        //判断出入库
        if ("ALLTRANSFEROUT".equals(detail.getOrderStatus()) || "ALLTRANSFEROUTANDPARTIN".equals(detail.getOrderStatus())) {
            beOutStock = true;
        }

        for (int i = 0; i < num; i++) {
            Item item = detail.getItems().get(i);
            TransferOrder transferOrder = null;

            //可能存在无正品全是残品的情况
//      <item>
//        <scItemCode>BB0430097</scItemCode>
//        <inventoryType>1</inventoryType>
//        <planCount>0</planCount>
//        <outCount>0</outCount>
//        <inCount>0</inCount>
//      </item>
//      <item>
//        <scItemCode>BB0430097</scItemCode>
//        <inventoryType>105</inventoryType>
//        <planCount>10</planCount>
//        <outCount>10</outCount>
//        <inCount>8</inCount>
//      </item>
//    </items>
//            if (item.getPlanCount() == 0) {
//                logger.error("通过调拨回传接口创建调拨单时，计划调拨数量为零，调用参数为：" + detail.getTransferOrderCode());
//                return "调拨回传计划调拨数量为零";
//            }

            if (beOutStock) {
                if (Integer.parseInt(item.getInventoryType()) != GoodsStatusEume.GENUINE.getGoodsStatus()) {
                    logger.error("通过调拨回传接口创建调拨单时，出库信息中有非正品，调用参数为：" + detail.getTransferOrderCode());
                    continue;
                }
                transferOrder = new TransferOrder();
                transferOrder.setScItemCode(item.getScItemCode());
                transferOrder.setCount(item.getPlanCount());
                transferOrder.setOutCount(item.getOutCount());
                transferOrder.setOrderStatus(TransferOrderStatusEume.ALLTRANSFEROUT.getStatus());
            } else {
                if (resultMap.containsKey(item.getScItemCode())) {
                    TransferOrder temp = resultMap.get(item.getScItemCode());
                    temp.setCount(temp.getCount() + item.getPlanCount());  //计划调拨数量累加
                    if (Integer.parseInt(item.getInventoryType()) == GoodsStatusEume.GENUINE.getGoodsStatus()) {
                        temp.setInCount(temp.getInCount() + item.getInCount());
                    } else {
                        temp.setRemnantNum(temp.getRemnantNum() + item.getInCount());
                    }
                    temp.setOutCount(temp.getOutCount() + item.getOutCount()); //出库数量和计划调拨数量必相等
                    continue;
                } else {
                    transferOrder = new TransferOrder();
                    transferOrder.setScItemCode(item.getScItemCode());
                    transferOrder.setCount(item.getPlanCount());
                    transferOrder.setOutCount(item.getOutCount());
                    transferOrder.setOrderStatus(TransferOrderStatusEume.TRANSFERINOUTNOAPPLYTOSAP.getStatus());
                    if (Integer.parseInt(item.getInventoryType()) == GoodsStatusEume.GENUINE.getGoodsStatus()) {
                        transferOrder.setInCount(item.getInCount());
                    } else {
                        transferOrder.setRemnantNum(item.getInCount());
                    }
                }
                //set confirmInTime
                if (null == detail.getConfirmInTime() || "".equals(detail.getConfirmInTime())) {
                    transferOrder.setConfirmInTime(detail.getCreateTime());
                } else {
                    transferOrder.setConfirmInTime(detail.getConfirmInTime());
                }
            }
            if (null == detail.getConfirmOutTime() || "".equals(detail.getConfirmOutTime())) {
                transferOrder.setConfirmOutTime(detail.getCreateTime());
            } else {
                transferOrder.setConfirmOutTime(detail.getConfirmOutTime());
            }
            transferOrder.setMessageIdStr("");
            transferOrder.setTransferOrderCode(detail.getTransferOrderCode());
            transferOrder.setOrderCode(detail.getErpOrderCode());
            transferOrder.setTransferOutOrderCode(detail.getTransferOutOrderCode());
            transferOrder.setTransferInOrderCode(detail.getTransferInOrderCode());
            transferOrder.setFromStoreCode(detail.getFromWarehouseCode());
            transferOrder.setToStoreCode(detail.getToWarehouseCode());
            transferOrder.setCreateTime(detail.getCreateTime());
            transferOrder.setOwnerCode(TransferConstants.ownerCode);
            transferOrder.setInventoryType(String.valueOf(GoodsStatusEume.GENUINE.getGoodsStatus())); //正品和残品保存为一条记录
            //增加设置行项目号，推送SAP时使用
            transferOrder.setItemNum(generateItemNum(++itemCount, 1));
            transferOrder.setCreater(TransferConstants.TRANSFERORDER_CREATER_CAINIAO);
            if (beOutStock) {
                resultLst.add(transferOrder);
            } else {
                resultMap.put(item.getScItemCode(), transferOrder);
            }
        }
        //入库需要补充状态和errorMessage
        List<TransferOrder> temp = null;
        if (!beOutStock) {
            temp = new ArrayList<TransferOrder>(resultMap.values());
            resultLst = temp;
            updateStatusAndErrMsg(resultLst);
        }
        transferOrderDao.createTransferOrders(resultLst);
        return "";
    }

    /**
     * 更新调拨单的状态和错误信息，入库时使用，新增的货品不需要修改状态和errorMessage
     *
     * @param transferOrders
     */
    private void updateStatusAndErrMsg(List<TransferOrder> transferOrders) {

        for (TransferOrder transferOrder : transferOrders) {
            //2017-12-4 实际出库数量与实际入库数量一致即可推送SAP（实际入库数量包含良品与不良品），SAP不区分不良品(和张建文确认)
            if (!needManualIntervention(transferOrder)) {
                transferOrder.setOrderStatus(TransferOrderStatusEume.TRANSFERINOUTNOAPPLYTOSAP.getStatus());  //出入库均未推送SAP
            } else {
                transferOrder.setOrderStatus(TransferOrderStatusEume.MANUALINTERVENTION.getStatus());  //出库未推送SAP后入库人工介入
                generateErrMsg(transferOrder);
            }
        }
    }

    /**
     * 处理新增的货品
     * @param beOutStock
     * @param insertMap
     * @param itemCode
     * @param detail
     */
    private void handleNewSku(boolean beOutStock,Map<String,TransferOrder> insertMap, String itemCode, TransferOrderDetail detail){

        TransferOrder newOrder = new TransferOrder();
        newOrder.setFromStoreCode(detail.getFromWarehouseCode());
        newOrder.setToStoreCode(detail.getToWarehouseCode());
        newOrder.setScItemCode(itemCode);
        newOrder.setInventoryType(String.valueOf(GoodsStatusEume.GENUINE.getGoodsStatus()));
        newOrder.setTransferOrderCode(detail.getTransferOrderCode());
        newOrder.setTransferOutOrderCode(detail.getTransferOutOrderCode());
        newOrder.setTransferInOrderCode(detail.getTransferInOrderCode());
        newOrder.setOrderCode(detail.getErpOrderCode());
        newOrder.setConfirmOutTime(detail.getConfirmOutTime());
        newOrder.setCreateTime(detail.getCreateTime());
        newOrder.setOwnerCode(TransferConstants.ownerCode);
        newOrder.setCreater(TransferConstants.TRANSFERORDER_CREATER_CAINIAO);
        for (Item item : detail.getItems()){
            if (item.getScItemCode().equals(itemCode)) {
                newOrder.setCount(newOrder.getCount() + item.getPlanCount());
                newOrder.setOutCount(newOrder.getOutCount() + item.getOutCount());
                if (!beOutStock) {
                    if (Integer.parseInt(item.getInventoryType()) == GoodsStatusEume.GENUINE.getGoodsStatus()){
                        newOrder.setInCount(item.getInCount());
                    }else{
                        newOrder.setRemnantNum(newOrder.getRemnantNum() + item.getInCount());
                    }
                }
            }
        }
        if (beOutStock){
            newOrder.setOrderStatus(TransferOrderStatusEume.ALLTRANSFEROUT.getStatus());
        }else{
            newOrder.setConfirmInTime(detail.getConfirmInTime());
            if (needManualIntervention(newOrder)){
                newOrder.setOrderStatus(TransferOrderStatusEume.MANUALINTERVENTION.getStatus());
                generateErrMsg(newOrder);
            }else {
                newOrder.setOrderStatus(TransferOrderStatusEume.TRANSFERINOUTNOAPPLYTOSAP.getStatus());
            }
        }
        insertMap.put(itemCode,newOrder);
    }

    /**
     * 处理异常调拨单(手动同步时触发)
     * @param updateInMap
     * @param old
     * @param detail
     */
    private void handleAbnormalOrder(Map<String,TransferOrder> updateInMap, TransferOrder old,  TransferOrderDetail detail){

        int incount = 0;
        int remnantNum = 0;
        for (Item item : detail.getItems()){
            if (item.getScItemCode().equals(old.getScItemCode())){
                if (Integer.parseInt(item.getInventoryType()) == GoodsStatusEume.GENUINE.getGoodsStatus()){
                    incount = item.getInCount();
                }else{
                    remnantNum += item.getInCount();
                }
            }
        }
        old.setInCount(incount);
        old.setRemnantNum(remnantNum);
        updateInMap.put(old.getScItemCode(),old);
    }

    /**
     * 判断调拨单是否需要人工介入
     * @param transferOrder
     * @return
     */
    public static boolean needManualIntervention(TransferOrder transferOrder){
        int balance = transferOrder.getOutCount() - transferOrder.getInCount() - transferOrder.getRemnantNum();
        if (balance == 0) {
            return false;
        }
        return true;
    }

    /**
     * 货品数量不对产生错误信息
     * @param transferOrder
     */
    public static void generateErrMsg(TransferOrder transferOrder){
        StringBuffer err = new StringBuffer("调拨实际出库数量与入库数量不相符，无法推送SAP，请人工进行修正，详细信息如下: ");
        err.append("计划调拨数量为：").append(transferOrder.getCount()).append(",");
        err.append("调拨出库数量为：").append(transferOrder.getOutCount()).append(",");
        err.append("正品入库数量为：").append(transferOrder.getInCount()).append(",");
        err.append("残次品入库数量为: ").append(transferOrder.getRemnantNum());
        transferOrder.setErrorMessage(err.toString());
    }

    /**
     * 生成行项目号，推送SAP时使用，结果前两位是物料序号，后两位是该物料调拨次数，
     * @param itemCount 调拨物料计数
     * @param transferCount  调拨次数计数
     * @return
     */
    private String generateItemNum(int itemCount, int transferCount)
    {
        StringBuffer itemNum = new StringBuffer();
        itemNum.append("0");
        if (itemCount > 0 && itemCount < 10)
        {
            itemNum.append(0).append(itemCount);
        }else if (itemCount >= 10 && itemCount <= 99)
        {
            itemNum.append(itemCount);
        }else
        {
            return "";
        }

        if (transferCount > 0 && transferCount < 10)
        {
            itemNum.append(transferCount);
        }else
        {
            return "";
        }
        return itemNum.toString();
    }

}
