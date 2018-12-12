package com.haier.svc.api.webService.WwwHpRecords.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.haier.shop.model.OrderhpRejectionLogs;
/**
 * HP 返回不良信息
 * 吴坤洋 2017-11-21
 * @author wukunyang
 *
 */
@WebService
public interface HPReturnUnhealthy {
	@WebMethod
	String HPReturnRejects(OrderhpRejectionLogs bean);//HP返回鉴定结果方法
}
