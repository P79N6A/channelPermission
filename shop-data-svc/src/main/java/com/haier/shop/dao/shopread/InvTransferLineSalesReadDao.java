package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvTransferLineSales;
import com.haier.shop.model.InvTransferLineSalesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author xiaojian
 * 2018/08/20
 * Shop库下InvTransferLineSales表
 */
@Mapper
public interface InvTransferLineSalesReadDao {
    /**
     * 分页查询 InvTransferLineSales
     * @param vo
     * @param start
     * @param rows
     * @return
     */
    List<InvTransferLineSales> findListByVo(@Param("vo")InvTransferLineSalesVo vo,@Param("start") int start, @Param("rows")int rows);
    /**
     * 分页查询计算总数据
     * @param vo
     * @return
     */
    int getPagerCountS(@Param("vo")InvTransferLineSalesVo vo);
    /**
     * 导出excel
     * @param vo
     * @return
     */
    List<InvTransferLineSales> exportListByVo(@Param("vo")InvTransferLineSalesVo vo);

    /**
     *  通过调拨网单号码查询
     * @param line_num
     * @return
     */
    InvTransferLineSales getByLine__num(String line_num);
}
