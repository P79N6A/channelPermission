package com.haier.svc.api.controller.eop;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.StocksynCstorage;
import com.haier.eop.service.EopCenterStockSyncLogService;
import com.haier.eop.service.EopCenterStocksynCstorageService;

/***
 * 库位关系
 * @author 孙玉凯
 *
 */
@Controller
@RequestMapping(value ="eop/StocksynCstorage")
public class StocksynCstorageController {

	@Autowired
	    private EopCenterStocksynCstorageService eopCenterStocksynCstorageService;
	@Autowired
    private EopCenterStockSyncLogService eopCenterStockSyncLogService;

  //菜单配置显示商品关系配置
  @RequestMapping("/StocksynCstorageList")
  public String showCommissionList() {

      return "eop/monitoring/StocksynCstorageList";
  }
//菜单配置显示库存同步日志
  @RequestMapping("/StockSyncLogList")
  public String showCommissionList1() {

      return "eop/monitoring/StockSyncLogList";
  }
  /**
   * 库存同步日志
   *
   * @param page
   * @param rows
   * @return
   */
  @RequestMapping(value = "/logListF", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject LogList(int page, int rows,
		  String sse,String sku,String sourceProductId,String sCode,String sourceStoreCode,
		  String stockSyncResult,String addTimeStart,String addTimeEnd) {
      page = page == 0 ? 1 : page;
      PagerInfo pager = new PagerInfo(rows, page);

      return eopCenterStockSyncLogService.LogListf(pager,   
    		   sse, sku, sourceProductId, sCode, sourceStoreCode,
    		   stockSyncResult, addTimeStart, addTimeEnd);
  }
  /**
   * 商品
   * @param page
   * @param rows
   * @param condition
   * @return
   */
  @RequestMapping(value = "/commission_productListF", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject findOrderProductList(int page, int rows, StocksynCstorage condition) {
      page = page == 0 ? 1 : page;
      PagerInfo pager = new PagerInfo(rows, page);

      return eopCenterStocksynCstorageService.Listf(pager, condition);
  }
  	//添加
		@RequestMapping(value="/addcommission_product", method = RequestMethod.POST)
	    @ResponseBody

	    public int addCommission(StocksynCstorage commission ){


			return eopCenterStocksynCstorageService.insert(commission);
	    }

		//产品政策修改
		@RequestMapping(value="/updatecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int updateCommission(StocksynCstorage commission) {

			return eopCenterStocksynCstorageService.updateByPrimaryKeySelective(commission);
	    }
		//删除
		@RequestMapping(value="/deletecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int deleteCommission(int id) {

			return eopCenterStocksynCstorageService.deleteByPrimaryKey(id);
	    }
		//校验
		@RequestMapping(value="/jiaoyan", method = RequestMethod.POST)
	    @ResponseBody
	    public int jiaoyan(String sCode) {

			return eopCenterStocksynCstorageService.jiaoyan(sCode);
	    }
}