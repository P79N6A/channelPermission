package com.haier.svc.api.controller.pop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.haier.distribute.service.DistributeCenterWareHouseService;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.model.TChannelsInfo;
import com.haier.distribute.data.model.TSaleProductStock;
import com.haier.distribute.data.model.TWarehouse;
import com.haier.distribute.data.model.TWarehouseRegion;

/**
 * @author hwl
 *         2017年11月1日
 */
@Controller
@Api(name = "分销仓库", description = "WareHouseController")
@RequestMapping(value = "warehouse/")
public class WareHouseController {

    @Autowired
    DistributeCenterWareHouseService distributeCenterWareHouseService;

    /**
     * 跳转仓库页面
     *
     * @param modal
     * @return
     */
    @RequestMapping(value = {"warehouseList"}, method = {RequestMethod.GET})
    public String warehouseIndex(Model modal) {
        List<TChannelsInfo> tChanneclsInfo = distributeCenterWareHouseService.getChannelIdList();
        List<TWarehouse> tWarehouse = distributeCenterWareHouseService.getWareHouseServiceStart();
        modal.addAttribute("cidList", tChanneclsInfo);
        modal.addAttribute("tWarehouse", tWarehouse);
        return "pop/warehouse/warehouseList";
    }

    /**
     * 仓库的条件查询
     *
     * @param page
     * @param rows
     * @param condition
     * @param session
     * @return
     */
    @RequestMapping("/findWarehouseList")
    @ResponseBody
    public JSONObject findWarehouseList(int page, int rows, TWarehouse condition, HttpSession session) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return distributeCenterWareHouseService.wareHouseList(pager, condition);
    }

    /**
     * 添加和修改仓库信息
     *
     * @param tWarehouse
     * @param session
     * @return
     */
    @RequestMapping("/addWarehouse")
    @ResponseBody
    public String addWarehouse(TWarehouse tWarehouse, HttpSession session) {
    	String username =(String) session.getAttribute("username");
        return distributeCenterWareHouseService.addWarehouse(tWarehouse,username);
    }

    /**
     * 删除仓库信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeWarehouse", method = RequestMethod.POST)
    @ResponseBody
    public String removeWarehouse(Integer id) {
        JSONObject json = new JSONObject();
        int flag = distributeCenterWareHouseService.removeWarehouse(id);
        if (1 == flag) {
            json.put("text", "success");
        } else {
            json.put("text", "fail");
        }
        return json.toString();
    }

    /**
     * 跳转仓库区域
     *
     * @param modal
     * @return
     */
    @RequestMapping(value = {"twRegionList"}, method = {RequestMethod.GET})
    public String twRegionIndex(Model modal) {
        List<TChannelsInfo> tChanneclsInfo = distributeCenterWareHouseService.getChannelIdList();
        List<TWarehouse> tWarehouse = distributeCenterWareHouseService.getTWarehouseList();
        modal.addAttribute("twhList", tWarehouse);
        modal.addAttribute("cidList", tChanneclsInfo);
        return "pop/warehouse/twRegionList";
    }

    /**
     * 仓库区域的条件查询
     *
     * @param page
     * @param rows
     * @param condition
     * @param session
     * @return
     */
    @RequestMapping("/findtwRegionList")
    @ResponseBody
    public JSONObject findtwRegionList(int page, int rows, TWarehouseRegion condition, HttpSession session) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return distributeCenterWareHouseService.twRegionList(pager, condition);
    }

    /**
     * 添加区域
     *
     * @param channelId
     * @param regionId
     * @param warehouseId
     * @param remark
     * @return
     */
    @RequestMapping(value = "/addTwRegion", method = RequestMethod.POST)
    @ResponseBody
    public String addTwRegion(int channelId, String[] regionId, int warehouseId, String remark) {
        List<String> list = Arrays.asList(regionId);
        int flag = distributeCenterWareHouseService.addTwRegion(channelId, list, warehouseId, remark);
        if (flag == 0) {
            return "regionIsSame";
        } else {
            return "add";
        }
    }

    /**
     * 修改区域
     *
     * @param id
     * @param channelId
     * @param regionId
     * @param warehouseId
     * @param remark
     * @return
     */
    @RequestMapping(value = "/updateTwRegion", method = RequestMethod.POST)
    @ResponseBody
    public String updateTwRegion(int id, int channelId, int regionId, int warehouseId, String remark) {
        int regionType = distributeCenterWareHouseService.getRegionType(regionId);
        if (regionType != 3) {
            return "notCounty";
        } else {
            int result = distributeCenterWareHouseService.updateTwRegion(id, channelId,
                    regionId, warehouseId, remark);
            if (result == 0) {
                return "regionIsSame";
            } else {
                return "edit";
            }
        }

    }

    /**
     * 删除区域
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeTwRegion", method = RequestMethod.POST)
    @ResponseBody
    public String removeTwRegion(Integer id) {
        JSONObject json = new JSONObject();
        int flag = distributeCenterWareHouseService.removeTwRegion(id);
        if (1 == flag) {
            json.put("text", "success");
        } else {
            json.put("text", "fail");
        }
        return json.toString();
    }

    /**
     * 跳转可售商品库存
     *
     * @param modal
     * @return
     */
    @RequestMapping(value = {"saleStock"}, method = {RequestMethod.GET})
    public String saleStockIndex(Model modal) {
        List<TChannelsInfo> tChanneclsInfo = distributeCenterWareHouseService.getChannelIdList();
        List<TWarehouse> tWarehouse = distributeCenterWareHouseService.getTWarehouseList();
        modal.addAttribute("twhList", tWarehouse);
        modal.addAttribute("cidList", tChanneclsInfo);
        return "pop/warehouse/saleStock";
    }

    /**
     * 可售商品库存条件查询
     *
     * @param page
     * @param rows
     * @param condition
     * @return
     */
    @RequestMapping("/findSaleStockList")
    @ResponseBody
    public JSONObject findSaleStockList(int page, int rows, TSaleProductStock condition) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return distributeCenterWareHouseService.SaleStockList(pager, condition);
    }

    /**
     * 添加或者修改可售商品库存
     *
     * @param tSaleProductStock
     * @return
     */
    @RequestMapping("/addSaleStock")
    @ResponseBody
    public String addSaleStock(TSaleProductStock tSaleProductStock) {
        return distributeCenterWareHouseService.addSaleStock(tSaleProductStock);
    }

    /**
     * 删除可售商品库存信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeSaleStock", method = RequestMethod.POST)
    @ResponseBody
    public String removeSaleStock(Integer id) {
        JSONObject json = new JSONObject();
        int flag = distributeCenterWareHouseService.removeSaleStock(id);
        if (1 == flag) {
            json.put("text", "success");
        } else {
            json.put("text", "fail");
        }
        return json.toString();
    }

    /**
     * 动态加载相应仓库
     *
     * @param channelId
     * @param id
     * @return
     */
    @RequestMapping(value = "/autoLoadPid", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> autoLoadPid(Integer channelId, Integer id) {
        List<Map<String, Object>> map = distributeCenterWareHouseService.autoLoadPid(channelId, id);
        return map;
    }

    /**
     * 获取区域树形的父节点
     *
     * @param cid
     * @return
     */
    @RequestMapping(value = "/geParentId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> geParentId(Integer cid) {
        if (cid == null) {
            return null;
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            Regions rd = distributeCenterWareHouseService.geParentId(cid);
            String pid = rd.getParentPath();
            String[] p = pid.split("/");
            map.put("one", p[1]);
            map.put("two", p[2]);
            return map;
        }
    }
    /**
     * -------------------------------------------tree-------------------------------------------
     */
    //存放转换后数据的集合
    List<Map<String, Object>> comboTreeList = new ArrayList<Map<String, Object>>();

    /**
     * 返回 treeGrid(树形表格)需要的json格式数据
     * 前端调用的就是这个方法
     */
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public String backComboTreeTreeRegions() {
        JSONArray res = new JSONArray();
        List<Regions> list = distributeCenterWareHouseService.getRegionsAll();

        createComboTreeTree(list, 0);

        Gson gson = new Gson();
        String json = gson.toJson(comboTreeList);

        System.err.println("@@@@" + json);
        comboTreeList.clear();
        return json;
    }


    /**
     * 将角色封装成树开始
     *
     * @param list
     * @param fid  父id
     */
    private void createComboTreeTree(List<Regions> list, int fid) {
        for (int i = 0; i < list.size(); i++) {
            Regions regions = list.get(i);
            Map<String, Object> map = null;
            if (regions.getParentId() == 0) {
                map = new HashMap<String, Object>();
                //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text
                //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性
                map.put("id", list.get(i).getId());
                map.put("text", list.get(i).getRegionName());
                map.put("children", createComboTreeChildren(list, regions.getId()));
                map.put("state", "closed");
            }
            if (map != null)
                comboTreeList.add(map);
        }
    }

    /**
     * 递归设置role树
     *
     * @param list
     * @param fid
     * @return
     */
    private List<Map<String, Object>> createComboTreeChildren(List<Regions> list, int fid) {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

        for (int j = 0; j < list.size(); j++) {
            Map<String, Object> map = null;
            Regions treeChild = list.get(j);
            if (treeChild.getParentId() == fid) {
                map = new HashMap<String, Object>();
                //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text
                //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性
                map.put("id", list.get(j).getId());
                map.put("text", list.get(j).getRegionName());
                if (list.get(j).getRegionType() == 3) {
                    map.put("state", "open");
                } else {
                    map.put("children", createComboTreeChildren(list, treeChild.getId()));
                    map.put("state", "closed");
                }
            }
            if (map != null)
                childList.add(map);
        }
        return childList;
    }

}
