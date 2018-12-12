package com.haier.shop.dao.shopread;



import com.haier.shop.model.OrderTmallReturnLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 	吴坤洋～ 2017-11-23
 * 天猫回传退货信息
 * @author wukunyang
 *
 */
@Mapper
public interface OrderTmallReturnLogsReadDao {

    List<OrderTmallReturnLogs> selectByPrimaryKey();
}