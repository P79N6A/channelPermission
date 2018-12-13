package com.haier.svc.api.controller.stock.mode;
import com.haier.shop.service.OrderProductsNewService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvStockLockEx;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.InvTransferLineService;
import com.haier.stock.service.MemStockLockService;
import com.haier.stock.service.OrderService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.service.StockService;
@Service
public class MemStockLockModel {
	@Autowired
    private StockService stockService;
	@Autowired
    private MemStockLockService    MemStockLockService;
	@Autowired
    private InvTransferLineService invTransferLineService;
	@Autowired
    private StockCommonService stockCommonService;
	@Autowired
    private OrderService       orderService;
	@Autowired
  private OrderProductsNewService orderProductsNewService;

    public ServiceResult<Boolean> lockStock(String sku, String secCode, int frozenQty, 
                                            String refNo, InventoryBusinessTypes billType,
                                            String optUser) {
        //***************后期要添加校验的话，可以用此代码优化
        /*if (refNo.substring(0,1).equals("WA")){
            OrderProductsNew orderProductsNew = this.orderProductsNewService.getByCOrderSn(refNo.trim());
            if (null == orderProductsNew || null == orderProductsNew.getId()){
                ServiceResult<Boolean> result = new ServiceResult<Boolean>();
                result.setMessage("未查询到对应的网单数据");
                result.setResult(false);
                return result;
            }
        }*/
        //***************后期要添加校验的话，可以用此代码优化
        ServiceResult<Boolean> result = stockService.frozeStockQty(sku, secCode, refNo, frozenQty,
            InventoryBusinessTypes.FROZEN_BY_MEN, optUser);
        return result;
    }

    public ServiceResult<Boolean> cancelLockStock(String sku, String secCode, String billNo,
                                                  int quantity, String id, String optUser) {

        InvStockLockEx lockCond = new InvStockLockEx();
        lockCond.setSku(sku);
        lockCond.setSecCode(secCode);
        lockCond.setRefno(billNo);
        lockCond.setId(id == null ? null : Integer.parseInt(id));
        lockCond.setRealeaseQty(quantity);
        InvStockLockEx lockEx = MemStockLockService.getMemStockLock(lockCond);
        ServiceResult<Boolean> result;
        if (lockEx == null) {
            result = new ServiceResult<Boolean>();
            result.setMessage("冻结记录不存在");
            result.setSuccess(false);
            return result;
        }
        int releaseQty = lockCond.getRealeaseQty();
        result = stockService.releaseFrozenStockQty(sku, billNo, releaseQty,
            InventoryBusinessTypes.RELEASE_BY_MEN, optUser);
        return result;
    }

    public List<InvStockLockEx> queryMemStockLockList(InvStockLockEx stockLock, PagerInfo pager) {
        List<InvStockLockEx> stockLockList = MemStockLockService.queryMemStockLockList(stockLock, pager);
        pager.setRowsCount(MemStockLockService.getRowCnt());
        return stockLockList;
    }

    public List<InvStockLockEx> queryMemStockWDLockList(InvStockLockEx stockLock, PagerInfo pager) {
        List<InvStockLockEx> stockLockList = MemStockLockService.queryMemStockWDLockList(stockLock, pager);
        pager.setRowsCount(MemStockLockService.getRowCnt());
        return stockLockList;
    }

    public InvStockLock getLastestMemStockLock() {
        return MemStockLockService.getLastMemStockLock();
    }

    public String generateRefNo() {
        InvStockLock stockLock = this.getLastestMemStockLock();
        Integer seq = 0;
        if (stockLock == null) {
            seq = 000001;
        } else {
            String refno = stockLock.getRefno();
            seq = Integer.parseInt(refno.substring(8)) + 1;
        }
        //一天不会手动锁定6位数的库存
        int zeroCnt = 6 - seq.toString().length();
        StringBuffer zeroBuf = new StringBuffer();
        for (int i = 0; i < zeroCnt; i++) {
            zeroBuf.append("0");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String datePart = format.format(new Date());
        String wdNo = "SD" + datePart + zeroBuf.toString() + seq;
        return wdNo;
    }

    /**
     * 给占用记录分出占用渠道
     * @return
     */
    public boolean lockStockInChannel() {
        InvStockLockEx stockLock = new InvStockLockEx();
        PagerInfo pager = new PagerInfo(6000, 1);
        List<InvStockLockEx> stockLockList = MemStockLockService.queryMemStockLockList(stockLock, pager);

        for (InvStockLockEx entry : stockLockList) {
            if (!StringUtil.isEmpty(entry.getChannel())) {
                continue;
            }
            boolean isWa = entry.isWa();
            String channel = "WA";
            if (!isWa) {
                channel = entry.getChannelCode();
            } else {
                //SC, TB, DKH
                String refNo = entry.getRefno();
                boolean isTransfer = refNo.endsWith("DH");
                if (isTransfer) {
                    InvTransferLine transferLine = this.invTransferLineService.getTransferLine(refNo);
                    //从哪儿调出，实际上就是冻结的谁的
                    if (transferLine != null) {
                        channel = transferLine.getChannelFrom();
                    }
                } else if (refNo.matches(".+(JS.*)") || refNo.endsWith("J")) {
                    channel = InvStockChannel.JD;
                } else if (refNo.matches(".+(YS.*)") || refNo.endsWith("Y")) {
                    channel = InvStockChannel.YX;
                } else if (refNo.startsWith("WD")) {
                    String temchannel = this.getChannel(refNo);
                    if (temchannel != null && !temchannel.equals(temchannel)) {
                        channel = temchannel;
                    }

                }
            }
            entry.setChannelCode(channel);
            this.MemStockLockService.updateStockLock(entry);
        }
        return false;

    }

    private String getChannel(String cOrderSn) {
        ServiceResult<OrderProductsNew> rs = orderService.getOrderProductByCOrderSn(cOrderSn);
        if (!rs.getSuccess()) {
            return "";
        }
        OrderProductsNew orderProduct = rs.getResult();
        if (orderProduct == null) {
            return "";
        }
        ServiceResult<OrdersNew> rs2 = orderService.getOrderById(orderProduct.getOrderId());
        if (!rs2.getSuccess()) {
            return "";
        }
        OrdersNew order = rs2.getResult();
        if (order == null) {
            return "";
        }
        ServiceResult<String> rs3 = stockCommonService.getChannelCodeByOrderSource(order
            .getSource());
        if (!rs3.getSuccess()) {
            return "";
        }
        return rs3.getResult();
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }


    public StockCommonService getStockCommonService() {
        return stockCommonService;
    }

    public void setStockCommonService(StockCommonService stockCommonService) {
        this.stockCommonService = stockCommonService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public StockService getStockService() {
        return stockService;
    }

}
