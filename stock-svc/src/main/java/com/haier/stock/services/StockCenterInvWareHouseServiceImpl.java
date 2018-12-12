package com.haier.stock.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.TChannelsInfo;
import com.haier.distribute.data.model.TWarehouse;
import com.haier.distribute.data.service.TChanneclsInfoService;
import com.haier.distribute.data.service.TWarehouseService;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.InvWarehouseInfo;
import com.haier.stock.model.PopInvWarehouse;
import com.haier.stock.model.StoreModel;
import com.haier.stock.service.StockCenterInvWareHouseService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockPopInvWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 胡万来 on 2018/1/19 0019.
 */
@Service
public class StockCenterInvWareHouseServiceImpl implements StockCenterInvWareHouseService {

    @Autowired
    private StockInvSectionService invSectionDao;
    @Autowired
    private StockPopInvWarehouseService popInvWarehouseDao;
    @Autowired
    StoreModel storeModel;

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(StockCenterInvWareHouseServiceImpl.class);

    @Override
    public JSONObject invSectionList(PagerInfo pager, InvSection condition) {
        List<InvSection> list = invSectionDao.getPageByCondition(condition, pager.getStart(), pager.getPageSize());
        long total = invSectionDao.getPagerCount(condition);
        return jsonResult(list, total);
    }

    @Override
    public String addInvSection(InvSection condition) {
        long check = invSectionDao.checkSame(condition.getSecCode());
        if ("insert".equals(condition.getId())) {
            if (check > 0) {
                return "codeIsSame";
            } else {
                condition.setUpdateTime(new Date());
                invSectionDao.insertSelective(condition);
                return "success";
            }
        } else {
            condition.setUpdateTime(new Date());
            invSectionDao.updateByPrimaryKeySelective(condition);
            return "success";
        }
    }

    @Override
    public String addInvWarehouse(PopInvWarehouse condition) {
        long checkCode = popInvWarehouseDao.checkCodeSame(condition.getWhCode());
        long checkName = popInvWarehouseDao.checkNameSame(condition.getWhCode());
        if ("insert".equals(condition.getId())) {
            if (checkCode > 0) {
                return "codeIsSame";
            } else if (checkName > 0) {
                return "nameIsSame";
            } else {
                condition.setCreateTime(new Date());
                if (1 == popInvWarehouseDao.insertSelective(condition)) {
                    return "success";
                } else {
                    return "fail";
                }
            }
        } else {
            condition.setUpdateTime(new Date());
            if (1 == popInvWarehouseDao.updateByPrimaryKeySelective(condition)) {
                return "success";
            } else {
                return "fail";
            }
        }
    }

    @Override
    public JSONObject invWarehouseList(PagerInfo pager, PopInvWarehouse condition) {
        List<PopInvWarehouse> list = popInvWarehouseDao.getPageByCondition(condition, pager.getStart(), pager.getPageSize());
        long total = popInvWarehouseDao.getPagerCount(condition);
        return jsonResult(list, total);
    }

    @Override
    public int removeInvWarehouse(String id) {
        return popInvWarehouseDao.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, String> getChannelMap(Map<String, String> invstockchannelmap) {
        // 调用stockCommonService，取得渠道数据
        List<InvStockChannel> tChannelsInfo = storeModel.getChannels();
        if (tChannelsInfo != null) {
            for (InvStockChannel channel : tChannelsInfo) {
                invstockchannelmap.put(channel.getChannelCode(),
                        channel.getName());// 将channelcode作为key，name作为value存入map中
            }
        }
        return invstockchannelmap;
    }

    @Override
    public ServiceResult<Map<String, Integer>> insertInvSections(List<InvSection> invSections) {

        ServiceResult<Map<String, Integer>> result = new ServiceResult<Map<String, Integer>>();
        try {
            int success = 0;
            int failure = 0;
            if (invSections == null)
                throw new BusinessException(
                        "[Warehouse_service]:insertInvSections对象不能为空");
            for (InvSection invSection : invSections) {
                try {
                    invSectionDao.insertSelective(invSection);
                    success++;
                } catch (Exception ex) {
                    log.error(
                            "[Warehouse_service][insertInvSections]:创建虚拟库位表单发生未知异常:",
                            ex);
                    failure++;
                }
            }
            // 提交事务
            // transactionManager.commit(status);
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("success", success);
            map.put("failure", failure);
            result.setResult(map);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("创建虚拟库位表单失败，" + e.getMessage());
            log.error("[Warehouse_service][insertInvSections]:虚拟库位表单创建失败", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Map<String, Integer>> insertInvWarehouses(List<PopInvWarehouse> invWarehouses) {
        ServiceResult<Map<String, Integer>> result = new ServiceResult<Map<String, Integer>>();
        try {
            int success = 0;
            int failure = 0;
            if (invWarehouses == null)
                throw new BusinessException(
                        "[Warehouse_service][T2OrderItem]:insertInvWarehouses对象不能为空");
            for (PopInvWarehouse invWarehouse : invWarehouses) {
                try {
                    popInvWarehouseDao.insertSelective(invWarehouse);
                    success++;
                } catch (Exception ex) {
                    log.error(
                            "[Warehouse_service][insertInvWarehouses]:创建基本库位表单发生未知异常:",
                            ex);
                    failure++;
                }
            }
            // 提交事务
            // transactionManager.commit(status);
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("success", success);
            map.put("failure", failure);
            result.setResult(map);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("创建基本库位表单失败，" + e.getMessage());
            log.error("[Warehouse_service][insertInvWarehouses]:基本库位表单创建失败", e);
        }
        return result;
    }

    @Override
    public long checkInvSectionByCode(String code) {
        return invSectionDao.checkSame(code);
    }

    @Override
    public long checkInvWarehouseByCode(String code) {
        return popInvWarehouseDao.checkCodeSame(code);
    }

    @Override
    public long checkInvWarehouseByName(String name) {
        return popInvWarehouseDao.checkNameSame(name);
    }

    @Override
    public List<InvSection> exportSection(InvSection condition) {
        return invSectionDao.exportSection(condition);
    }

    @Override
    public List<PopInvWarehouse> exportInvWarehouse(PopInvWarehouse condition) {
        return popInvWarehouseDao.exportWarehouse(condition);
    }

    @Override
    public int removeInvSection(String id) {
        return invSectionDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<InvStockChannel> getChannelIdList() {
        List<InvStockChannel> list = storeModel.getChannels();
        return list;
    }


//    @Override
//    public List<TWarehouse> getWareHouseServiceStart() {
//        return tWarehouseDao.getWareHouseServiceStart();
//    }


    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }

	@Override
	public List<InvWarehouse> findCenter() {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.findCenter();
	}
}
