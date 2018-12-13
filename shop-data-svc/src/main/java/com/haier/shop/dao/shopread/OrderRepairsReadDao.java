package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderRepairs;
import com.haier.shop.model.OrderRepairsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-04
 **/
@Mapper
public interface OrderRepairsReadDao {

    List<OrderRepairs> getByOrderProductId(@Param("orderProductId") Integer orderProductId);

    String queryRepaiSn(int cOrderId); //查询此网单是否第一次退货
    
    OrderRepairsVo queryWhetherRepaiSn(int cOrderId);//查询是否有退货单

    OrderRepairsVo queryPairsId(String id);

    List<OrderRepairsVo> selectOrderRepairsNOFinish(String id);

    OrderRepairsVo selectPairs(String id); //查询详细信息 推送HP

    String queryIsRejectionSign(String id);//查询用户签收时间来判断是揽收还是拒收

    OrderRepairsVo queryOrderProductId(@Param("id") String id);//查询信息生成出入库单

    OrderRepairsVo qureyRepairs(String id);//查询退货信息

    OrderRepairsVo selectOrederProductId(String id);//查询网单id 和退货主键

    OrderRepairsVo  queryTwoIdentification(String id);//推送二次鉴定的时候 需要查询退货单数据
    
    OrderRepairsVo  queryReturnEdit(String id); //查询网单号退货主键
    
    OrderRepairsVo queryRepairsInvoiceId(String repairSn);//根据退货单号查询数据
    int queryRepairsStats(String id);//查询此退货单是否为非正品需买单
}
