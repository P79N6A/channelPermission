package com.haier.svc.api.controller;

import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.WaSeparateBillService;
import com.haier.shop.model.*;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WA拆单
 * @author
 * Created by 付振兴 on 2018/7/26.
 *
 */
@Controller
@RequestMapping(value="separate/")
public class WASeparateBillController {
	@Autowired
	private WaSeparateBillService waSeparateBillService;

	private final static org.apache.log4j.Logger logger = LogManager
			.getLogger(WASeparateBillController.class);


	/**
	 * 根据网单id判断是否作废发票
	 * @param
	 * @return
	 */
	@RequestMapping("judeInvoice")
	@ResponseBody
	public void judeInvoice(String orderProductId, HttpServletResponse response) throws IOException {
                String msg = "yes";
				List<Invoices> list_invoices = waSeparateBillService.findInvoices(orderProductId);
				if(list_invoices.size()>0) {
					for (Invoices invoices : list_invoices) {
						if (invoices.getStatusType() == 1 || (invoices.getStatusType() == 4 && invoices.getSuccess() == 0)) {//不能拆单
							msg = "no";
						}
					}
				}
				else{
						msg ="no";
				}
				response.addHeader("Content-type","text/html;charset=utf-8");
				response.getWriter().write(msg);
				response.getWriter().flush();
				response.getWriter().close();
	}
	/**
	 * 根据网单id找到网单和数量
	 * @return
	 */
	   @RequestMapping(value = {"/separateBill"})
	   @ResponseBody
	    public void separateBill(String orderProductId, String num, HttpServletRequest request,HttpServletResponse response) throws IOException {
		   OrderProducts orderProducts = waSeparateBillService.queryGetId(orderProductId);
				int number = Integer.parseInt(num);
           String  msg = "no";
          HttpSession session = request.getSession();
          String userName = Ustring.getString(session.getAttribute("userName"));
		   BigDecimal productAmount  = orderProducts.getProductAmount();//商品总金额
		   BigDecimal price  = orderProducts.getPrice();//单价
		   Long newNum = orderProducts.getNumber(); //数量
				if(number>=1&&number<newNum) {
                    waSeparateBillService.insertLog(userName,orderProducts);
					for (int i = 0; i < number; i++) {
						 waSeparateBillService.insertOrderProducts(orderProducts,userName);//复制一条新网单
						newNum =newNum-1;
						productAmount = productAmount.subtract(price);
					}
				int	result =  waSeparateBillService.updateNum(newNum,productAmount,orderProductId);//更改原网单的数量
					if(result == 1){
						msg = "yes";
					}

				}
		   response.addHeader("Content-type","text/html;charset=utf-8");
		   response.getWriter().write(msg);
		   response.getWriter().flush();
		   response.getWriter().close();
	    }



}
