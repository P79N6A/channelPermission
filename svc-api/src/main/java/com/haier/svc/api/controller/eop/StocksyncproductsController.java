package com.haier.svc.api.controller.eop;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.Stocksyncproducts;
import com.haier.eop.service.EopCenterStocksyncproductsService;

/***
 * 商品关系表
 * @author 孙玉凯
 *
 */
@Controller
@RequestMapping(value ="eop/monitoring")
public class StocksyncproductsController {

	@Autowired
	    private EopCenterStocksyncproductsService eopCenterStocksyncproductsService;
	
  //菜单配置显示商品关系配置
  @RequestMapping("/StocksyncproductsList")
  public String showCommissionList() {

      return "eop/monitoring/StocksyncproductsList";
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
  public JSONObject findOrderProductList(int page, int rows, Stocksyncproducts condition) {
      page = page == 0 ? 1 : page;
      PagerInfo pager = new PagerInfo(rows, page);

      return eopCenterStocksyncproductsService.Listf(pager, condition);
  }
  	//添加
		@RequestMapping(value="/addcommission_product", method = RequestMethod.POST)
	    @ResponseBody

	    public int addCommission(Stocksyncproducts commission ){


			return eopCenterStocksyncproductsService.insert(commission);
	    }

		//产品政策修改
		@RequestMapping(value="/updatecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int updateCommission(Stocksyncproducts commission) {

			return eopCenterStocksyncproductsService.updateByPrimaryKey(commission);
	    }
		//删除
		@RequestMapping(value="/deletecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int deleteCommission(int id) {

			return eopCenterStocksyncproductsService.deleteByPrimaryKey(id);
	    }
		//校验
		@RequestMapping(value="/jiaoyan", method = RequestMethod.POST)
	    @ResponseBody
	    public int jiaoyan(String sku) {

			return eopCenterStocksyncproductsService.jiaoyan(sku);
	    }
}