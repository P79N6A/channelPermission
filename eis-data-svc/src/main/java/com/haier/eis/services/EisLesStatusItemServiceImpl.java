package com.haier.eis.services;

import com.haier.eis.dao.eis.LesStatusItemDao;
import com.haier.eis.model.LesStatusItem;
import com.haier.eis.service.EisLesStatusItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class EisLesStatusItemServiceImpl implements EisLesStatusItemService ,Serializable{
    @Autowired
    private LesStatusItemDao lesStatusItemDao;
    /**
     * 获取待处理的出入库明细
     * @return
     */
    @Override
    public List<LesStatusItem> getNotProcessList(){
        return lesStatusItemDao.getNotProcessList();
    }
    /**
     * 处理后，更新相关信息
     * @param item
     * @return
     */
    @Override
    public Integer updateAfterProcessed(LesStatusItem item){
        return lesStatusItemDao.updateAfterProcessed(item);
    }
}
