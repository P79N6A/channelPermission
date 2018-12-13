package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.WwwHpRecordsReadDao;
import com.haier.shop.dao.shopwrite.WwwHpRecordsWriteDao;
import com.haier.shop.model.WwwHpRecords;
import com.haier.shop.service.WwwHpRecordsService;

@Service
public class WwwHpRecordsServiceImpl implements WwwHpRecordsService {

    @Autowired
    WwwHpRecordsReadDao wwwHpRecordsReadDao;
    @Autowired
    WwwHpRecordsWriteDao wwwHpRecordsWriteDao;

    @Override
    public WwwHpRecords get(Integer id) {
        return wwwHpRecordsReadDao.get(id);
    }

    @Override
    public int insert(WwwHpRecords wwwHpRecords) {
        return wwwHpRecordsWriteDao.insert(wwwHpRecords);
    }

    @Override
    public int update(WwwHpRecords wwwHpRecords) {
        return wwwHpRecordsWriteDao.update(wwwHpRecords);
    }

    /**
     * 通过vomRepairSn、sku查询"hp拒收推送质检"(orderRepairId) 退货单id
     *
     * @param vomRepairSn
     * @param sku
     * @return
     */
    @Override
    public WwwHpRecords getByVomRepairSnAndSku(String vomRepairSn,
                                               String sku) {
        return wwwHpRecordsReadDao.getByVomRepairSnAndSku(vomRepairSn, sku);
    }

	@Override
	public String queryTHRepairSn(String orderRepairId) {
		// TODO Auto-generated method stub
		return wwwHpRecordsReadDao.queryTHRepairSn(orderRepairId);
	}

}