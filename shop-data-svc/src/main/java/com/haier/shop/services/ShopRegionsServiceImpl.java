package com.haier.shop.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Regions;
import com.haier.shop.service.ShopRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShopRegionsServiceImpl implements ShopRegionsService {
    @Autowired
    private RegionsServiceImpl regionsService;

    @Override
    public JSONObject Listf(PagerInfo pager, Regions condition) {
        Regions u = new Regions();
        List<Regions> list = regionsService.Listf(condition,pager.getStart(), pager.getPageSize());
        int total = regionsService.getPagerCountS(condition);
        JSONArray res = new JSONArray();
        if (list != null) {
            for (Regions o : list) {
                Regions regions = o;
                JSONObject json = new JSONObject();
                json.put("id", regions.getId());
                json.put("regionName", regions.getRegionName());
                json.put("regionType", regions.getRegionType());
                json.put("parentId", regions.getParentId());
                json.put("parentPath", regions.getParentPath());
                json.put("firstLetter", regions.getFirstLetter());
                json.put("shippingTime", regions.getShippingTime());
                json.put("visible", regions.getVisible());
                json.put("rowId", regions.getRowId());
                json.put("testNum", regions.getTestNum());
                json.put("standardRegionId", regions.getStandardRegionId());
                json.put("code", regions.getCode());
                json.put("activeFlag", regions.getActiveFlag());
                json.put("hasRead", regions.getHasRead());
                json.put("isSupportCod", regions.getIsSupportCod());
                json.put("addTime", regions.getAddTime());
                json.put("modified", regions.getModified());
                json.put("zipCode", regions.getZipCode());
                json.put("receivingTime", regions.getReceivingTime());
                json.put("shippingDistance", regions.getShippingDistance());
                json.put("isOto", regions.getIsOto());
                res.add(json);
            }
        }
        return jsonResult(res,total);
    }

    @Override
    public JSONArray getRegion(int id) {
        return JSONArray.parseArray(JSON.toJSONString(regionsService.getRegion(id)));
    }

    @Override
    public ServiceResult<Boolean> insert(Regions condition) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        condition.setAddTime(sdf.format(d));
        condition.setModified(d);
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        int x = regionsService.insert(condition);
        if (x == 1){
            result.setSuccess(true);
            result.setMessage("保存成功");
        }else {
            result.setSuccess(false);
            result.setMessage("保存失败");
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> update(Regions condition) {
        Date d = new Date();
        condition.setModified(d);
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        Integer regionType = condition.getRegionType();
        int x = regionsService.updateByPrimaryKeySelective(condition);
        if (regionType == 3) {
            if (x == 1) {
                condition.setParentId(condition.getId());
                int i = regionsService.updateByParentId(condition);
                result.setSuccess(true);
                result.setMessage("更新成功");
            } else {
                result.setSuccess(false);
                result.setMessage("更新失败");
            }
        }else {
            if (x == 1) {
                result.setSuccess(true);
                result.setMessage("更新成功");
            } else {
                result.setSuccess(false);
                result.setMessage("更新失败");
            }
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> delete(int id) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        int x = regionsService.deleteByPrimaryKey(id);
        if (x == 1){
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteSubordinateRegion(int id, int regionType) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (regionType == 1) {
                //查出下辖的所有的市
                List<Regions> r1 = regionsService.getRegion(id);
                //市不为空
                if (r1 != null || r1.size() != 0) {
                    for (Regions region1 : r1) {
                        //查出下辖的所有的市  下辖的所有区
                        List<Regions> r2 = regionsService.getRegion(region1.getId());
                        if (r2 != null || r2.size() != 0) {
                            for (Regions region2 : r2) {
                                //查出下辖的所有的市  下辖的所有区 下辖的所有街
                                List<Regions> r3 = regionsService.getRegion(region2.getId());
                                if (r3 != null || r3.size() != 0) {
                                    for (Regions region3 : r3) {
                                        //删除当前街
                                        regionsService.deleteByPrimaryKey(region3.getId());
                                    }
                                }
                                //删除当前区
                                regionsService.deleteByPrimaryKey(region2.getId());
                            }
                        }
                        //删除当前市
                        regionsService.deleteByPrimaryKey(region1.getId());
                    }
                }
                //删除当前省
                regionsService.deleteByPrimaryKey(id);

            } else if (regionType == 2) {
                //查出市 下辖的所有区
                List<Regions> r1 = regionsService.getRegion(id);
                //区不为空
                if (r1 != null || r1.size() != 0) {
                    for (Regions region1 : r1) {
                        //查出下辖的所有区下的街
                        List<Regions> r2 = regionsService.getRegion(region1.getId());
                        //街不为空
                        if (r2 != null || r2.size() != 0) {
                            //删除当前街
                            for (Regions region2 : r2) {
                                regionsService.deleteByPrimaryKey(region2.getId());
                            }
                        }
                        //删除当前区
                        regionsService.deleteByPrimaryKey(region1.getId());
                    }
                }
                //删除当前市
                regionsService.deleteByPrimaryKey(id);

            } else if (regionType == 3) {
                //查出区下辖的所有街
                List<Regions> r1 = regionsService.getRegion(id);
                if (r1 != null || r1.size() != 0) {
                    for (Regions region1 : r1) {
                        //删除当前街
                        regionsService.deleteByPrimaryKey(region1.getId());
                    }
                }
                //删除当前区
                regionsService.deleteByPrimaryKey(id);
            }else {}
            result.setSuccess(true);
            result.setMessage("删除成功");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
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
