
package com.haier.svc.api.webService.WwwHpRecords.ServiceImpl;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.haier.afterSale.model.Ustring;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrederVOMReturnLogs;
import com.haier.svc.api.controller.util.SpringContextUtil;
import com.haier.svc.api.webService.WwwHpRecords.Service.VOMReturn;



/**
 * VOM 回传入库信息
 * 吴坤洋 2017-11-20
 * @author wukunyang
 *
 */

@WebService
public class VOMReturnImpl implements VOMReturn{
//	@Override
//	public String VOMReturn(OrederVOMReturnLogs vomReturnLogs) throws MalformedURLException, ParseException {
//		HPReturn_transfer hpRetun = (HPReturn_transfer) SpringContextUtil.getBean("hpReturnTransfer");
//		List<OrderRepairsVo> listVo = new ArrayList<>();
////		String id=hpRetun.insertAnalyLogs(vomReturnLogs);//处理VOM 返回来的数据
//		//判断是否有返回的id 如果不为空就说明处理vom数据成功 否则就是失败
//		if(Ustring.isNotEmpty(id)){
//			//如果VOM返回成功 则发起二次鉴定
//			OrderRepairsVo vo = new OrderRepairsVo();
//			vo.setMenuflag("2");
//			vo.setId(Integer.parseInt(id));
//			listVo.add(vo);
//			hpRetun.ModifyPushHP(listVo);//调用发起二次鉴定方法
//		}else {
//			return "失败";
//		}
//		return "调用成功！";
//	}
	
	


}

