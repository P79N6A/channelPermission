package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.Outstoragelist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15.
 */
@Mapper
public interface OutstoragelistDao {
    /**
     * 查询产品出库信息
     * @param param
     * @return
     */
    List<Outstoragelist> findOutstoragelist(Outstoragelist param);

    int insert(Outstoragelist param);
    int update(Outstoragelist param);
    List<Outstoragelist> findallbyyearmonth(Outstoragelist param);
    List<Outstoragelist> findreout(Outstoragelist param);

    List<Outstoragelist> importout(Outstoragelist param);
}
