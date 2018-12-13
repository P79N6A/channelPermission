package com.haier.svc.api.controller.eop;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.Salesettings;
import com.haier.eop.service.EopCenterSalesettingsService;
import com.haier.eop.service.EopCenterStockSyncLogService;

/***
 *营销套装配置
 * @author 孙玉凯
 *
 */
@Controller
@RequestMapping(value ="eop/Salesettings")
public class SalesettingsServiceController {

	@Autowired
	    private EopCenterSalesettingsService eopCenterSalesettingsService;

  //菜单配置显示商品关系配置
  @RequestMapping("/SalesettingsList")
  public String showCommissionList() {

      return "eop/monitoring/SalesettingsList";
  }

  
  /**
   //TODO 列表查询按钮 商品
   * @param page
   * @param rows
   * @param condition
   * @return
   */
  @RequestMapping(value = "/commission_productListF", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject findOrderProductList(int page, int rows, Salesettings condition) {
      page = page == 0 ? 1 : page;
      PagerInfo pager = new PagerInfo(rows, page);

      return eopCenterSalesettingsService.Listf(pager, condition);
  }
  	//添加
		@RequestMapping(value="/addcommission_product", method = RequestMethod.POST)
	    @ResponseBody

	    public String addCommission(Salesettings commission ){


			return eopCenterSalesettingsService.insert(commission);
	    }

		//产品政策修改
		@RequestMapping(value="/updatecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public String updateCommission(Salesettings commission) {

			return eopCenterSalesettingsService.updateByPrimaryKey(commission);
	    }
		//删除
		@RequestMapping(value="/deletecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int deleteCommission(int id) {

			return eopCenterSalesettingsService.deleteByPrimaryKey(id);
	    }
}