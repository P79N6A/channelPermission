package com.haier.stock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvBaseStoreDao;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvBaseStore;
import com.haier.stock.service.InvBaseStoreService;

@Service
public class InvBaseStoreServiceImpl implements InvBaseStoreService {
    @Autowired
    InvBaseStoreDao invBaseStoreDao;

    @Override
    public BaseStock get(String sku, String code) {
        return invBaseStoreDao.get(sku, code);
    }

    @Override
    public BaseStock getByItemProperty(String sku, String code, String itemProperty) {
        return invBaseStoreDao.getByItemProperty(sku, code, itemProperty);
    }

	@Override
	public InvBaseStore getByItemPropertyForUpdate(String sku,
			String storeCode, String itemProperty) {
		return invBaseStoreDao.getByItemPropertyForUpdate(sku, storeCode, itemProperty);
	}

	@Override
	public InvBaseStore getStore(String sku, String storeCode,
			String itemProperty) {
		return invBaseStoreDao.getStore(sku, storeCode, itemProperty);
	}

	@Override
	public Integer insert(InvBaseStore invBaseStore) {
		return invBaseStoreDao.insert(invBaseStore);
	}

	@Override
	public Integer update(InvBaseStore store) {
		return invBaseStoreDao.update(store);
	}

	@Override
	public Integer releaseFrozenQty(Integer id, Integer releaseQty) {
		return invBaseStoreDao.releaseFrozenQty(id, releaseQty);
	}

	@Override
	public Integer releaseStockQtyAndFrozenQty(Integer id, Integer releaseQty) {
		return invBaseStoreDao.releaseStockQtyAndFrozenQty(id, releaseQty);
	}

	@Override
	public Integer updateStockQty(Integer id, Long storeTs, Integer newStockQty) {
		return invBaseStoreDao.updateStockQty(id, storeTs, newStockQty);
	}

	@Override
	public Integer updateQtyForFrozen(InvBaseStore baseStore) {
		return invBaseStoreDao.updateQtyForFrozen(baseStore);
	}


}
