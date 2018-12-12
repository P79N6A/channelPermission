package com.haier.shop.services;

import com.haier.shop.dao.shopread.OperationAreaReadDao;
import com.haier.shop.dao.shopwrite.OperationAreaWriteDao;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.service.ShopOperationAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ShopOperationAreaServiceImpl implements ShopOperationAreaService {

    @Autowired
    private OperationAreaReadDao operationAreaReadDao;
    @Autowired
    private OperationAreaWriteDao operationAreaWriteDao;
    @Override
    public List<OrderProductsVo> queryOderProductList(OrderProductsVo params){
        return operationAreaReadDao.queryOderProductList(params);
    }
    public int  findOrderProductCNT(OrderProductsVo params){
        return operationAreaReadDao.findOrderProductCNT(params);
    }

    public OrderProductsVo queryOrdeProduct(String cOrderSn){
        return operationAreaReadDao.queryOrdeProduct(cOrderSn);
    }
    public OrderProductsVo queryCommodity(String cOrderSn){
        return operationAreaReadDao.queryCommodity(cOrderSn);
    }
    public Map<String,String> selectMemberInvoicesByorderSn(String orderSn){
        return operationAreaReadDao.selectMemberInvoicesByorderSn(orderSn);
    }
    public List<Map<String,String>> selectPrudevtDatail(String orderSn){
        return operationAreaReadDao.selectPrudevtDatail(orderSn);
    }
    public List<Map<String,String>> selectOrderOperateLogs(String orderSn){
        return operationAreaReadDao.selectOrderOperateLogs(orderSn);
    }
    public List<Map<String,String>> selectCoupon(String orderSn){
        return operationAreaReadDao.selectCoupon(orderSn);
    }
    public Map<String,String> orderNumberSelect(String orderSn){return operationAreaReadDao.orderNumberSelect(orderSn);}
    public List<Map<String,String>> datagrid_WwwHpRecords(Map<String, Object> map){
        return operationAreaReadDao.datagrid_WwwHpRecords(map);
    }
    public List<Map<String,String>> datagrid_WwwHpRecords1(Map<String, Object> map){
        return operationAreaReadDao.datagrid_WwwHpRecords1(map);
    }
    public List<Map<String,Object>> check_cOrderSn_isExist(List<Map<String, Object>> list){
        return operationAreaReadDao.check_cOrderSn_isExist(list);
    }
    public void update_WwwHpRecordsCount(List<String> list){
        operationAreaWriteDao.update_WwwHpRecordsCount(list);
    }
    public void updateFlagBatch(List<String> list){
        operationAreaWriteDao.updateFlagBatch(list);
    }
    public List<Map<String,Object>> select_export_ExcelData(Map<String, Object> map){
        return operationAreaReadDao.select_export_ExcelData(map);
    }
    public void insertHPlogs(Map<String, Object> map){
        operationAreaWriteDao.insertHPlogs(map);
    }
    public List<String> selectHPlogsRowid(String rowid){
        return operationAreaReadDao.selectHPlogsRowid(rowid);
    }
    public void insertWwwHpRecords(Map<String, Object> map){
        operationAreaWriteDao.insertWwwHpRecords(map);
    }

    public String queryTBorderSn(String orderSn){
        return operationAreaReadDao.queryTBorderSn(orderSn);
    }

    public int queryWwwHpTbSn(String tbSn){
        return operationAreaReadDao.queryWwwHpTbSn(tbSn);
    }

    public OrderProductsVo queryTmallTiming(String sku, String source,String tid){
        return operationAreaReadDao.queryTmallTiming(sku, source,tid);
    }

    public void update_WwwHpRecordsInfo(List<Map<String, Object>> list){
        operationAreaWriteDao.update_WwwHpRecordsInfo(list);
    }
    public Map<String,Object> select_ThInfo(String cOrderSn){
        return operationAreaReadDao.select_ThInfo(cOrderSn);
    }
    public String selectOrderSn(String cOrderSn){
        return operationAreaReadDao.selectOrderSn(cOrderSn);
    }
    /*
    * 退换货列表显示
    * */
    public List<Map<String,String>> datagrid_orderForecastGoal(Map<String, Object> map){
        return operationAreaReadDao.datagrid_orderForecastGoal(map);

    }
    public List<Map<String,String>> datagrid_orderForecastGoalcount(Map<String, Object> map){
        return operationAreaReadDao.datagrid_orderForecastGoalcount(map);
    }
    public List<Map<String,Object>> export_ExcelOrderRepairs(Map<String, Object> map){
        return operationAreaReadDao.export_ExcelOrderRepairs(map);
    }
    public Map<String,Object> selectPhoneAndName(String cOrderSn){
        return operationAreaReadDao.selectPhoneAndName(cOrderSn);
    }

    @Override
    public String selectOrderProductId(int th_id) {
        return operationAreaReadDao.selectOrderProductId(th_id);
    }
	@Override
	public int updateStatus(String id,String Status) {
		// TODO Auto-generated method stub
		return operationAreaReadDao.updateStatus(id,Status);
	}
	@Override
	public List<OrderProductsVo> searchcod(OrderProductsVo vo) {
		// TODO Auto-generated method stub
		return operationAreaReadDao.searchcod(vo);
	}
	@Override
	public int findCodConfirmCNT(OrderProductsVo vo) {
		// TODO Auto-generated method stub
		return operationAreaReadDao.findCodConfirmCNT(vo);
	}
}
