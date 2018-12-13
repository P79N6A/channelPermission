package com.haier.svc.api.controller.eop;


import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.ExternalSaleSettings;
import com.haier.shop.service.CenterExternalSalesettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 *营销套装配置
 * @author hanhaoyang
 *
 */
@Controller
@RequestMapping(value ="eop/externalSalesettings")
public class ExternalSalesettingsController {

	@Autowired
	private CenterExternalSalesettingsService centerSalesettingsService;

  //菜单配置显示商品关系配置
  @RequestMapping("/externalSalesettingsList")
  public String showCommissionList() {

      return "eop/monitoring/externalSalesettingsList";
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
  public JSONObject findOrderProductList(int page, int rows, ExternalSaleSettings condition) {
      page = page == 0 ? 1 : page;
      PagerInfo pager = new PagerInfo(rows, page);

      return centerSalesettingsService.Listf(pager, condition);
  }
  	//添加
		@RequestMapping(value="/addcommission_product", method = RequestMethod.POST)
	    @ResponseBody

	    public String addCommission(ExternalSaleSettings commission ){


			return centerSalesettingsService.insert(commission);
	    }

		//产品政策修改
		@RequestMapping(value="/updatecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public String updateCommission(ExternalSaleSettings commission) {

			return centerSalesettingsService.updateByPrimaryKey(commission);
	    }
		//删除
		@RequestMapping(value="/deletecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int deleteCommission(int id) {

			return centerSalesettingsService.deleteByPrimaryKey(id);
	    }
}