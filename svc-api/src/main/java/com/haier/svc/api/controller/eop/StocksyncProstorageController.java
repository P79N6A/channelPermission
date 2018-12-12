package com.haier.svc.api.controller.eop;



import com.haier.eop.data.model.StocksyncProstorage;
import com.haier.eop.service.EopCenterStocksyncProstorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;

/***
 * 库存同步表
 * @author 孙玉凯
 *
 */
@Controller
@RequestMapping(value ="eop/StocksyncProstorage")
public class StocksyncProstorageController {

	@Autowired
	    private EopCenterStocksyncProstorageService eopCenterStocksyncProstorageService;
	
  //菜单配置显示商品关系配置
  @RequestMapping("/StocksyncProstorageList")
  public String showCommissionList() {

      return "eop/monitoring/StocksyncProstorageList";
  }

  /**
   * 商品关系分页查询
   *
   * @param page
   * @param rows
   * @return
   */
  @RequestMapping(value = "/commission_productListF", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject findOrderProductList(int page, int rows, StocksyncProstorage condition) {
      page = page == 0 ? 1 : page;
      PagerInfo pager = new PagerInfo(rows, page);

      return eopCenterStocksyncProstorageService.Listf(pager, condition);
  }
  	//添加
		@RequestMapping(value="/addcommission_product", method = RequestMethod.POST)
	    @ResponseBody

	    public int addCommission(StocksyncProstorage commission ){


			return eopCenterStocksyncProstorageService.insert(commission);
	    }

		//产品政策修改
		@RequestMapping(value="/updatecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int updateCommission(StocksyncProstorage commission) {

			return eopCenterStocksyncProstorageService.updateByPrimaryKey(commission);
	    }
		//删除
		@RequestMapping(value="/deletecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int deleteCommission(int id) {

			return eopCenterStocksyncProstorageService.deleteByPrimaryKey(id);
	    }
		//校验
		@RequestMapping(value="/jiaoyan", method = RequestMethod.POST)
	    @ResponseBody
	    public JSONObject jiaoyan(String sku ,String sCode,String source) {

			return eopCenterStocksyncProstorageService.jiaoyan(sku,sCode,source);
	    }
}