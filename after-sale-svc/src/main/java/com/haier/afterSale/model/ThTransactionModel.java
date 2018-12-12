package com.haier.afterSale.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvThOrder;
import com.haier.stock.model.InvThTransaction;
import com.haier.stock.service.InvThOrderService;
import com.haier.stock.service.InvThTransactionService;
import com.haier.stock.service.StockInvSectionService;
@Service
public class ThTransactionModel<T> {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(ThTransactionModel.class);

    private DataSourceTransactionManager   transactionManagerStock;
    @Autowired
    private InvThTransactionService            invThTransactionDao;
    @Autowired
    private StockInvSectionService<T>                  invSectionDao;
    @Autowired
    private InvThOrderService                  invThOrderDao;

    /**
     * 京东不良品数据入库数据 - Les 出库，HP入库 
     * @param thTrans 
     * @isTransIn true 为入库 false为出库
     * @return
     */
    public boolean saveTransInOut(List<InvThTransaction> thTrans, boolean isTransIn) {
        //1. 循环列表， 每次都试着添加一条
        List<InvThTransaction> updateTransList = new ArrayList<InvThTransaction>();
        for (InvThTransaction tran : thTrans) {
            String channel = tran.getChannel();
            boolean isSc = InvThTransaction.CHANNEL_SHANGCHENG.equals(channel) || InvThTransaction.CHANNEL_SHANGCHENG_3W.equals(channel);
            tran.setChannel(
                StringUtil.isEmpty(channel) ? InvThTransaction.CHANNEL_JINGDONG : channel);
            if (isSc) {
                tran.setOutStatus(1);
            }
            tran.setChannelOrderSn(tran.getChannelOrderSn());
            tran.setSecCode(tran.getSecCode());
            tran.setCharg(Integer.parseInt(InvSection.W21));
            //2.生成网单号
            InvThTransaction transId = invThTransactionDao.getMaxTransId();
            tran.setCorderSn(getCOrderSn(transId == null ? 0 : transId.getId(), tran.getChannel()));
            //3.设置状态
            if (isTransIn) {
                // 商城
                if (isSc) {
                    if (!StringUtil.isEmpty(tran.getHpLesId()) && tran.getHpLesDate() != null) {
                        tran.setInStatus(InvThTransaction.TRANS_IN_STATUS_HPLES);
                    }
                    //入库 判断 提单号不为空，开单时间不为空时， 标示为向les开单成功，标记in_status=-1
                    if (!StringUtil.isEmpty(tran.getInwhId()) && tran.getInwhDate() != null) {
                    	//3W不良品退仓推送sap不推入库只推出库，判断为3W不良品将入库状态置为推送sap成功状态
                    	if(InvThTransaction.CHANNEL_SHANGCHENG_3W.equals(channel)){
                    		tran.setInStatus(InvThTransaction.TRANS_IN_STATUS_ACCEPTED_BYSAP);
                    	}else{
                    		tran.setInStatus(InvThTransaction.TRANS_IN_STATUS_ARRIV);
                    	}
                    }
                } else {
                    tran.setInStatus(InvThTransaction.TRANS_IN_STATUS_ARRIV);
                }

                tran.setInAddTime(new Date());
            } else {
                //出库
                tran.setOutStatus(InvThTransaction.TRANS_OUT_STATUS_ARRIV);
                tran.setOutAddTime(new Date());
            }

            /**
             * 虚出有storePlace，虚入没有storePlace
             * 虚出没有secCode，虚入有secCode
             */
            //4.计算库位
            String tempSecCode = "";
            if (!StringUtil.isEmpty(tran.getStorePlace())) {//出库信息没有secCode，有storePlace
                //                InvSection section = invSectionDao.getBySecCode(tran.getStorePlace());
                //                tran.setSecCode(section.getWhCode() + InvSection.CHANNEL_CODE_WA);
               if(InvThTransaction.CHANNEL_SHANGCHENG_3W.equals(channel)){
            	   tempSecCode = tran.getStorePlace();
               }else{
            	   InvSection section = invSectionDao.getBySecCode(tran.getStorePlace());
            	   if (section != null) {
            		   tempSecCode = section.getWhCode() + InvSection.CHANNEL_CODE_WA;
            	   } else {
            		   tempSecCode = tran.getStorePlace().substring(0, 2) + InvSection.CHANNEL_CODE_WA;
            	   }
               }
            	
            } else {
                //库位为空无效数据
                if (!isTransIn) {
                    tran.setInStatus(InvThTransaction.TRANS_ERROR);
                    tran.setOutStatus(InvThTransaction.TRANS_ERROR);
                }
            }

            if (!StringUtil.isEmpty(tran.getSecCode())) {//入库信息有secCode，没有storePlace
                tran.setStorePlace(tran.getSecCode());
                //                InvSection section = invSectionDao.getBySecCode(tran.getStorePlace());
                //                tran.setSecCode(section.getWhCode() + InvSection.CHANNEL_CODE_WA);
                if(InvThTransaction.CHANNEL_SHANGCHENG_3W.equals(channel)){
             	   tempSecCode = tran.getSecCode();
                }else{
	                InvSection section = invSectionDao.getBySecCode(tran.getStorePlace());
	                if (section != null) {
	                    tempSecCode = section.getWhCode() + InvSection.CHANNEL_CODE_WA;
	                } else {
	                    tempSecCode = tran.getSecCode().substring(0, 2) + InvSection.CHANNEL_CODE_WA;
	                }
                } 
            }
            if (tempSecCode.equals("QDWA")) {//QDWA已不再使用
                tran.setSecCode("JOWA");
            } else {
                tran.setSecCode(tempSecCode);
            }

            Integer row = invThTransactionDao.insertTrans(tran);
            if (row <= 0) {
                tran.setCorderSn(null);
                updateTransList.add(tran);
            }
        }
        if (updateTransList.size() <= 0) {
            log.info("saveTransInOut:无更新的交易记录");
        }
        /*List<InvThTransaction> errorOrderSnList = new ArrayList<InvThTransaction>();*/
        /*     DefaultTransactionDefinition def = new DefaultTransactionDefinition();
             def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
             TransactionStatus status = transactionManagerStock.getTransaction(def);*/
        try {
            /* for (InvThTransaction uTrans : updateTransList) {
                 Integer rows = invThTransactionDao.updateTrans(uTrans);
                 if (rows <= 0) {
                     InvThTransaction transId = invThTransactionDao.getMaxTransId();
                     uTrans.setCorderSn(getCOrderSn(transId == null ? 0 : transId.getId()));
                     errorOrderSnList.add(uTrans);
                 }
             }*/
            List<InvThTransaction> errorOrderSnList = new ArrayList<InvThTransaction>();
            boolean flag = true;
            while (flag) {
                errorOrderSnList = recurInsert(updateTransList);
                if (errorOrderSnList.size() <= 0) {
                    flag = false;
                } else {
                    updateTransList = new ArrayList<InvThTransaction>();
                    for (InvThTransaction transTh : errorOrderSnList) {
                        Integer row = invThTransactionDao.insertTrans(transTh);
                        if (row <= 0) {
                            updateTransList.add(transTh);
                        }
                    }
                }
            }
            //transactionManagerStock.commit(status);
        } catch (BusinessException e) {
            //transactionManagerStock.rollback(status);
            log.info("saveTransInOut: 生成" + (isTransIn ? "入库" : "出库") + "交易记录时发生未知异常");
        }
        return true;
    }

    private List<InvThTransaction> recurInsert(List<InvThTransaction> updateTransList) {
        List<InvThTransaction> errorOrderSnList = new ArrayList<InvThTransaction>();
        for (InvThTransaction uTrans : updateTransList) {
            //更新不成功就代表机器编码没有
            Integer rows = invThTransactionDao.updateTrans(uTrans);
            if (rows <= 0) {
                //机器编码没有，重新生成网单号，当时没有正确插入，就是网单重复了
                InvThTransaction transId = invThTransactionDao.getMaxTransId();
                uTrans.setCorderSn(
                    getCOrderSn(transId == null ? 0 : transId.getId(), uTrans.getChannel()));
                errorOrderSnList.add(uTrans);
            }
        }
        return errorOrderSnList;
    }

    public List<InvThTransaction> getHpNodesList() {
        List<InvThTransaction> thTransList = this.invThTransactionDao.getHpNodesList();
        return thTransList;
    }

    /**
     * 京东不良品数据入库数据 -- 出库数据
     * @return
     */
    public List<InvThTransaction> getOutDataList(String channel) {
        String tempChannel = StringUtil.isEmpty(channel) ? InvThTransaction.CHANNEL_JINGDONG
            : channel;
        List<Map<String, Object>> maplist = invThTransactionDao.getOutDataList(tempChannel);
        List<InvThTransaction> tnvlist = new ArrayList<InvThTransaction>();
        if (maplist != null && maplist.size() > 0) {
            for (int i = 0; i < maplist.size(); i++) {
                //判断相同gvs下已发送入库数量,待发送出库数量和总数是否都一致，如果不等就不添加到发送列表
                if (maplist.get(i) != null
                    && maplist.get(i).get("total").toString()
                        .equals(maplist.get(i).get("sendin").toString())
                    && maplist.get(i).get("total").toString()
                        .equals(maplist.get(i).get("sendoutwait").toString())) {
                    InvThTransaction invth = new InvThTransaction();
                    invth.setVbelnSo(maplist.get(i).get("vbeln_so").toString());
                    invth.setSku(maplist.get(i).get("sku").toString());
                    invth.setInwhId(maplist.get(i).get("inwh_id").toString());
                    //                    invth.setSoNum(maplist.get(i).get("so_num").toString());//有空指针异常
                    invth.setSoNum(String.valueOf(maplist.get(i).get("so_num")));
                    invth.setQuantity(Integer.parseInt(maplist.get(i).get("quantity").toString()));
                    Object objsec = maplist.get(i).get("sec_code");
                    if (objsec == null || objsec.toString().trim().equals("")
                        || objsec.toString().trim().equals(",")) {
                        continue;
                    }
                    String sec_code = objsec.toString().split(",")[0];//库位取第一个库位编码
                    if (sec_code == null || sec_code.equals("")) {
                        continue;
                    }
                    invth.setSecCode(sec_code);
                    //增加channel_order_sn
                    Object channelOrderSn = maplist.get(i).get("channel_order_sn");
                    if (channelOrderSn != null && !StringUtil.isEmpty(channelOrderSn.toString())) {
                        invth.setChannelOrderSn(channelOrderSn.toString());
                    }
                    //增加ID
                    Object id = maplist.get(i).get("id");
                    invth.setId(Integer.valueOf(id.toString()));
                    tnvlist.add(invth);
                }
            }
        }
        return tnvlist;
    }

    /**
     * 传SAP成功，回写出库状态
     * @param params
     * @return
     */
    public Integer updateOutStatusByVbelnSos(Map<String, Object> params) {
        return invThTransactionDao.updateOutStatusByVbelnSos(params);
    }

    /**
     * 京东不良品入库 -- 单件记录
     * @return
     */
    public List<InvThTransaction> getInDataList(String channel) {
        String tempChannel = StringUtil.isEmpty(channel) ? InvThTransaction.CHANNEL_JINGDONG
            : channel;
        return invThTransactionDao.getInDataList(tempChannel);
    }

    /**
     * 京东不良品入库 -- 套机记录
     * @return
     */
    public List<InvThTransaction> getInDataMachineSetList(String channel) {
        List<InvThTransaction> allInDataList = new ArrayList<InvThTransaction>();
        String tempChannel = StringUtil.isEmpty(channel) ? InvThTransaction.CHANNEL_JINGDONG
            : channel;
        List<InvThTransaction> inDataMachineSetList = invThTransactionDao
            .getInDataMachineSetList(tempChannel);
        for (InvThTransaction entry : inDataMachineSetList) {
            if (entry.getSubReady()) {
                allInDataList.add(entry);
            }
        }
        return allInDataList;
    }

    public Integer updateInStatusByOrderSns(Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return 0;
        }
        Object list = params.get("corder_sn");
        Integer effect = 0;
        if (list != null) {
            @SuppressWarnings("unchecked")
            List<String> orderSnList = (List<String>) list;
            if (orderSnList.size() > 0) {
                effect = invThTransactionDao.updateInStatusByOrderSns(params);
            }
        }
        List<InvThTransaction> thTransList = invThTransactionDao.querySubList(params);
        List<String> keyProductNoList = new ArrayList<String>();
        for (InvThTransaction thTrans : thTransList) {
            keyProductNoList.add(thTrans.getKeyProductNo());
        }
        if (keyProductNoList.size() > 0) {
            params.put("key_product_no", keyProductNoList);
            effect += invThTransactionDao.updateInStatusByKeyProductNo(params);
        }

        return effect;
    }

    /**
     * 查询未完结的退货单
     * @return
     */
    public List<InvThTransaction> getInCompleteThTransList(Integer repairStatus) {
        List<InvThTransaction> thTransList = invThTransactionDao
            .getRepairIncompleteList(repairStatus);
        return thTransList;
    }

    public InvThTransactionService getInvThTransactionDao() {
        return invThTransactionDao;
    }

    /**
     * 更新退货状态
     * @param channelOrderSn
     * @return
     */
    public boolean updateRepairStatus(String channelOrderSn, Integer repairStatus, String message) {
        Integer row = invThTransactionDao.updateRepairStatus(channelOrderSn, repairStatus, message);
        return row > 0;
    }

    /**
     * 更新退货状态
     * @return
     */
    public boolean updateJlRepairStatus(String channelOrderSn, Integer repairStatus, String message,
                                        String channel) {
        Integer row = invThTransactionDao.updateJlRepairStatus(channelOrderSn, repairStatus,
            message, channel);
        return row > 0;
    }

    /**
     * 京东不良品数据入库数据--生成单号
     * @param transId
     * @return
     */
    private static String getCOrderSn(Integer transId, String channel) {
        if (StringUtil.isEmpty(channel)) {
            channel = InvThTransaction.CHANNEL_JINGDONG;
        }
        if (transId == null) {
            transId = 0;
        }
        //序列号,不足6位补0,超过6位取末尾6位
        String seq = String.format("%04d", transId);
        int len = seq.length();
        if (len > 4) {
            seq = seq.substring(len - 4);
        }
        //取4位id 后面补2位随机数 防止并发
    	int random = (int)(Math.random()*100);
    	if(random < 10 ){
    		seq +="0"+random;
    	}else{
    		seq +=random;
    	}
        //日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String sDate = sdf.format(new Date());
        if (InvThTransaction.CHANNEL_JINGDONG.equals(channel)) {
            return "WD" + sDate + seq + "JT";
        } else {
            return "WD" + sDate + seq;
        }
    }

    /**
     * 通过ID更新
     * @param thTrans
     * @return
     */
    public Integer updateById(InvThTransaction thTrans) {
        return invThTransactionDao.updateById(thTrans);
    }

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    public InvThTransaction get(Integer id) {
        return invThTransactionDao.get(id);
    }

    /**
     * 统帅彩电不良品虚入
     * @return
     */
    public List<InvThTransaction> getInvThOrderInDataList(String channel) {
        return invThTransactionDao.getInvThOrderInDataList(channel);
    }

    /**
     * 统帅彩电不良品虚出
     * @return
     */
    public List<InvThTransaction> getInvThOrderOutDataList(String channel) {
        return invThTransactionDao.getInvThOrderOutDataList(channel);
    }

    /**
     * 查询需要关单的统帅彩电不良品数据
     * @return
     */
    public List<InvThTransaction> queryInvThOrderRepairCloseData(Integer repairStatus,
                                                                 String channel) {
        List<InvThTransaction> thTransList = invThTransactionDao
            .queryInvThOrderRepairCloseData(repairStatus, channel);
        return thTransList;
    }

    public boolean saveInvThTransInFormJl(InvThTransaction invThTransaction) {
        if (invThTransactionDao.getInvThTransactionByProductNoAndChannelSn(
            invThTransaction.getProductNo(), invThTransaction.getChannelOrderSn()) != null) {
            throw new BusinessException("已经推送过出库信息，不再处理");
        }
        InvThOrder ito = invThOrderDao.getInvThOrderByChannelSnAndRepairSn(
            invThTransaction.getProductNo(), invThTransaction.getChannelOrderSn());
        if (ito == null) {
            throw new BusinessException("没有推送过PR单和退货单号的信息");
        }
        try {
            invThTransaction.setInAddTime(new Date());
            invThTransaction.setCharg(Integer.parseInt(InvSection.W21));
            //生成网单号
            InvThTransaction transId = invThTransactionDao.getMaxTransId();
            invThTransaction.setCorderSn(
                getCOrderSn(transId == null ? 0 : transId.getId(), invThTransaction.getChannel()));
            invThTransaction.setInStatus(InvThTransaction.TRANS_IN_STATUS_ARRIV);
            invThTransaction.setOutStatus(InvThTransaction.TRANS_OUT_STATUS_ARRIV);

            return invThTransactionDao.insertTrans(invThTransaction) > 0;
        } catch (Exception e) {
            log.error("[saveInvThTransInFormJl]PR单" + invThTransaction.getProductNo() + ",退货单"
                      + invThTransaction.getChannelOrderSn() + "记录入库信息时发生未知异常");
            return false;
        }
    }

    /**
     * 查询统帅彩电不良品PR信息
     * @return
     */
    public List<InvThOrder> queryInvThOrderData() {
        List<InvThOrder> thTransList = invThOrderDao.queryInvThOrderData();
        return thTransList;
    }

    /**
     * 查询统帅彩电不良品PR信息
     * @return
     */
    public boolean updateInvThOrder(InvThOrder invThOrder) {
        if (invThOrder.getMessage() != null && invThOrder.getMessage().length() > 255) {
            invThOrder.setMessage(invThOrder.getMessage().substring(0, 255));
        }
        return invThOrderDao.updateInvThOrder(invThOrder) > 0;
    }

    public StockInvSectionService<T> getInvSectionDao() {
        return invSectionDao;
    }

    public void setInvSectionDao(StockInvSectionService<T> invSectionDao) {
        this.invSectionDao = invSectionDao;
    }

    public void setInvThTransactionDao(InvThTransactionService invThTransactionDao) {
        this.invThTransactionDao = invThTransactionDao;
    }

    public DataSourceTransactionManager getTransactionManagerStock() {
        return transactionManagerStock;
    }

    public void setTransactionManagerStock(DataSourceTransactionManager transactionManagerStock) {
        this.transactionManagerStock = transactionManagerStock;
    }

    public InvThOrderService getInvThOrderDao() {
        return invThOrderDao;
    }

    public void setInvThOrderDao(InvThOrderService invThOrderDao) {
        this.invThOrderDao = invThOrderDao;
    }

}
