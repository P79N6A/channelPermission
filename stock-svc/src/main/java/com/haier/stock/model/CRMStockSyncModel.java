package com.haier.stock.model;


import com.haier.afterSale.service.ItemService;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.ItemBase;
import com.haier.stock.eai.getinvrebateinfofromihs.DetailType;
import com.haier.stock.service.InvStockAgeService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.services.Helper.EAIHandler;
import com.haier.stock.services.StockCommonServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CRMStockSyncModel
 *
 * Created by 钊 on 14-3-14.
 */
@Service
public class CRMStockSyncModel {

  private Logger logger = LoggerFactory.getLogger(CRMStockSyncModel.class);
  @Autowired
  private InvStockAgeService invStockAgeService;
  @Autowired
  private StockCommonServiceImpl stockCommonService;
  @Autowired
  private ItemService itemService;
  @Autowired
  private EAIHandler eaiHandler;


  private static final Pattern pattern = Pattern.compile("[0-9]+(.[0-9]+)?");
  /**
   * Job：更新采购价格到CBS
   */
  public void updatePurchasePriceToCBS() {
    long tsEntering = System.currentTimeMillis();

    //获取库齡列表
    List<InvStockAge> ageList = invStockAgeService.getAll();
    if (ageList == null || ageList.size() == 0) {
      logger.info("更新采购价格到CBS：没有要处理的队列。耗时:" + (System.currentTimeMillis() - tsEntering)
          + "ms。");
      return;
    }
    //获取库位列表
    List<InvSection> sectionList = stockCommonService.getSectionByStatus(
        InvSection.STATUS_IS_VALID).getResult();
    if (sectionList == null || sectionList.size() == 0) {
      logger.error("更新采购价格到CBS：在用的库位队列为空。耗时:" + (System.currentTimeMillis() - tsEntering)
          + "ms。");
      return;
    }
    //循环列表，逐条处理
    int errorCount = 0;
    for (InvStockAge age : ageList) {
      //计算对应的库位对象
      InvSection sec = this.getSectionInList(age.getSecCode(), sectionList);
      if (sec == null) {
        logger.error("更新采购价格到CBS：找不到对应的库位(secCode:" + age.getSecCode() + ")");
        errorCount++;
        continue;
      }

      ServiceResult<ItemBase> itemResult = itemService.getItemBaseBySku(age.getSku());
      BigDecimal unitPrice = new BigDecimal(0);
      if (!itemResult.getSuccess()) {
        logger.error("更新采购价格到CBS：查询item服务出错");
        errorCount++;
        continue;
      }
      ItemBase itemBase = itemResult.getResult();
      boolean isCA = itemBase == null ? false : "CA".equals(itemBase.getDepartment());
      String productType = itemBase == null ? "" : itemBase.getProductType();
      boolean isInner = "T05".equals(productType);
      boolean isOuter = "T06".equals(productType);
      boolean fetchMachinePrice = false;
      //是家用空调
      if (isCA) {
        if (isOuter) {
          unitPrice = new BigDecimal(0);
        } else if (isInner) {
          fetchMachinePrice = true;
        }
      }
      boolean zeroPrice = isCA && isOuter;
      if (!zeroPrice) {
        String mainSku = null;
        //找到套机
        try {
          if (fetchMachinePrice) {
            ServiceResult<List<InvMachineSet>> machineSetResult = stockCommonService
                .getMainSkuBySubSku1(age.getSku());
            if (machineSetResult.getSuccess()) {
              InvMachineSet machineSet = machineSetResult.getResult().size() > 0 ? machineSetResult
                  .getResult().get(0) : null;
              if (machineSet != null) {
                mainSku = machineSet.getMainSku();
              }
            }
          }
        } catch (Exception e) {
          logger.error("获取主物料编码发生异常, sku=：" + age.getSku() + "," + e.getMessage());
          continue;
        }
        String sku = StringUtil.isEmpty(mainSku) ? age.getSku() : mainSku;
        //获取采购单价
        com.haier.stock.eai.getinvrebateinfofromihs.OutputType output = eaiHandler.getPurchasePriceFromCrm(sec.getCorpCode(),
            sec.getCustCode(), StringUtil.isEmpty(mainSku) ? age.getSku() : mainSku,
            sec.getRegionCode());
        boolean verify = output == null || output.getResult() == null
            || !pattern.matcher(output.getResult().getReUnitPrice()).matches();
        if (verify) {
          ServiceResult<ItemBase> itemBasePrice = itemService.getItemBaseBySku(sku);
          if (itemBasePrice.getSuccess()) {
            ItemBase base = itemBasePrice.getResult();
            if (base != null) {
              unitPrice = base.getPrice();
            }
          }
        } else {
          DetailType detailType = output.getResult();
          if (detailType != null) {
            unitPrice = new BigDecimal(detailType.getReUnitPrice());
          }
        }
      }

            /*   if (output == null) {
                   logger.error("更新采购价格到CBS：从CRM获取价格出错(sku:" + age.getSku() + ",corp_code:"
                                + sec.getCorpCode() + ",cust_code:" + sec.getCustCode()
                                + ",region_code:" + sec.getRegionCode() + ")");
                   errorCount++;
                   continue;
               }
               DetailType priceDetail = output.getResult();
               if (priceDetail == null) {
                   logger.warn("更新采购价格到CBS：从CRM获取价格出错(sku:" + age.getSku() + ",corp_code:"
                               + sec.getCorpCode() + ",cust_code:" + sec.getCustCode()
                               + ",region_code:" + sec.getRegionCode() + "), DetailType为Null。");
                   errorCount++;
                   continue;
               }
               if (!StringUtils.isNumeric(priceDetail.getReUnitPrice())) {
                   logger.warn("更新采购价格到CBS：从CRM获取价格出错(sku:" + age.getSku() + ",corp_code:"
                               + sec.getCorpCode() + ",cust_code:" + sec.getCustCode()
                               + ",region_code:" + sec.getRegionCode() + "), ReUnitPrice("
                               + priceDetail.getReUnitPrice() + ")不是数值。");
                   errorCount++;
                   continue;
               }*/
      //未能从CRM获取到对应的价格，则获取该sku其他库位的平均采购价格
      //更新采购单价到库齡表
      try {
        age.setPrice(unitPrice);
        invStockAgeService.updatePriceForStockAge(age);
      } catch (Exception ex) {
        logger.error(
            "更新采购价格到CBS：更新CBS价格时发生未知异常(sku:" + age.getSku() + ",corp_code:"
                + sec.getCorpCode() + ",cust_code:" + sec.getCustCode()
                + ",region_code:" + sec.getRegionCode() + ")", ex);
        errorCount++;
      }
    }
    logger.info("更新采购价格到CBS：总计处理" + ageList.size() + "条(出错:" + errorCount + ")记录。耗时:"
        + (System.currentTimeMillis() - tsEntering) + "ms。");
  }

  /**
   * 在库位列表中计算指定库位编码的库位对象
   *
   * @param secCode 库位编码
   * @param list 库位列表
   */
  private InvSection getSectionInList(String secCode, List<InvSection> list) {
    if (list == null || list.size() == 0) {
      return null;
    }
    for (InvSection section : list) {
      if (section.getSecCode().equalsIgnoreCase(secCode)) {
        return section;
      }
    }
    return null;
  }

}
