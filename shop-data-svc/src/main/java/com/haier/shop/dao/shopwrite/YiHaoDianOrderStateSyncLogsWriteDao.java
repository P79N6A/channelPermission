package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.YiHaoDianOrderStateSyncLogs;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2018/1/3
* */
@Mapper
public interface YiHaoDianOrderStateSyncLogsWriteDao {

    /**
     * 创建对象
     * @param log
     * @return
     */
    Integer insert(YiHaoDianOrderStateSyncLogs log);
}
