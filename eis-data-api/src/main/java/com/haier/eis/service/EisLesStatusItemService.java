package com.haier.eis.service;



import com.haier.eis.model.LesStatusItem;

import java.util.List;

/*
* 作者:张波
* 2017/12/19
* */
public interface EisLesStatusItemService {
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
