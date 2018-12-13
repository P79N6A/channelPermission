package com.haier.shop.services;

import com.haier.shop.dao.shopread.OperationAreaReadDao;
import com.haier.shop.dao.shopread.OrderProductsReadDao;
import com.haier.shop.dao.shopwrite.OperationAreaWriteDao;
import com.haier.shop.dao.shopwrite.WwwHpRecordsWriteDao;
import com.haier.shop.model.*;
import com.haier.shop.service.ShopOperationAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ShopOperationAreaServiceImpl implements ShopOperationAreaService {

    @Autowired
    private OperationAreaReadDao operationAreaReadDao;
    @Autowired
    private OperationAreaWriteDao operationAreaWriteDao;
    @Autowired
    private WwwHpRecordsWriteDao wwwHpRecordsWriteDao;
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
    public List<Map<String, Object>> selectAllHPlogs(){
        return operationAreaReadDao.selectAllHPlogs();
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

    public List<OrderProductsVo> queryTmallTiming(String sku, String source,String tid){
        return operationAreaReadDao.queryTmallTiming(sku, source,tid);
    }

    public List<OrderProductsVo> queryTmallTimingoid(String oid, String tid){
        return operationAreaReadDao.queryTmallTimingoid(oid, tid);
    }

    public void update_WwwHpRecordsInfo(List<Map<String, Object>> list){
        operationAreaWriteDao.update_WwwHpRecordsInfo(list);
    }
    public Map<String,Object> select_ThInfo(String cOrderSn){
        return operationAreaReadDao.select_ThInfo(cOrderSn);
    }
    public OrderRepairs selectOrderSn(String cOrderSn){
        return operationAreaReadDao.selectOrderSn(cOrderSn);
    }
    @Override
    public OrderRepairs Checkstate(String cOrderSn) {
        return operationAreaReadDao.Checkstate(cOrderSn);
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
		return operationAreaWriteDao.updateStatus(id,Status);
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
	@Override
	public Map<String,Object> selectOrderProductView(String id) {
		// TODO Auto-generated method stub
		return operationAreaReadDao.selectOrderProductView(id);
	}
    @Override
    public List<Map<String,Object>> selectOrderProductViewTwo(String id) {
        // TODO Auto-generated method stub
        return operationAreaReadDao.selectOrderProductViewTwo(id);
    }

    @Override
    public List<OrderProducts> findByTradeSn(String tradeSn, String sku) {
        return operationAreaReadDao.findByTradeSn(tradeSn,sku);    }

    @Override
    public List<Map<String, String>> getShippingModeAndStockType(String orderSn) {
        return operationAreaReadDao.getShippingModeAndStockType(orderSn);
    }

    @Override
    public List<Map<String, Object>> exportBadCommentsList(List<String> sourceOrderSn) {
        return operationAreaReadDao.exportBadCommentsList(sourceOrderSn);
    }

    @Override
    public OrderProductsVo selectIdAndIsMakeReceiptBycOrderSn(String cOrderSn) {
        return operationAreaReadDao.selectIdAndIsMakeReceiptBycOrderSn(cOrderSn);
    }

    public List<OrderProducts> findOrderProduct(String sourceOrderSn, String sku) {
		// TODO Auto-generated method stub
		return operationAreaReadDao.findOrderProduct(sourceOrderSn,sku);
	}
	@Override
	public OrderProducts findOrderProductByOid(String oid) {
		// TODO Auto-generated method stub
		return operationAreaReadDao.findOrderProductByOid(oid);
	}
	
	public OrderProducts queryGetId(String id) {
		// TODO Auto-generated method stub
		return operationAreaReadDao.queryGetId(id);
	}
	@Override
	public int insertOrderProducts(OrderProducts products) {
		// TODO Auto-generated method stub
		operationAreaWriteDao.insertOrderProducts(products);
		return products.getId();
	}

    @Override
    public List<OrderProducts> queryOrderProductStatus(String orderId) {
        return operationAreaReadDao.queryOrderProductStatus(orderId);
    }
    public String selectOutping(String cOrderSn){
        return operationAreaReadDao.selectOutping(cOrderSn);
    }
    public Map<String,Object> selectlessOrderSn(String cOrderSn){
        return operationAreaReadDao.selectlessOrderSn(cOrderSn);
    }
    public Map<String,Object> queryisStop(Integer id){
        LesQueues lesQueues=operationAreaReadDao.queryisStop(id);
        if (lesQueues==null){
            return new HashMap<>();
        }
        Map<String,Object> map=new HashMap<>();
        map.put("id",lesQueues.getId());
        map.put("isStop",lesQueues.getIsStop());
        return map;
    }
    public Map<String,Object> selecctLockedNumber(String cOrderSn){
        return operationAreaReadDao.selecctLockedNumber(cOrderSn);
    }
    public void updateOPStatus(Integer lockedNumber,String cOrderSn){
        operationAreaWriteDao.updateOPStatus(lockedNumber,cOrderSn);
    }
    public List<Map<String,Object>> selectOPCount(Integer orderId){
        return operationAreaReadDao.selectOPCount(orderId);
    }
    public void updataOrderStatus(Integer id){
        operationAreaWriteDao.updataOrderStatus(id);
    }
    public void updateLesQueuesIsStop(Integer id){
        operationAreaWriteDao.updateLesQueuesIsStop(id);
    }
    public Integer selectStatus(String cOrderSn){
        return operationAreaReadDao.selectStatus(cOrderSn);
    }
    public void updateHPQueuessuccess(Integer id){
        operationAreaWriteDao.updateHPQueuessuccess(id);
    }
    public List<Map<String,Object>> queryOrderProductByTB(String tbSn){
        return operationAreaReadDao.queryOrderProductByTB(tbSn);
    }

    public List<Map<String,Object>> queryRepairsOrderProductByTB(String tbSn, String sku){
        return operationAreaReadDao.queryRepairsOrderProductByTB(tbSn, sku);
    }

    public List<Map<String,Object>> queryRepairsOrderProductByTBSKU(String tbSn, String sku){
        return operationAreaReadDao.queryRepairsOrderProductByTBSKU(tbSn, sku);
    }

    public void updateHPjushouCount(Map<String,Object> map){
        operationAreaWriteDao.updateHPjushouCount(map);
    }
    public void updateOrderRepairsStatus(Map<String,Object> map){
        operationAreaWriteDao.updateOrderRepairsStatus(map);
    }
    public void insertOrderRepairLog(Map<String,Object> map){
        operationAreaWriteDao.insertOrderRepairLog(map);
    }

    @Override
    public List<Regions> getRegion(int id) {
        return  operationAreaReadDao.getRegion(id);
    }

    @Override
    public List<Regions> getRegionB2C(int id) {
        return operationAreaReadDao.getRegionB2C(id);
    }

    @Override
    public Regions getOneRegion(int id) {
        return  operationAreaReadDao.getOneRegion(id);
    }
    @Override
    public Orders getRegionName(String orderSn){
        return  operationAreaReadDao.getRegionName(orderSn);
    }
    @Override
    public void updateRegion(String orderSn,int province,int city,int region,int street,String regionName){
        operationAreaWriteDao.updateRegion(orderSn,province,city,region,street,regionName);
    }
	@Override
	public List<Map<String,Object>> queryNetSheetExportDate(OrderProductsVo vo) {
		return operationAreaReadDao.queryNetSheetExportDate(vo);
	}
    public Map<String,Object> selectData(String cOrderSn){
        return operationAreaReadDao.selectData(cOrderSn);
    }


    @Override
    public Integer Rejectionsinglereset(String id) {
        Integer rid=Integer.valueOf(id);
        return wwwHpRecordsWriteDao.Rejectionsinglereset(rid);
    }
}
