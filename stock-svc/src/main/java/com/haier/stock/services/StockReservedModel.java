package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.QueryTotalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.service.InvReservedToReleaseService;
@Service
public class StockReservedModel {
	@Autowired
	private InvReservedToReleaseService invReservedToReleaseDao;
	   public Boolean updateStockReservedConfig(InvReservedConfig config) {
	        return this.invReservedToReleaseDao.updateReservedConfigById(config) > 0;
	    }
	   
	   /**
	     * 查询预留配置信息
	     * @param config
	     * @return
	     */
	    public List<InvReservedConfig> queryInvReservedConfigs(InvReservedConfig config) {
	        try {
	            return this.invReservedToReleaseDao.getReservedConfig(config);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    /**
	     * 添加预留配置信息
	     * @param config
	     * @return
	     */
	    public Boolean insertStockReservedConfig(InvReservedConfig config) {
	        return this.invReservedToReleaseDao.insertReservedConfig(config) > 0;
	    }
	/**
	 * 查询预留配置信息
	 * @param
	 * @return
	 */
	public List<Map<String,String>> queryTotalPage(Map<String,Object> map) {
		try {
			return invReservedToReleaseDao.queryTotalPage(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<QueryTotalData> queryTotalData(Map<String,Object> map, int start, int rows) {
		try {
			return invReservedToReleaseDao.queryTotalData(map,start,rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
