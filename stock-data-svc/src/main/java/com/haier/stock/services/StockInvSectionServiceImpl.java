package com.haier.stock.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvSectionDao;
import com.haier.stock.model.InvSection;
import com.haier.stock.service.StockInvSectionService;
@Service
public class StockInvSectionServiceImpl implements StockInvSectionService{
	@Autowired
	 private InvSectionDao invSectionDao;
	@Override
	public int deleteByPrimaryKey(String secCode) {
		// TODO Auto-generated method stub
		return invSectionDao.deleteByPrimaryKey(secCode);
	}

	@Override
	public int insert(InvSection record) {
		// TODO Auto-generated method stub
		return invSectionDao.insert(record);
	}

	@Override
	public int insertSelective(InvSection record) {
		// TODO Auto-generated method stub
		return invSectionDao.insertSelective(record);
	}

	@Override
	public InvSection selectByPrimaryKey(String secCode) {
		// TODO Auto-generated method stub
		return invSectionDao.selectByPrimaryKey(secCode);
	}

	@Override
	public int updateByPrimaryKeySelective(InvSection record) {
		// TODO Auto-generated method stub
		return invSectionDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(InvSection record) {
		// TODO Auto-generated method stub
		return invSectionDao.updateByPrimaryKey(record);
	}

	@Override
	public long checkSame(String secCode) {
		// TODO Auto-generated method stub
		return invSectionDao.checkSame(secCode);
	}

	@Override
	public List<InvSection> exportSection(InvSection entity) {
		// TODO Auto-generated method stub
		return invSectionDao.exportSection(entity);
	}

	@Override
	public InvSection getBySecCode(String secCode) {
		// TODO Auto-generated method stub
		return invSectionDao.getBySecCode(secCode);
	}
	 /**
     * 通过渠道编码获取在用的库存列表
     * @param whCode
     * @param channelCode
     * @return
     */
	@Override
	public List<InvSection> getByChannelCode(String whCode, String channelCode) {
		// TODO Auto-generated method stub
		return invSectionDao.getByChannelCode(whCode, channelCode);
	}
	 /**
     * 根据状态获取库位列表
     * @param status 状态，可为空
     * @return
     */
	@Override
	public List<InvSection> getByStatus(Integer status) {
		// TODO Auto-generated method stub
		return invSectionDao.getByStatus(status);
	}
	  /**
     * 根据条件获取响应库位
     * @param secCode
     * @return
     */
	@Override
	public InvSection getInvSection(String secCode, String channelCode, String itemProperty) {
		// TODO Auto-generated method stub
		return invSectionDao.getInvSection(secCode, channelCode, itemProperty);
	}

	@Override
	public InvSection getByLesSecCodeAndChannelCode(String lesSecCode, String channelCode) {
		// TODO Auto-generated method stub
		return invSectionDao.getByLesSecCodeAndChannelCode(lesSecCode, channelCode);
	}

	@Override
	public List getPageByCondition(Object entity, int start, int rows) {
		// TODO Auto-generated method stub
		return invSectionDao.getPageByCondition((InvSection) entity, start, rows);
	}

	@Override
	public long getPagerCount(Object entity) {
		// TODO Auto-generated method stub
		return invSectionDao.getPagerCount((InvSection) entity);
	}

	@Override
	public JSONObject getInvSectionList(InvSection invSection) {
		JSONObject result = new JSONObject();
		invSection.setStart((invSection.getPage()-1)*100);
		invSection.setSize(invSection.getRows());
		result.put("total", invSectionDao.countInvSectionWithLike(invSection));
		result.put("rows", invSectionDao.getInvSectionList(invSection));
		return result;
	}

	@Override
	public List<InvSection> queryInvSectionExcel(InvSection invSection) {

		return invSectionDao.queryInvSectionExcel(invSection);
	}

}
