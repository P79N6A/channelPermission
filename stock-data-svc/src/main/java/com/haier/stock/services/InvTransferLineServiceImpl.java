package com.haier.stock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvTransferLineDao;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.service.InvTransferLineService;
@Service
public class InvTransferLineServiceImpl implements InvTransferLineService{
	@Autowired
	private InvTransferLineDao InvTransferLineDao;
	@Override
	public InvTransferLine getTransferLine(String refDHNo) {
		// TODO Auto-generated method stub
		return InvTransferLineDao.getTransferLine(refDHNo);
	}

}
