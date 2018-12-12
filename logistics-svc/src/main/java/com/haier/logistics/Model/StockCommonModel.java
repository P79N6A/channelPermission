package com.haier.logistics.Model;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvSection;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvWarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockCommonModel<T> {
    @Autowired
    private StockInvSectionService<T> invSectionDao;
	@Autowired
    private StockInvWarehouseService           invWarehouseDao;
    /**
     * 根据库位编码获取库位对象
     *
     * @param secCode
     * @return
     */
    public InvSection getSectionByCode(String secCode) {
        if (StringUtil.isEmpty(secCode)) {
            throw new BusinessException("库位编码不能为空。");
        }
        return invSectionDao.getBySecCode(secCode);
    }
    public String getWhCodeByCenterCode(String centerCode) {
        return invWarehouseDao.getWhCodeByCenterCode(centerCode);
    }
}
