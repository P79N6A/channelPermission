package com.haier.distribute.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.model.TChannelsInfo;
import com.haier.distribute.data.model.TSaleProductStock;
import com.haier.distribute.data.model.TWarehouse;
import com.haier.distribute.data.model.TWarehouseRegion;
import com.haier.distribute.data.service.RegionsService;
import com.haier.distribute.data.service.TChanneclsInfoService;
import com.haier.distribute.data.service.TSaleProductStockService;
import com.haier.distribute.data.service.TWarehouseRegionService;
import com.haier.distribute.data.service.TWarehouseService;
import com.haier.distribute.service.DistributeCenterWareHouseService;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.PopInvWarehouse;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockPopInvWarehouseService;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/16 0016
 * \* Time: 9:19
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class DistributeCenterWareHouseServiceImpl implements DistributeCenterWareHouseService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(DistributeCenterWareHouseServiceImpl.class);

    @Autowired
    private TWarehouseService tWarehouseService;
    @Autowired
    private TChanneclsInfoService tChanneclsInfoService;
    @Autowired
    private TWarehouseRegionService tWarehouseRegionService;
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private TSaleProductStockService tSaleProductStockService;

    @Override
    public JSONObject wareHouseList(PagerInfo pager, TWarehouse condition) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TWarehouse> list = tWarehouseService.getPageByCondition(condition, pager.getStart(), pager.getPageSize());
        long total = tWarehouseService.getPagerCount(condition);
        for (TWarehouse twh : list) {
            if (null != twh.getCreateTime()) {
                if (null != twh.getCreateTime()) {
                    twh.setcTime(sdf.format(twh.getCreateTime()));
                }
                if (null != twh.getUpdateTime()) {
                    twh.setuTime(sdf.format(twh.getUpdateTime()));
                }
            }
        }
        return jsonResult(list, total);
    }

    @Override
    public String addWarehouse(TWarehouse tWarehouse,String userName) {

        List<TWarehouse> checkCode = tWarehouseService.checkCode(tWarehouse);
        if (null != tWarehouse.getId()) {
            if (checkCode != null && !checkCode.isEmpty()) {
                return "codeIsSame";
            } else {
                tWarehouse.setUpdateBy(userName);
                tWarehouse.setUpdateTime(new Date());
                tWarehouseService.updateByPrimaryKey(tWarehouse);
                return "success";
            }
        } else {
            if (checkCode != null && !checkCode.isEmpty()) {
                return "codeIsSame";
            } else {
                tWarehouse.setCreateBy(userName);
                tWarehouse.setCreateTime(new Date());
                tWarehouseService.insertSelective(tWarehouse);
                return "success";
            }
        }
    }

    @Override
    public List<TChannelsInfo> getChannelIdList() {
        return tChanneclsInfoService.getList();
    }

    @Override
    public int removeWarehouse(Integer id) {
        return tWarehouseService.deleteByPrimaryKey(id);
    }

    @Override
    public JSONObject twRegionList(PagerInfo pager, TWarehouseRegion condition) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TWarehouseRegion> list = tWarehouseRegionService.getPageByCondition(condition, pager.getStart(), pager.getPageSize());
        long total = tWarehouseRegionService.getPagerCount(condition);
        for (TWarehouseRegion twr : list) {
            if (null != twr.getCreateTime()) {
                if (null != twr.getCreateTime()) {
                    twr.setcTime(sdf.format(twr.getCreateTime()));
                }
                if (null != twr.getUpdateTime()) {
                    twr.setuTime(sdf.format(twr.getUpdateTime()));
                }
            }
        }
        return jsonResult(list, total);
    }

    @Override
    public List<TWarehouse> getTWarehouseList() {
        return tWarehouseService.getAll();
    }

    @Override
    public int addTwRegion(int channelId, List<String> regionId, int warehouseId, String remark) {
        int flag = 0;
        int sameCount = 0;
        int size = regionId.size();
        //去除非第三级的区域（省、市）
        for (int i = 0; i < regionId.size(); i++) {
            Regions regionsForCounty = regionsService.selectByPrimaryKey(Integer.parseInt(regionId.get(i)));
            String parentPath = regionsForCounty.getParentPath();
            String[] path = parentPath.split("/");
            int len = path.length;
            if (len < 3) {
                regionId.remove(i);
                continue;
            }
        }
        //去除重复数据
        for (int i = 0; i < regionId.size(); i++) {
            flag = tWarehouseRegionService.checkRegion(channelId, Integer.parseInt(regionId.get(i)), 0);
            if (flag > 0) {
                sameCount++;
                regionId.remove(i);
                continue;
            }
        }
        if (sameCount == size + 1) {
            return flag;
        } else {
            for (int j = 0; j < regionId.size(); j++) {
                Regions regionsForCounty = regionsService.selectByPrimaryKey(Integer.parseInt(regionId.get(j)));
                String county = regionsForCounty.getRegionName();
                String parentPath = regionsForCounty.getParentPath();
                String[] path = parentPath.split("/");
                Regions regionsForCity = regionsService.selectByPrimaryKey(Integer.parseInt(path[2]));
                String city = regionsForCity.getRegionName();
                Regions regionsForProvince = regionsService.selectByPrimaryKey(Integer.parseInt(path[1]));
                String province = regionsForProvince.getRegionName();

                TWarehouseRegion tWarehouseRegion = new TWarehouseRegion();
                tWarehouseRegion.setChannelId(channelId);
                tWarehouseRegion.setRegionParentPath(parentPath);
                tWarehouseRegion.setRegionId(Integer.parseInt(regionId.get(j)));
                tWarehouseRegion.setWarehouseId(warehouseId);
                tWarehouseRegion.setCity(city);
                tWarehouseRegion.setProvince(province);
                tWarehouseRegion.setCounty(county);
                tWarehouseRegion.setRemark(remark);
                tWarehouseRegion.setCreateTime(new Date());
                tWarehouseRegion.setCreateBy("no one");
                tWarehouseRegionService.insertSelective(tWarehouseRegion);
            }
            return 1;
        }
    }

    @Override
    public int updateTwRegion(int id, int channelId, int regionId, int warehouseId, String remark) {

        int flag = tWarehouseRegionService.checkRegion(channelId, regionId, id);
        if (flag > 0) {
            return 0;
        } else {
            Regions regionsForCounty = regionsService.selectByPrimaryKey(regionId);
            String county = regionsForCounty.getRegionName();
            String parentPath = regionsForCounty.getParentPath();
            String[] path = parentPath.split("/");
            Regions regionsForCity = regionsService.selectByPrimaryKey(Integer.parseInt(path[2]));
            String city = regionsForCity.getRegionName();
            Regions regionsForProvince = regionsService.selectByPrimaryKey(Integer.parseInt(path[1]));
            String province = regionsForProvince.getRegionName();

            TWarehouseRegion tWarehouseRegion = new TWarehouseRegion();
            tWarehouseRegion.setId((long) id);
            tWarehouseRegion.setChannelId(channelId);
            tWarehouseRegion.setRegionParentPath(parentPath);
            tWarehouseRegion.setRegionId(regionId);
            tWarehouseRegion.setWarehouseId(warehouseId);
            tWarehouseRegion.setCity(city);
            tWarehouseRegion.setProvince(province);
            tWarehouseRegion.setCounty(county);
            tWarehouseRegion.setRemark(remark);
            tWarehouseRegion.setUpdateTime(new Date());
            tWarehouseRegion.setUpdateBy("no one");
            return tWarehouseRegionService.updateByPrimaryKeySelective(tWarehouseRegion);
        }
    }

    @Override
    public int removeTwRegion(Integer id) {
        return tWarehouseRegionService.deleteByPrimaryKey(id);
    }

    @Override
    public List<TWarehouse> getWareHouseServiceStart() {
        return tWarehouseService.getWareHouseServiceStart();
    }

    @Override
    public JSONObject SaleStockList(PagerInfo pager, TSaleProductStock condition) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TSaleProductStock> list = tSaleProductStockService.getPageByCondition(condition, pager.getStart(), pager.getPageSize());
        long total = tSaleProductStockService.getPagerCount(condition);
        for (TSaleProductStock twr : list) {
            if (null != twr.getCreateTime()) {
                if (null != twr.getCreateTime()) {
                    twr.setcTime(sdf.format(twr.getCreateTime()));
                }
                if (null != twr.getUpdateTime()) {
                    twr.setuTime(sdf.format(twr.getUpdateTime()));
                }
            }
        }
        return jsonResult(list, total);
    }

    @Override
    public String addSaleStock(TSaleProductStock tSaleProductStock) {

        String userName = "no one";
        List<TSaleProductStock> checkCode = tSaleProductStockService.checkCode(tSaleProductStock);
        if (null != tSaleProductStock.getId()) {
            if (checkCode != null && !checkCode.isEmpty()) {
                return "codeIsSame";
            } else {
                tSaleProductStock.setUpdateBy(userName);
                tSaleProductStock.setUpdateTime(new Date());
                tSaleProductStockService.updateByPrimaryKeySelective(tSaleProductStock);
                return "success";
            }
        } else {
            if (checkCode != null && !checkCode.isEmpty()) {
                return "codeIsSame";
            } else {
                tSaleProductStock.setCreateBy(userName);
                tSaleProductStock.setCreateTime(new Date());
                tSaleProductStockService.insertSelective(tSaleProductStock);
                return "success";
            }
        }
    }

    @Override
    public int removeSaleStock(Integer id) {
        return tSaleProductStockService.deleteByPrimaryKey(id);
    }

    @Override
    public int getRegionType(int regionId) {
        return regionsService.getRegionType(regionId);
    }

    @Override
    public List<Map<String, Object>> autoLoadPid(Integer channelId, Integer id) {

        // TODO Auto-generated method stub
        List<Map<String, Object>> list = tWarehouseService.autoLoadPid(channelId, id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", "");
        map.put("warehouseCode", "");
        map.put("warehouseName", "请输入配送仓库");
        list.add(0, map);
        return list;
    }

    @Override
    public Regions geParentId(Integer cid) {
        return regionsService.selectByPrimaryKey(cid);
    }

    @Override
    public List<Regions> getRegionsAll() {
        return regionsService.getRegionsAll();
    }

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


}