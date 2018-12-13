package com.haier.stock.services;

import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockBatch;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.stock.model.QueryTotalData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.service.InvReservedToReleaseService;
@Service
public class StockReservedModel {

  private final static Logger log = LoggerFactory.getLogger(StockReservedModel.class);

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

  public boolean stockReservedToRelease(InvStockBatch stockBatch, InvSection invSection,
      Map<String, InvReservedConfig> refMap,
      Map<String, InvReservedConfig> channelMap,
      StringBuffer info) {
    InvReservedConfig config = null;
    //1.检查单号是否有预留
    if (refMap.containsKey(stockBatch.getRefno())) {
      config = refMap.get(stockBatch.getRefno());
      //单号已开启预留，则单号预留不能修改
      reservedConfigOnlyRead(config.getId());
      //1.入WA的单号：调拨， 统帅、新宝产品入库，直接指定单号预留，并产生调拨
      //2.入渠道的单号：单号号对应渠道和入库单号对应的渠道匹配，则直接预留
      //3.入渠道单号：不匹配，走渠道预留
      if (invSection.isChannelWA()) {
        log.info("调拨预留，refno:" + stockBatch.getRefno());
        info.append("预留不能直接进行，需调拨, refno:" + stockBatch.getRefno());
        return false;
      } else if (!config.getChannelCode().equals(invSection.getChannelCode())) {
        config = null;
      }
    }
    //如果1步没找到可用配置，则检查渠道
    if (config == null && channelMap.containsKey(invSection.getChannelCode().toUpperCase())) {
      info.append("按照指定渠道预留， refno:" + stockBatch.getRefno());
      config = channelMap.get(invSection.getChannelCode().toUpperCase());
    }
    if (config != null) {
      insertInvReservedToRelease(stockBatch, config);
    }
    return true;
  }

  private void reservedConfigOnlyRead(Integer id) {
    InvReservedConfig updateConfig = new InvReservedConfig();
    updateConfig.setAllowUpdate(0);
    updateConfig.setId(id);
    updateConfig.setUpdateUser("job");
    updateStockReservedConfig(updateConfig);
  }

  /**
   * 插入预留释放记录
   */
  public boolean insertInvReservedToRelease(InvStockBatch stockBatch, InvReservedConfig config) {
    SimpleDateFormat format = new SimpleDateFormat();
    Calendar cl = Calendar.getInstance();
    cl.setTime(stockBatch.getAddTime());
    if (config != null) {
      cl.add(Calendar.HOUR, config.getLockHours());
      log.debug("释放时间：" + format.format(cl.getTime()) + ", batchId:" + stockBatch.getId());
      this.invReservedToReleaseDao.insertReserved(stockBatch.getId(), cl.getTime());
    }
    return true;

  }

  private Map<String, InvReservedConfig> convertReservedChannelConfigs(
      List<InvReservedConfig> configs) {
    Map<String, InvReservedConfig> channels = new HashMap<String, InvReservedConfig>();
    for (InvReservedConfig config : configs) {
      if (config.getChannelCode() != null) {
        channels.put(config.getChannelCode().toUpperCase(), config);
      }
    }
    return channels;
  }
}
