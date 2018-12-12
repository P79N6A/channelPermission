package com.haier.distribute.data.dao.distribute;/**
 * Created by Administrator on 2017/11/7 0007.
 */

import com.haier.distribute.data.model.SysDictionary;
import org.apache.ibatis.annotations.Param;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 10:56
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface SysDictionaryDao extends BaseDao<SysDictionary> {
/***
 * mapp找不到此方法，方法生態系統菜單
 * @param begin
 * @return
 */
    String getSysDictionaryNo(@Param("begin") String begin);

    int updateSelectiveBySysDictionaryNo(@Param("entity") SysDictionary entity);

    SysDictionary selectOrderCancelFlag();

}
