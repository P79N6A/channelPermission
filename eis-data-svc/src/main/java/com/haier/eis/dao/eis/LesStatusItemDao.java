package com.haier.eis.dao.eis;



import com.haier.eis.model.LesStatusItem;

import java.util.List;

public interface LesStatusItemDao {
    /**
     * 获取待处理的出入库明细
     * @return
     */
    List<LesStatusItem> getNotProcessList();
    /**
     * 处理后，更新相关信息
     * @param item
     * @return
     */
    Integer updateAfterProcessed(LesStatusItem item);
}
