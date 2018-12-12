package com.haier.afterSale.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairLogs;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.service.ShopOrderRepairLogsService;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;

/**
 * 	售后定时任务
 * @author wukunyang
 *
 */
@Service
public class CustomeAfterServiceImpl {
	private static Logger log = LoggerFactory.getLogger(CustomeAfterServiceImpl.class);
	@Autowired
	private ShopOrderrepairHPrecordsService shopOrderrepairHPrecordsService;
	@Autowired
	private OperationAreaServiceImpl operationAreaServiceImpl;
	@Autowired
	private ShopOrderRepairLogsService shopOrderRepairLogsService;//退货日志
	/**
	 * 查询需要生成出入库记录的信息 定时生成
	 */
	public void GenerateOutOfStorage(){
		//查询需要生成出入库记录的信息
		List<OrderrepairHPrecords> orderrepairHPrecordsList=	shopOrderrepairHPrecordsService.queryGenerateOutOfStorage();
		//循环判断
		for(OrderrepairHPrecords precords :orderrepairHPrecordsList){
			//根据退货单id查询退货信息
			OrderRepairsVo vo=	operationAreaServiceImpl.queryOrderProductId(precords.getOrderRepairId().toString());
			
			OrderRepairLESRecords cords= new OrderRepairLESRecords();
			cords.setOrderProductId(vo.getOrderProductId());//网单id
			cords.setOrderRepairId(vo.getId());//退货单主键

			//判断是否是1次鉴定回传的信息
			if("1".equals(precords.getCheckType())){
				cords.setRecordSn(vo.getcOrderSnId()+"CX2");//入库单号
				cords.setOperate(2);//操作，1=出库；2=入库
				cords.setStorageType(22);//批次，22；21；10
				int s = operationAreaServiceImpl.insert(cords); //插入出库单
				//记录退货操作流程 日志
				operationAreaServiceImpl.ProcessLog(precords.getOrderRepairId(), "生成入库单", "生成22入库单");
			}else if("2".equals(precords.getCheckType())){ //判断是否是二次鉴定回传
				//判断是否有箱可换  有箱的话 CBS给VOM发送命令 生成22出库单  和生成 10入库单号 然后 VOM再给CBS返回入库结果 
				if("1".equals(precords.getPackResult())){
					cords.setRecordSn(vo.getcOrderSnId()+"CX1");//出库单号
					cords.setOperate(1);//操作，1=出库；2=入库
					cords.setStorageType(22);//批次，22；21；10
					int s = operationAreaServiceImpl.insert(cords); //插入出库单
					//记录退货操作流程 日志
					operationAreaServiceImpl.ProcessLog(precords.getOrderRepairId(), "生成出库单", "生成22出库单："+cords.getRecordSn());
					log.info("插入22出库单");
					if(s>0){
						cords.setRecordSn(vo.getcOrderSnId()+"CX2");//入库单号
						cords.setOperate(2);//操作，1=出库；2=入库
						cords.setStorageType(10);//批次，22；21；10
						operationAreaServiceImpl.insert(cords); //插入 入库单
						operationAreaServiceImpl.ProcessLog(precords.getOrderRepairId(), "生成入库单", "生成10入库单："+cords.getRecordSn());
						log.info("插入10入库单");
					}
				}

				
			}
			//更改出入库单生成状态
			shopOrderrepairHPrecordsService.updataOutOfStorage("1", precords.getId().toString());
		}
	}


}
