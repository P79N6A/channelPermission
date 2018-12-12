package com.haier.eis.dao.eis;


import com.haier.eis.model.GQGYSStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27 0027.
 */
@Repository
public interface GQGYSStockDao {
    List<GQGYSStock> getAll();
    List<GQGYSStock> getBySku(String sku);
}
