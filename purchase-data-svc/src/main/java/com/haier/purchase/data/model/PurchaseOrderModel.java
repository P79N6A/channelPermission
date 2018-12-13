package com.haier.purchase.data.model;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.dao.purchase.PurchaseItemDao;
import com.haier.purchase.data.dao.purchase.PurchaseOrderDao;
import com.haier.purchase.data.dao.purchase.PurchaseOrderQueue4DailyDao;
import com.haier.purchase.data.dao.purchase.PurchaseOrderQueueDao;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 中间层 用来链接service和dao
 */
@Service
public class PurchaseOrderModel {

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(PurchaseOrderModel.class);

  @Autowired
  private PurchaseItemDao purchaseItemDao;
  @Autowired
  private PurchaseOrderDao purchaseOrderDao;
  @Autowired
  private PurchaseOrderQueueDao purchaseOrderQueueDao;
  @Autowired
  private PurchaseOrderQueue4DailyDao purchaseOrderQueue4DailyDao;

  /**
   * 根据订单来源号，产品Sku，库位编码查询验证是否存在采购订单明细记录
   *
   * @param sourceSn
   * @param sku
   * @param secCode
   * @return
   */
  public Boolean isExistPurchaseItem(String sourceSn, String sku, String secCode) {
    int i = purchaseItemDao.isExistPurchaseItem(sourceSn, sku, secCode);
    return i > 0;
  }

  /**
   * 创建采购单
   *
   * @param purchaseOrder
   * @param purchaseItemList
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  public Boolean createPurchaseOrder(PurchaseOrder purchaseOrder,
      List<PurchaseItem> purchaseItemList) {
    Assert.notNull(purchaseOrderDao, "Property 'purchaseOrderDao' is required.");
    Assert.notNull(purchaseItemDao, "Property 'purchaseItemDao' is required.");

    if (purchaseOrder == null) {
      throw new BusinessException(
          "[puchase_order_model][createPurchaseOrder]:PurchaseOrder对象不能为空");
    }
    if (purchaseItemList == null || purchaseItemList.isEmpty()) {
      throw new BusinessException(
          "[puchase_order_model][createPurchaseOrder]:List<PurchaseItem>列表不能为空！");
    }

    try {
      // 插入到采购订单表purchase_order
      purchaseOrder.setPoNo("");
      Date date = new Date();
      //不知道旧系统的表结构如何，新系统该字段不能为null，但是导入时候又没有指定该字段，所以先默认为空，不然导入报错
      purchaseOrder.setCreateTime(date);
      purchaseOrder.setUpdateTime(date);
      purchaseOrderDao.insert(purchaseOrder);
      // 获取采购订单插入成功后的主键ID值
      int poId = purchaseOrder.getPoId();
      // 修改采购订单号,采购订单号以PO开头，16位
      String poNo = this.getPoNoOrPoItemNoNew(PrefixEnum.PREFIX_PO.getValue());
      purchaseOrderDao.updatePoNo(poId, poNo);

      for (PurchaseItem purchaseItem : purchaseItemList) {
        // 插入到采购订单明细表purchase_item
        purchaseItem.setPoItemNo("");
        purchaseItem.setPoId(poId);
        purchaseItem.setCreateTime(date);
        purchaseItem.setUpdateTime(date);
        purchaseItemDao.insert(purchaseItem);
        // 获取采购明细插入成功后主键ID值
        int poItemId = purchaseItem.getPoItemId();
        // 修改采购订单明细编号,采购网单号以WD开头，16位
        String poItemNo = this.getPoNoOrPoItemNoNew(PrefixEnum.PREFIX_PI.getValue());
        purchaseItemDao.updatePoItemNo(poItemId, poItemNo);

        PurchaseOrderQueue purchaseOrderQueue = new PurchaseOrderQueue();
        purchaseOrderQueue.setLesNo("");
        purchaseOrderQueue.setLesMsg("");
        purchaseOrderQueue.setPoTime(purchaseItem.getPoTime());
        purchaseOrderQueue.setPoId(poId);
        purchaseOrderQueue.setPoItemId(poItemId);
        purchaseOrderQueue.setCreateTime(date);
        purchaseOrderQueue.setUpdateTime(date);
        // 插入采购队列表 purchase_order_queue
        purchaseOrderQueueDao.insert(purchaseOrderQueue);
      }
      // 返回执行成功
      return true;
    } catch (Exception ex) {
      // 记录日志
      log.error("[puchase_order_model][createPurchaseOrder]:创建采购订单发生未知异常:", ex);
      throw ex;
    }
  }

  /**
   * 获取采购订单编号及采购网单编号，生成16位编号（采购订单以PO前缀开头，采购订单明细以WD开头，后面均接yyyyMMdd
   * ，再接ID生成6位字符串，如果不足6位，ID前面用0填充，如果超过6位则截取末尾6位）
   *
   * @param id：采购订单ID或采购订单明细ID
   * @param prefix：采购订单编号前缀或采购订单明细编号前缀
   * @return
   */
  public String getPoNoOrPoItemNo(Integer id, String prefix) {
    // 序列号,不足6位补0,超过6位取末尾6位
    String seq = String.format("%06d", id);
    int len = seq.length();
    if (len > 6) {
      seq = seq.substring(len - 6);
    }
    // 日期yyyyMMdd
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String sDate = sdf.format(new Date());
    return prefix + sDate + seq;
  }

  public List<PurchaseOrderQueueWrapper> findPurchaseOrderQueueByCondition(Date startTime,
      Date endTime, String poItemNo, int status, PagerInfo pager) {

    if (startTime == null) {
      startTime = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd") + " 00:00:00",
          "yyyy-MM-dd HH:mm:ss");
    }
    if (endTime == null) {
      endTime = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd") + " 00:00:00",
          "yyyy-MM-dd HH:mm:ss");
    }
    Date tempDate = DateUtil.add(startTime, Calendar.MONTH, 6);
    if (tempDate.before(endTime)) {
      endTime = tempDate;
    } else {
      endTime = DateUtil.add(endTime, Calendar.DATE, 1);
    }

    List<PurchaseOrderQueueWrapper> returnList = new ArrayList<PurchaseOrderQueueWrapper>();
    if (StringUtil.isEmpty(poItemNo, true)) {
      int rowsCount = purchaseOrderQueueDao.getRowCount(startTime, endTime, status);
      if (pager != null) {
        pager.setRowsCount(rowsCount);
      }
      List<PurchaseOrderQueue> list = purchaseOrderQueueDao.findPurchaseOrderQueueByCondition(startTime, endTime, status, pager);

      for (PurchaseOrderQueue purchaseOrderQueue : list) {
        PurchaseOrderQueueWrapper purchaseOrderQueueWrapper = new PurchaseOrderQueueWrapper();
        int poId = purchaseOrderQueue.getPoId();
        int poItemId = purchaseOrderQueue.getPoItemId();
        PurchaseOrder purchaseOrder = purchaseOrderDao.get(poId);
        PurchaseItem purchaseItem = purchaseItemDao.get(poItemId);
        purchaseOrderQueueWrapper.setPurchaseOrderQueue(purchaseOrderQueue);
        purchaseOrderQueueWrapper.setPurchaseOrder(purchaseOrder);
        purchaseOrderQueueWrapper.setPurchaseItem(purchaseItem);
        returnList.add(purchaseOrderQueueWrapper);
      }
    } else {
      PurchaseItem purchaseItem = purchaseItemDao.getPurchaseItemByPoItemNo(poItemNo);
      if (purchaseItem != null) {
        PurchaseOrderQueueWrapper purchaseOrderQueueWrapper = new PurchaseOrderQueueWrapper();
        int poItemId = purchaseItem.getPoItemId();
        int poId = purchaseItem.getPoId();
        PurchaseOrderQueue purchaseOrderQueue = purchaseOrderQueueDao.getByPoItemId(poItemId);
        PurchaseOrder purchaseOrder = purchaseOrderDao.get(poId);

        purchaseOrderQueueWrapper.setPurchaseItem(purchaseItem);
        purchaseOrderQueueWrapper.setPurchaseOrder(purchaseOrder);
        purchaseOrderQueueWrapper.setPurchaseOrderQueue(purchaseOrderQueue);

        returnList.add(purchaseOrderQueueWrapper);
        pager.setRowsCount(returnList.size());
      }
    }
    return returnList;
  }

  public List<PurchaseOrder> findPurchaseOrder(Date startTime, Date endTime, String poNo,
      int status, PagerInfo pager) {
    Assert.notNull(purchaseOrderDao, "Property 'purchaseOrderDao' is required.");
    if (startTime == null) {
      startTime = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd") + " 00:00:00",
          "yyyy-MM-dd HH:mm:ss");
    }
    if (endTime == null) {
      endTime = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd") + " 00:00:00",
          "yyyy-MM-dd HH:mm:ss");
    }

    Date tempDate = DateUtil.add(startTime, Calendar.MONTH, 6);
    if (tempDate.before(endTime)) {
      endTime = tempDate;
    } else {
      endTime = DateUtil.add(endTime, Calendar.DATE, 1);
    }

    List<PurchaseOrder> list = purchaseOrderDao.findPurchaseOrder(startTime, endTime, poNo,
        status, pager);
    // 统计符合条件的总条数
    int rowsCount = purchaseOrderDao.getRowCnts();
    if (pager != null) {
      pager.setRowsCount(rowsCount);
    }

    if (list == null) {
      return new ArrayList<PurchaseOrder>();
    }
    return list;
  }

  /**
   * 根据采购订单ID查询查询订单详情
   *
   * @param poId:采购订单ID
   * @return
   */
  public PurchaseOrder getPurchaseOrder(int poId) {
    Assert.notNull(purchaseOrderDao, "Property 'purchaseOrderDao' is required.");
    return purchaseOrderDao.get(poId);
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean confirmPurchaseOrder(int poId) {
    PurchaseOrder purchaseOrderNew = new PurchaseOrder();
    purchaseOrderNew.setPoId(poId);
    purchaseOrderNew.setStatus(PurchaseOrderStatus.CONFIRM.getValue());
    purchaseOrderNew.setUpdateTime(new Date());
    try {
      // 根据采购订单ID更新采购订单表记录状态
      boolean result = true;
      int n = purchaseOrderDao.updatePurchaseOrderStatus(purchaseOrderNew);
      if (n <= 0) {
        result = false;
      }
      List<PurchaseItem> itemList = purchaseItemDao.getItemByPOID(poId);
      if (null != itemList && !itemList.isEmpty()) {
        for (PurchaseItem purchaseItem : itemList) {
          if (purchaseItem.getStatus().intValue() == PurchaseItemStatus.PI_NEW.getValue()) {
            boolean resulttmp = this.confirmPurchaseItem(purchaseItem.getPoItemId());
            if (!resulttmp) {
              result = false;
            }
          }
        }
      }
      return result;
    } catch (Exception e) {
      throw e;
    }
  }

  public boolean confirmPurchaseItem(int poItemId) {
    PurchaseItem purchaseItemNew = new PurchaseItem();
    purchaseItemNew.setPoItemId(poItemId);
    purchaseItemNew.setStatus(PurchaseItemStatus.CONFIRM.getValue());
    purchaseItemNew.setUpdateTime(new Date());
    // 根据采购网单ID更新采购订单表记录状态
    int n = purchaseItemDao.updatePurchaseItemStatusbyID(purchaseItemNew);
    return n > 0;
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean cancelPurchaseOrder(int poId) {
    PurchaseOrder purchaseOrderNew = new PurchaseOrder();
    purchaseOrderNew.setPoId(poId);
    purchaseOrderNew.setStatus(PurchaseOrderStatus.CANCEL.getValue());
    purchaseOrderNew.setUpdateTime(new Date());
    try {
      // 根据采购订单ID更新采购订单表记录状态
      boolean result = true;
      int n = purchaseOrderDao.updatePurchaseOrderStatus(purchaseOrderNew);
      if (n <= 0) {
        result = false;
      }
      List<PurchaseItem> itemList = purchaseItemDao.getItemByPOID(poId);
      if (null != itemList && !itemList.isEmpty()) {
        for (PurchaseItem purchaseItem : itemList) {
          if (purchaseItem.getStatus().intValue() == PurchaseItemStatus.PI_NEW.getValue()) {
            boolean resulttmp = this.cancelPurchaseItem(purchaseItem.getPoItemId());
            if (!resulttmp) {
              result = false;
            }
          }
        }
      }
      return result;
    } catch (Exception e) {
      throw e;
    }

  }

  public boolean cancelPurchaseItem(int poItemId) {
    PurchaseItem purchaseItemNew = new PurchaseItem();
    purchaseItemNew.setPoItemId(poItemId);
    purchaseItemNew.setStatus(PurchaseItemStatus.PO_CANCEL.getValue());
    purchaseItemNew.setUpdateTime(new Date());
    // 根据采购网单ID更新采购订单表记录状态
    int n = purchaseItemDao.updatePurchaseItemStatusbyID(purchaseItemNew);
    return n > 0;
  }

  public List<PurchaseItem> findPurchaseItemByPoId(int poId) {
    Assert.notNull(purchaseItemDao, "Property 'purchaseItemDao' is required.");

    // 如果传入参数值为-1,则查询全部状态记录，如果传入参数值为0，则查询状态不为100的记录；如果传入参数值为10,20,100则查询指定状态记录
    List<PurchaseItem> list = purchaseItemDao.findPurchaseItemByPoIdAndStatus(poId, -1);

    if (list == null || list.isEmpty()) {
      return new ArrayList<PurchaseItem>();
    }
    return list;
  }


  @Transactional(rollbackFor = Exception.class)
  public Boolean updatePurchaseProduceDailyOrderAfterInput(String sku, String corderSn,
                                                           Integer inputQty, Date inputTime) {
    Assert.notNull(purchaseItemDao, "Property 'purchaseItemDao' is required.");
    Assert.notNull(purchaseOrderDao, "Property 'purchaseOrderDao' is required.");

    List<PurchaseItem> purchaseItems = purchaseItemDao.getPurchaseItemForPD(corderSn);
    if (purchaseItems.size() <= 0) {
      log.warn("无法更新日日单采购的入库信息，关联的采购单不存在");
      return true;
    }
    PurchaseItem targetItem = null;
    for (PurchaseItem purchaseItem : purchaseItems) {
      if (purchaseItem.getSku().equals(sku)) {
        targetItem = purchaseItem;
        break;
      }
    }
    if (targetItem == null) {
      throw new BusinessException("入库记录的物料关联的日日单采购单不存在");
    }

    Integer status = targetItem.getStatus();
    if (status == PurchaseItemStatus.PI_FINISH.getValue()) {
      return true;
    }

    try {
      int hasInputQty = targetItem.getInputQty() == null ? 0 : targetItem.getInputQty();
      int poQty = targetItem.getPoQty();
      if (poQty >= (hasInputQty + inputQty)) {
        targetItem.setStatus(PurchaseItemStatus.PI_FINISH.getValue());
      }
      targetItem.setInputQty(hasInputQty + inputQty);
      targetItem.setInputTime(inputTime);
      targetItem.setUpdateTime(new Date());
      int effectNum = purchaseItemDao.updatePurchaseItemStatus(targetItem);
      if (effectNum > 0) {
        log.info("[puchase_order_model][updatePurchaseOrderAfterInput]:成功更新采购网单状态。采购网单ID为："
            + targetItem.getPoItemId() + ",采购订单ID为:" + targetItem.getPoId()
            + ", 采购网单号为:" + targetItem.getPoItemNo() + ",采购网单当前状态为:"
            + targetItem.getStatus() + ". (10：新建，20：入库中，100：完成)");
      }
      else {
        log.info(
            "[puchase_order_model][updatePurchaseOrderAfterInput]:不更新采购网单状态。因为采购网单状态已经完成。采购网单ID为："
                + targetItem.getPoItemId() + ",采购订单ID为:" + targetItem.getPoId()
                + ", 采购网单号为:" + targetItem.getPoItemNo() + ",采购网单当前状态为:" + status
                + ". (10：新建，20：入库中，100：完成)");
      }

      boolean isAllDown = true;
      if (purchaseItems.size() > 1) {
        PurchaseItem mainItem = null;
        Date lastInputTime = DateUtil.parse("1970-01-01", "yyyy-MM-dd");
        for (PurchaseItem purchaseItem : purchaseItems) {
          if ("TAO".equals(purchaseItem.getUnit())) {
            mainItem = purchaseItem;
          } else {
            if (purchaseItem.getStatus() != PurchaseItemStatus.PI_FINISH.getValue()) {
              isAllDown = false;
              break;
            }
            if (purchaseItem.getInputTime().after(lastInputTime)) {
              lastInputTime = purchaseItem.getInputTime();
            }
          }
        }
        if (isAllDown) {
          if (mainItem != null) {
            mainItem.setInputQty(mainItem.getPoQty());
            mainItem.setInputTime(lastInputTime);
            mainItem.setUpdateTime(new Date());
            mainItem.setStatus(PurchaseItemStatus.PI_FINISH.getValue());
            int effectNum2 = purchaseItemDao.updatePurchaseItemStatus(mainItem);
            if (effectNum2 > 0) {
              log.info(
                  "[puchase_order_model][updatePurchaseOrderAfterInput]:成功更新采购网单状态。采购网单ID为："
                      + mainItem.getPoItemId() + ",采购订单ID为:" + targetItem.getPoId()
                      + ", 采购网单号为:" + mainItem.getPoItemNo() + ",采购网单当前状态为:"
                      + mainItem.getStatus() + ". (10：新建，20：入库中，100：完成)");
            }
            else {
              log.info(
                  "[puchase_order_model][updatePurchaseOrderAfterInput]:不更新采购网单状态。因为采购网单状态已经完成。采购网单ID为："
                      + mainItem.getPoItemId() + ",采购订单ID为:" + mainItem.getPoId()
                      + ", 采购网单号为:" + mainItem.getPoItemNo() + ",采购网单当前状态为:" + status
                      + ". (10：新建，20：入库中，100：完成)");
            }
          }
        }
      }

      if (isAllDown) {
        PurchaseOrder purchaseOrder = purchaseOrderDao.get(targetItem.getPoId());
        if (purchaseOrder == null) {
          throw new BusinessException("关联的采购订单已不存在，id " + targetItem.getPoId());
        }
        purchaseOrder.setStatus(PurchaseOrderStatus.PO_FINISH.getValue());
        purchaseOrder.setUpdateTime(new Date());
        int n = purchaseOrderDao.updatePurchaseOrderStatus(purchaseOrder);
        if (n > 0) {
          log.info(
                  "[puchase_order_model][updatePurchaseOrderAfterInput]:成功更新采购订单状态。采购订单ID为:"
                          + purchaseOrder.getPoId() + ", 采购订单当前状态为:"
                          + PurchaseOrderStatus.PO_FINISH.getValue() + ". (100:新建,200::完成)");
        }
      }
      return true;
    } catch (BusinessException e) {

      // 记录日志
      log.error(
              "[puchase_order_model][updatePurchaseOrderAfterInput]:采购订单入库后更新采购订单与采购订单明细表状态发生未知异常:",
              e);
      throw e;
    }

  }

  /**
   * 在采购订单完成入库操作后更新订单明细表及订单表相应记录的状态
   *
   * @param purchaseItem
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  public Boolean updatePurchaseOrderAfterInput(PurchaseItem purchaseItem) {
    Assert.notNull(purchaseItemDao, "Property 'purchaseItemDao' is required.");
    Assert.notNull(purchaseOrderDao, "Property 'purchaseOrderDao' is required.");

    if (purchaseItem == null) {
      throw new BusinessException(
              "[puchase_order_model][updatePurchaseOrderAfterInput]:方法参数purchaseItem不能为null！");
    }
    if (purchaseItem.getPoItemId() == null) {
      throw new BusinessException(
              "[puchase_order_model][updatePurchaseOrderAfterInput]:purchaseItem.getPoItemId()不能为null！");
    }

    if (purchaseItem.getStatus() == null) {
      throw new BusinessException(
              "[puchase_order_model][updatePurchaseOrderAfterInput]:purchaseItem.getStatus()不能为null！");
    }

    if (purchaseItem.getInputQty() == null) {
      throw new BusinessException(
              "[puchase_order_model][updatePurchaseOrderAfterInput]:purchaseItem.getInputQty()不能为null！");
    }

    if (purchaseItem.getUpdateTime() == null) {
      purchaseItem.setUpdateTime(new Date());
    }

    try {
      int poItemId = purchaseItem.getPoItemId();
      // 根据采购订单明细ID查询采购订单明细详情
      PurchaseItem purchaseItemCheck = purchaseItemDao.get(poItemId);
      // 采购订单明细对象不为空时，才进行处理更新操作
      if (purchaseItemCheck != null) {
        // 如果当前采购订单明细表记录的状态不为100，即未完成时,才进行更新，否则不更新
        if (PurchaseItemStatus.PI_FINISH.getValue() != purchaseItemCheck.getStatus()) {
          int inputQtyParam = purchaseItem.getInputQty();// Les传回的实际入库数量
          int poQty = purchaseItemCheck.getPoQty();// 采购数量
          int inputQty = purchaseItemCheck.getInputQty();// 实际入库数量
          int validate_inputQty = inputQty + inputQtyParam;
          purchaseItem.setInputQty(validate_inputQty);
          if (poQty == validate_inputQty) {
            // 采购数量等于入库数量的情况(poQty=quantity),更新采购网单状态为100
            purchaseItem.setStatus(PurchaseItemStatus.PI_FINISH.getValue());
          } else if (poQty > validate_inputQty) {
            // 采购数量大于入库数量的情况(poQty>quantity),更新采购网单状态为20
            purchaseItem.setStatus(PurchaseItemStatus.PI_HANDLE.getValue());
          } else {
            // 采购数量小于入库数量的情况（poQty<quantity）不存在,此情况不存在,不进行更新处理
            log.warn(
                    "[puchase_order_model][updatePurchaseOrderAfterInput]:采购数量小于入库数量的情况（poQty<quantity）不存在,此情况不存在");

            return false;
          }
          // 更新采购订单明细表对应记录的状态值
          int m = purchaseItemDao.updatePurchaseItemStatus(purchaseItem);
          if (m > 0) {
            log.info(
                    "[puchase_order_model][updatePurchaseOrderAfterInput]:成功更新采购网单状态。采购网单ID为："
                            + poItemId + ",采购订单ID为:" + purchaseItem.getPoId() + ", 采购网单号为:"
                            + purchaseItem.getPoItemNo() + ",采购网单当前状态为:"
                            + purchaseItem.getStatus() + ". (10：新建，20：入库中，100：完成)");
          }
        } else {
          log.info(
                  "[puchase_order_model][updatePurchaseOrderAfterInput]:不更新采购网单状态。因为采购网单状态已经完成。采购网单ID为："
                          + poItemId + ",采购订单ID为:" + purchaseItem.getPoId() + ", 采购网单号为:"
                          + purchaseItem.getPoItemNo() + ",采购网单当前状态为:"
                          + purchaseItemCheck.getStatus() + ". (10：新建，20：入库中，100：完成)");
        }

        int poId = purchaseItemCheck.getPoId();
        // 根据采购订单ID查询采购订单详情
        PurchaseOrder purchaseOrderCheck = purchaseOrderDao.get(poId);
        // 采购订单对象是否为空，不为空时才进行处理更新操作
        if (purchaseOrderCheck != null) {
          // 如果采购订单表记录的状态不为200时,才进行更新，否则不更新
          if (PurchaseOrderStatus.PO_FINISH.getValue() != purchaseOrderCheck
                  .getStatus()) {
            // 根据采购订单ID与状态查询采购订单明细列表(如果传入参数值为-1,则查询全部状态记录，如果传入参数值为0，则查询状态不为100的记录；如果传入参数值为10,20,100则查询指定状态记录)
            List<PurchaseItem> purchaseItemList = purchaseItemDao
                    .findPurchaseItemByPoIdAndStatus(poId, 0);

            // 如果根据指定poId,状态值不为100查询返回结果为空，则说明订单明细已经全部处理完成，此时需要更新采购订单表记录状态
            if (purchaseItemList == null || purchaseItemList.isEmpty()) {
              // 构建更新字段信息
              PurchaseOrder purchaseOrderNew = new PurchaseOrder();
              purchaseOrderNew.setPoId(poId);
              purchaseOrderNew.setStatus(PurchaseOrderStatus.PO_FINISH.getValue());
              purchaseOrderNew.setUpdateTime(new Date());
              // 根据采购订单ID更新采购订单表记录状态
              int n = purchaseOrderDao.updatePurchaseOrderStatus(purchaseOrderNew);
              if (n > 0) {
                log.info(
                        "[puchase_order_model][updatePurchaseOrderAfterInput]:成功更新采购订单状态。采购订单ID为:"
                                + poId + ", 采购订单当前状态为:"
                                + PurchaseOrderStatus.PO_FINISH.getValue()
                                + ". (100:新建,200::完成)");
              }
            } else {
              log.info(
                      "[puchase_order_model][updatePurchaseOrderAfterInput]:不更新采购订单状态, 因为采购订单明细存在未处理的记录,采购订单ID为:"
                              + poId + ",采购订单号为:" + purchaseOrderCheck.getPoNo() + ", 当前状态为:"
                              + purchaseOrderCheck.getStatus() + "(100:新建,200:完成)");
            }
          } else {
            log.info(
                    "[puchase_order_model][updatePurchaseOrderAfterInput]:不更新采购订单状态，因为当前采购订单记录状态为:"
                            + PurchaseOrderStatus.PO_FINISH.getValue() + ",即已完成，当前采购订单ID为:"
                            + poId + ",当前采购订单状态为" + purchaseOrderCheck.getStatus()
                            + "(100:新建,200:完成)");
          }
        } else {
          log.info("[puchase_order_model][updatePurchaseOrderAfterInput]:不存在采购订单ID为:"
                  + poId + "的采购订单记录");
        }
      } else {
        log.info("[puchase_order_model][updatePurchaseOrderAfterInput]:不存在采购网单ID为:"
                + poItemId + "的采购订单网单记录");
      }

      // 返回执行成功
      return true;
    } catch (Exception ex) {

      // 记录日志
      log.error(
              "[puchase_order_model][updatePurchaseOrderAfterInput]:采购订单入库后更新采购订单与采购订单明细表状态发生未知异常:",
              ex);
      return false;
    }
  }

  /**
   * 根据采购订单明细编号查询记录信息
   *
   * @param poItemNo
   * @return
   */
  public PurchaseItem getPurchaseItemByPoItemNo(String poItemNo) {
    Assert.notNull(purchaseItemDao, "Property 'purchaseItemDao' is required.");
    if (StringUtil.isEmpty(poItemNo, true)) {
      throw new BusinessException(
          "[puchase_order_model][getPurchaseItemByPoItemNo]:方法参数采购订单明细编号poItemNo不能为空");
    }
    return purchaseItemDao.getPurchaseItemByPoItemNo(poItemNo);

  }

  /**
   * 根据采购订单明细ID查询采购订单明细详情
   *
   * @param poItemId:采购订单明细ID
   * @return
   */
  public PurchaseItem getPuchaseItem(int poItemId) {
    Assert.notNull(purchaseItemDao, "Property 'purchaseItemDao' is required.");
    return purchaseItemDao.get(poItemId);
  }

  /**
   * 查询"采购订单队列"表,查询某段时间内没有处理或处理出错的记录
   *
   * @return
   */
  public List<PurchaseOrderQueueWrapper> findPurchaseOrderQueue() {
    Assert.notNull(purchaseOrderQueueDao, "Property 'purchaseOrderQueueDao' is required.");

    List<PurchaseOrderQueue> list = purchaseOrderQueueDao.findPurchaseOrderQueue();

    List<PurchaseOrderQueueWrapper> wrapperList = new ArrayList<PurchaseOrderQueueWrapper>();
    if (list != null) {
      for (PurchaseOrderQueue purchaseOrderQueue : list) {
        int poId = purchaseOrderQueue.getPoId();
        int poItemId = purchaseOrderQueue.getPoItemId();

        PurchaseOrder purchaseOrder = purchaseOrderDao.getConfirmOrder(poId);
        PurchaseItem purchaseItem = purchaseItemDao.getConfirmItem(poItemId);
        // 如果采购订单或者采购网单不是确认状态，不处理
        if (null == purchaseOrder || null == purchaseItem) {
          continue;
        }
        if (purchaseOrder != null && purchaseItem != null) {
          PurchaseOrderQueueWrapper poWrapper = new PurchaseOrderQueueWrapper();
          poWrapper.setPurchaseOrderQueue(purchaseOrderQueue);
          poWrapper.setPurchaseItem(purchaseItem);
          poWrapper.setPurchaseOrder(purchaseOrder);

          wrapperList.add(poWrapper);
        }

      }
    }
    return wrapperList;
  }

  /**
   * 向Les传送入库单数据成功后更新队列表状态并记录Les响应信息
   *
   * @param purchaseOrderQueue
   * @return
   */
  public Boolean updatePurchaseOrderQueueAfterSynced(PurchaseOrderQueue purchaseOrderQueue) {
    Assert.notNull(purchaseOrderQueueDao, "Property 'purchaseOrderQueueDao' is required.");
    if (purchaseOrderQueue == null) {
      throw new BusinessException(
          "[puchase_order_model][updatePurchaseOrderQueueAfterSynced]:方法参数purchaseOrderQueue不能为null！");
    }

    if (purchaseOrderQueue.getId() == null) {
      throw new BusinessException(
          "[puchase_order_model][updatePurchaseOrderQueueAfterSynced]:purchaseOrderQueue.getId()不能为null！");
    }

    if (purchaseOrderQueue.getStatus() == null) {
      throw new BusinessException(
          "[puchase_order_model][updatePurchaseOrderQueueAfterSynced]:purchaseOrderQueue.getStatus()不能为null！");
    }

    if (purchaseOrderQueue.getLesNo() == null) {
      throw new BusinessException(
          "[puchase_order_model][updatePurchaseOrderQueueAfterSynced]:purchaseOrderQueue.getLesNo()不能为null！");
    }

    if (purchaseOrderQueue.getLesMsg() == null) {
      throw new BusinessException(
          "[puchase_order_model][updatePurchaseOrderQueueAfterSynced]:purchaseOrderQueue.getLesMsg()不能为null！");
    }

    if (purchaseOrderQueue.getUpdateTime() == null) {
      purchaseOrderQueue.setUpdateTime(new Date());
    }

    PurchaseOrderQueue purchaseOrderQueueCheck = purchaseOrderQueueDao
        .get(purchaseOrderQueue.getId());
    if (purchaseOrderQueueCheck == null) {
      log.info("[puchase_order_model][updatePurchaseOrderAfterInput]:不存在ID为:"
          + purchaseOrderQueue.getId() + "采购订单队列记录");
      return false;
    } else {
      // 如果当前队列状态不为1,即已处理，则允许更新。-1：出错，0：待处理，1：已处理
      if (purchaseOrderQueueCheck.getStatus() != PurchaseOrderQueueStatus.POQ_FINISH
          .getValue()) {
        int i = purchaseOrderQueueDao.updatePurchaseOrderQueueStatus(purchaseOrderQueue);
        if (i > 0) {
          log.info(
              "[puchase_order_model][updatePurchaseOrderAfterInput]:成功更新采购订单队列表记录状态。当前队列记录ID为:"
                  + purchaseOrderQueue.getId() + ",当前队列记录状态为:"
                  + purchaseOrderQueue.getStatus() + "(-1:出错,0:待处理,1:已处理)");
        }
        return i > 0;
      } else {
        log.info(
            "[puchase_order_model][updatePurchaseOrderAfterInput]:不更新采购订单队列表记录状态。因为当前队列记录状态已经为:"
                + PurchaseOrderQueueStatus.POQ_FINISH.getValue() + "即已完成。当前队列记录ID为:"
                + purchaseOrderQueueCheck.getId() + ",当前队列记录状态为:"
                + purchaseOrderQueueCheck.getStatus() + "(-1:出错,0:待处理,1:已处理)");
        return false;
      }
    }
  }

  /**
   * 更新日日单的生产信息
   *
   * @param queue
   *            日日单队列
   * @param params
   *            生产信息
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  public boolean updatePurchaseProduceDailyFromOms(PurchaseOrderQueue4Daily queue,
      Map<String, String> params) {
    // 队列状态不是待更新生产信息，不处理
    if (!queue.getStatus().equals(PurchaseOrderQueue4Daily.STATUS_PRODUCE)) {
      return true;
    }

    List<PurchaseItem> items = purchaseItemDao.getPurchaseItemForPD(queue.getRefNo());
    if (items.size() <= 0) {
      throw new BusinessException("关联的采购单不存在，无法更新");
    }

    PurchaseOrder purchaseOrder = purchaseOrderDao.get(items.get(0).getPoId());
    if (purchaseOrder == null) {
      throw new BusinessException("关联的采购订单不存在，无法更新");
    }

    String omsStatus = params.get("statusTypeName");// OMS 订单状态
    queue.setOmsStatus(omsStatus);
    String custRevQtyStr = params.get("custRevQty");// 签收数量
    Integer revQty = getInt(custRevQtyStr);

    PurchaseItemStatus status = getCBSStatusByOmsStatus(omsStatus, revQty);// 根据oms订单状态确定日日单采购单状态
    if (status == null) {
      throw new BusinessException(
          "无法识别OMS的状态(" + queue.getRefNo() + ")：" + params.get("statusTypeName"));
    }

    if (status == PurchaseItemStatus.PD_HAS_ENTER_RRS_WAREHOUSE) {// 完成生产信息获取
      queue.setStatus(PurchaseOrderQueue4Daily.STATUS_LOGISTICS);
    }

    if (status == PurchaseItemStatus.PO_CANCEL) {// 联动取消
      queue.setStatus(PurchaseOrderQueue4Daily.STATUS_CANCEL);
    }

    boolean isReviewed = false;
    if (status.getValue() > PurchaseItemStatus.PD_TO_REVIEW.getValue()) {
      isReviewed = true;
    }

    for (PurchaseItem item : items) {
      item.setAbortReason(
          params.get("abortReason") != null && params.get("abortReason").length() > 254
              ? params.get("abortReason").substring(0, 253) : params.get("abortReason"));
      item.setLatestRevDate(getDate(params.get("commitmentRevDate")));
      item.setCustRevQty(getInt(params.get("custRevQty")));
      item.setCustSendDate(getDate(params.get("custSendDate")));
      item.setExpectReceiveDate(getDate(params.get("expectReciveDate")));
      item.setGvsDN(params.get("gvsDn1"));
      item.setGvsOrderCode(params.get("gvsOrderCode"));
      item.setMadeFactoryCode(params.get("madeFactoryCode"));
      item.setMadeFactoryName(params.get("madeFectoryName"));
      item.setPlanCustRevDate(getDate(params.get("planCustRevDate")));
      item.setPlanSendDate(getDate(params.get("planSendDate")));
      item.setPodDate(getDate(params.get("podDate")));
      item.setBrand(params.get("proBand"));
      item.setSeries(params.get("prodSeriesName"));
      // item.setProductType(params.get("productCode"));
      item.setProductType(params.get("productName"));
      item.setProductTypeName(params.get("productLineName"));
      item.setRrsSecCode(params.get("rrsStoreName"));
      item.setSignDate(getDate(params.get("signDate")));
      item.setSourceSn(params.get("soCode"));
      item.setStatus(status.getValue());
      item.setSubmitDate(getDate(params.get("submitDate")));
      item.setPoItemAmount(new BigDecimal(params.get("totalAmount")));
      item.setTradeSendDate(getDate(params.get("tradeSendDate")));
      item.setTransitArrivalDate(getDate(params.get("transitArrivalDate")));
      item.setTransitCode(params.get("transitCode"));
      item.setOrderRevYear(params.get("orderRevYear"));
      item.setOrderRevWeekNumber(params.get("orderRevWeekNumber"));
      item.setCustPoDetailCode(params.get("custPoDetailCode"));
      // 评审通过
      if (isReviewed) {
        if (item.getReviewDate() == null) {
          item.setReviewDate(new Date());
        }
      }
    }

    purchaseOrder.setReceiver(params.get("custName"));
    purchaseOrder.setDeliveryToName(params.get("shipToPartyName"));

    try {
      purchaseOrderQueue4DailyDao.update(queue);
      purchaseOrderDao.update(purchaseOrder);
      for (PurchaseItem item : items) {
        purchaseItemDao.updateFromOMS(item);
      }
    } catch (Exception e) {
      log.error("更新日日单采购单信息from OMS失败：", e);
      throw new BusinessException("更新日日单采购单信息from OMS出现未知错误：" + e.getMessage());
    }
    return true;
  }

  public boolean updatePurchaseOrderQueue4DailyMessage(Integer queueId, String msg) {
    return purchaseOrderQueue4DailyDao.updateMsg(queueId,
        msg != null && msg.length() > 253 ? msg.substring(0, 253) : msg) > 0;
  }

  public List<PurchaseOrderQueue4Daily> getPurchaseOrderQueue4Daily(Integer status) {
    return purchaseOrderQueue4DailyDao.getByStatus(status);
  }

  private PurchaseItemStatus getCBSStatusByOmsStatus(String omsStatus, Integer revQty) {
    if ("到达客户".equals(omsStatus) || "等待工贸发货".equals(omsStatus) || "工贸已发货".equals(omsStatus)
        || "已返单".equals(omsStatus) || "已开发票".equals(omsStatus)) {
      if (revQty > 0) {
        return PurchaseItemStatus.PD_HAS_ENTER_RRS_WAREHOUSE;
      }
      if ("到达客户".equals(omsStatus)) {
        return PurchaseItemStatus.PD_WAIT_INDUSTRY_TRADE_BOOK;
      }
      if ("等待工贸发货".equals(omsStatus)) {
        return PurchaseItemStatus.PD_WAIT_INDUSTRY_TRADE_DELIVERY;
      }
      if ("工贸已发货".equals(omsStatus)) {
        return PurchaseItemStatus.PD_WAIT_INTO_RRS_WAREHOUSE;
      }
      if ("已返单".equals(omsStatus) || "已返单".equals(omsStatus)) {
        return PurchaseItemStatus.PD_HAS_ENTER_RRS_WAREHOUSE;
      }
      return null;
    } else if ("已取消".equals(omsStatus)) {
      return PurchaseItemStatus.PO_CANCEL;
    } else if ("工厂已装车".equals(omsStatus) || "车辆已离园".equals(omsStatus)) {
      return PurchaseItemStatus.PD_BIZ_DEPt_HAS_SHIPPED;
    } else {
      return PurchaseItemStatus.fromText(omsStatus);
    }
  }

  private Integer getInt(String str) {
    if (StringUtil.isEmpty(str)) {
      return 0;
    }

    Double d;
    try {
      d = Double.parseDouble(str);

    } catch (Exception e) {
      d = 0.0;
    }
    return d.intValue();
  }

  private Date getDate(String str) {
    if (str == null) {
      return null;
    }
    return DateUtil.parse(str, "yyyy-MM-dd");
  }

  /**
   * 添加日日单采购单队列
   *
   * @param queue4Daily
   */
  public boolean addToPurchaseOrderQueue4Daily(PurchaseOrderQueue4Daily queue4Daily) {
    return purchaseOrderQueue4DailyDao.insert(queue4Daily) > 0;
  }

  public Map<String, Boolean> checkProduceDailyQueueIsExist(List<String> refNos) {
    Map<String, Boolean> retMap = new HashMap<String, Boolean>();
    for (String refNo : refNos)
      retMap.put(refNo, false);
    List<PurchaseOrderQueue4Daily> queues = purchaseOrderQueue4DailyDao.getQueues(refNos);
    for (PurchaseOrderQueue4Daily queue : queues)
      retMap.put(queue.getRefNo(), true);
    return retMap;
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean createPurchaseProduceDaily(PurchaseOrderQueue4Daily queue,
      PurchaseOrder purchaseOrder,
      List<PurchaseItem> purchaseItems) {
    if (purchaseOrder == null)
      throw new BusinessException(
          "[puchase_order_model][createPurchaseOrder]:PurchaseOrder对象不能为空");
    if (purchaseItems == null || purchaseItems.isEmpty())
      throw new BusinessException(
          "[puchase_order_model][createPurchaseOrder]:List<PurchaseItem>列表不能为空！");

    try {

      int effectNum = purchaseOrderQueue4DailyDao.updateStatus(queue.getId(),
          PurchaseOrderQueue4Daily.STATUS_PRODUCE, PurchaseOrderQueue4Daily.STATUS_NEW);
      if (effectNum < 1) {
        return true;
      }
      // 插入到采购订单表purchase_order
      purchaseOrder.setPoNo("");
      Date date = new Date();
      purchaseOrder.setCreateTime(date);
      purchaseOrder.setUpdateTime(date);
      purchaseOrderDao.insert(purchaseOrder);
      // 获取采购订单插入成功后的主键ID值
      int poId = purchaseOrder.getPoId();
      // 修改采购订单号,采购订单号以PO开头，16位
      String poNo = this.getPoNoOrPoItemNoNew(PrefixEnum.PREFIX_PO.getValue());
      purchaseOrderDao.updatePoNo(poId, poNo);

      for (PurchaseItem purchaseItem : purchaseItems) {
        // 插入到采购订单明细表purchase_item
        purchaseItem.setPoItemNo("");
        purchaseItem.setPoId(poId);
        purchaseItem.setCreateTime(date);
        purchaseItem.setUpdateTime(date);
        purchaseItemDao.insert(purchaseItem);
        // 获取采购明细插入成功后主键ID值
        int poItemId = purchaseItem.getPoItemId();
        // 修改采购订单明细编号,采购网单号以WD开头，16位
        String poItemNo = queue.getRefNo();
        purchaseItemDao.updatePoItemNo(poItemId, poItemNo);

      }
      // 返回执行成功
      return true;
    } catch (Exception ex) {
      // 记录日志
      log.error("[puchase_order_model][createPurchaseOrder]:创建采购订单发生未知异常:", ex);
      throw ex;
    }
  }

  public boolean isPurchaseProduceDailyHasEntryWAWareHouse(String corderSn) {
    List<PurchaseItem> purchaseItems = purchaseItemDao.getPurchaseItemForPD(corderSn);
    if (purchaseItems.size() <= 0)
      return false;
    boolean isPurchaseProduceDailyHasEntryWAWareHouse = true;

    PurchaseItem mainItem = null;
    Date lastInputTime = null;
    for (PurchaseItem item : purchaseItems) {
      if ("TAO".equals(item.getUnit())) {
        mainItem = item;
        continue;
      }
      if (item.getStatus() != PurchaseItemStatus.PI_FINISH.getValue()) {
        isPurchaseProduceDailyHasEntryWAWareHouse = false;
        break;
      } else {
        lastInputTime = item.getInputTime();
      }
    }

    if (isPurchaseProduceDailyHasEntryWAWareHouse && mainItem != null
        && mainItem.getStatus() != PurchaseItemStatus.PI_FINISH.getValue()) {
      mainItem.setInputQty(mainItem.getPoQty());
      mainItem.setInputTime(lastInputTime);
      mainItem.setUpdateTime(new Date());
      mainItem.setStatus(PurchaseItemStatus.PI_FINISH.getValue());
      try {
        purchaseItemDao.updatePurchaseItemStatus(mainItem);
      } catch (Exception ex) {
        log.error("isPurchaseProduceDailyHasEntryWAWareHouse:", ex);
      }
    }

    return isPurchaseProduceDailyHasEntryWAWareHouse;
  }

  public List<PurchaseOrderQueue4Daily> getPurchaseOrderQueue4DailyForUpdateOrders() {
    return purchaseOrderQueue4DailyDao.getByOrderStatus();
  }

  public boolean updateProduceDailyQueueOrderStatus(Integer id, Integer newStatus,
      Integer oldStatus) {
    return purchaseOrderQueue4DailyDao.updateOrderStatus(id, newStatus, oldStatus) >= 1;
  }

  public List<PurchaseOrderQueue4Daily> getPurchaseOrderQueue4DailyForSyncDetailInfo() {
    return purchaseOrderQueue4DailyDao.getBySyncStatus();
  }

  public PurchaseItem getPurchaseProduceDailyItem(String corderSn) {
    List<PurchaseItem> items = purchaseItemDao.getPurchaseItemForPD(corderSn);
    if (items.size() <= 0)
      return null;
    if (items.size() == 1)
      return items.get(0);
    for (PurchaseItem item : items) {
      if ("TAO".equals(item.getUnit()))
        return item;
    }
    return null;
  }

  public boolean updateProduceDailyQueueSyncStatus(Integer id, Integer newStatus,
      Integer oldStatus) {
    return purchaseOrderQueue4DailyDao.updateSyncStatus(id, newStatus, oldStatus) >= 1;
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean updatePurchaseProduceDailyFromLess(PurchaseOrderQueue4Daily queue,
      String lesPing, Date createOrderToLessDate) {
    switch (queue.getStatus()) {
      case PurchaseOrderQueue4Daily.STATUS_NEW:
        throw new BusinessException("采购单未生成，无法更新");
      case PurchaseOrderQueue4Daily.STATUS_PRODUCE:
      case PurchaseOrderQueue4Daily.STATUS_LOGISTICS:
        List<PurchaseItem> items = purchaseItemDao.getPurchaseItemForPD(queue.getRefNo());
        if (items.size() <= 0)
          throw new BusinessException("关联的采购单不存在，无法更新");

        int status;
        for (PurchaseItem item : items) {
          status = item.getStatus();
          if (status != PurchaseItemStatus.PI_FINISH.getValue())
            item.setStatus(
                PurchaseItemStatus.PD_HAS_CREATE_ENTRY_WA_WAREHOUSE_PING.getValue());
          item.setLesPing(lesPing);
        }
        try {
          int n = purchaseOrderQueue4DailyDao.updateStatus(queue.getId(),
              PurchaseOrderQueue4Daily.STATUS_DOWN, queue.getStatus());
          if (n >= 1) {
            for (PurchaseItem item : items) {
              purchaseItemDao.updateLessPing(item.getPoItemId(), item.getLesPing(),
                  createOrderToLessDate);
            }
          } else
            log.info("更新日日单采购单状态为已完成失败，不再更新，refNo " + queue.getRefNo());
        } catch (Exception e) {
          log.error("更新日日单采购单信息from OMS失败：", e);
          throw new BusinessException("更新日日单采购单信息from OMS出现未知错误：" + e.getMessage());
        }
        break;
      case PurchaseOrderQueue4Daily.STATUS_CANCEL:
        throw new BusinessException("采购单已取消，不再更新");
      case PurchaseOrderQueue4Daily.STATUS_DOWN:
        throw new BusinessException("采购单已完成，不再更新");
    }
    return true;
  }

  public List<PurchaseOrderQueue4Daily> getPurchaseOrderQueue4DailyForSyncProdDateFromEDW() {
    return purchaseOrderQueue4DailyDao.getForSyncProdDateFromEDW();
  }

  /**
   * 更新日日单的生产信息
   *
   * @param queue
   *            日日单队列
   * @param prodDate
   *            生产日期
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  public boolean updatePurchaseProduceDailyProdDateFromOms(PurchaseOrderQueue4Daily queue,
      Date prodDate) {

    List<PurchaseItem> items = purchaseItemDao.getPurchaseItemForPD(queue.getRefNo());
    if (items.size() <= 0) {
      purchaseOrderQueue4DailyDao.updateSyncProdDateStatus(queue.getId(),
          PurchaseOrderQueue4Daily.SYNC_PROD_DATE_STATUS_DOWN, queue.getSyncProdDateStatus());
      log.info("关联的采购网单(" + queue.getRefNo() + ")不存在，不再更新生产/下线日期");
      return true;
    }

    for (PurchaseItem item : items) {
      item.setProdDate(prodDate);
    }

    try {
      purchaseOrderQueue4DailyDao.updateSyncProdDateStatus(queue.getId(),
          PurchaseOrderQueue4Daily.SYNC_PROD_DATE_STATUS_DOWN, queue.getSyncProdDateStatus());
      for (PurchaseItem item : items)
        purchaseItemDao.updateProdDate(item);
    } catch (Exception e) {
      log.error("更新日日单采购单生产/下线信息S失败：", e);
      throw new BusinessException("更新日日单采购单生产/下线信息失败：" + e.getMessage());
    }
    return true;
  }

  /**
   * 获取采购订单编号及采购网单编号，生成16位编号（采购订单以PO前缀开头，采购订单明细以WD开头，后面均接yyyyMMdd
   * ，再接ID生成6位字符串，如果不足6位，ID前面用0填充，如果超过6位则截取末尾6位）
   *
   * @param type：来源类型 是订单还是网单
   * @return
   */
  public String getPoNoOrPoItemNoNew(String type) {
    int num = 0;
    // 日期yyyyMMdd
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String sDate = sdf.format(new Date());

    //根据类型时间判断数据库有没有编号
    Integer number = purchaseOrderDao.getNum(type, sDate);
    //如果没有
    if (number == null) {
      //当前数据使用编号1
      num = 1;
      //数据库新增数据 编号为2
      purchaseOrderDao.insertNum(type, sDate);
    } else {
      //如果有 则当前数据使用当前编号
      num = number;
      //编号+1
      purchaseOrderDao.updateNum(type, sDate);
    }

    String seq = String.format("%06d", num);
    int len = seq.length();
    if (len > 6) {
      seq = seq.substring(len - 6);
    }
    return type + sDate + seq;
  }

}
