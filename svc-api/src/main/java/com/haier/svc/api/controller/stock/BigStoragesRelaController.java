package com.haier.svc.api.controller.stock;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrderPriceSourceChannel;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.StoragesRela;
import com.haier.shop.service.BigStoragesRelaService;
import com.haier.shop.service.StoragesRelaService;
import com.haier.shop.service.StoragesService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: liwei
 * @Description: 大家电多层级库位关系
 * @Date: Create in 15:22 2018/7/18
 * @Modified By:
 */
@Controller
@RequestMapping("bigStoragesRela")
public class BigStoragesRelaController {

  private org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(this.getClass());

  @Autowired
  private BigStoragesRelaService bigStoragesRelaService;

  @Autowired
  private StoragesService storagesService;

  @Autowired
  private StoragesRelaService storagesRelaService;

  /**
   * 大家电多层级关系页面
   * @return
   */
  @RequestMapping(value = { "/loadBigStoragesRela" }, method = { RequestMethod.GET })
  public String loadBigStorage() {
    return "stock/bigStoragesRela";
  }

  /**
   * 大家电多层级关系查询
   * @param code
   * @param masterCode
   * @param centerCode
   * @param name
   * @param masterName
   * @param centerName
   * @param page
   * @param rows
   * @return
   */
  @RequestMapping(value = { "/getBigStoragesRela" })
  @ResponseBody
  public JSONObject getBigStoragesRela(@RequestParam(required = false) String code,
      @RequestParam(required = false) String masterCode,
      @RequestParam(required = false) String centerCode,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String masterName,
      @RequestParam(required = false) String centerName,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer rows) {
    PagerInfo pagerInfo = new PagerInfo(rows, page);
    JSONObject json = new JSONObject();
    try {
      if (pagerInfo.getPageIndex() > 0) {
        page = (page - 1) * rows;
      }
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("code", code);
      params.put("masterCode", masterCode);
      params.put("centerCode", centerCode);
      params.put("name", name);
      params.put("masterName", masterName);
      params.put("centerName", centerName);
      params.put("start", page);
      params.put("size", rows);

      int count = bigStoragesRelaService.getListCountByParam(params);
      List<BigStoragesRela> list = bigStoragesRelaService.getListByParam(params);

      json.put("rows", list);
      json.put("total", Long.valueOf(count));
    } catch (Exception e) {
      logger.error("查询【多层级关系数据】失败", e);
    }
    return json;
  }

  /**
   * 新增更新
   * @param id
   * @param centerStorage
   * @param masterStorage
   * @param storage
   * @param masterShippingTime
   * @param masterDistance
   * @param centerShippingTime
   * @param centerDistance
   * @param flag
   * @return
   */
  @RequestMapping(value = { "/saveOrUpdateBig" }, method = { RequestMethod.POST})
  @ResponseBody
  public HttpJsonResult<Map<String, Object>> saveOrUpdateBig(
      @RequestParam(required = false) Integer id,
      @RequestParam(required = true) String centerStorage,
      @RequestParam(required = true) String masterStorage,
      @RequestParam(required = true) String storage,
      @RequestParam(required = true) Integer masterShippingTime,
      @RequestParam(required = true) String masterDistance,
      @RequestParam(required = true) Integer centerShippingTime,
      @RequestParam(required = true) String centerDistance,
      @RequestParam(required = true) Integer flag) {
    HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
    try {

      BigStoragesRela bigStoragesRela = new BigStoragesRela();
      bigStoragesRela.setCode(storage);
      bigStoragesRela.setMasterCode(masterStorage);
      bigStoragesRela.setCenterCode(centerStorage);
      bigStoragesRela.setMasterShippingTime(masterShippingTime);
      bigStoragesRela.setMasterDistance(masterDistance);
      bigStoragesRela.setCenterShippingTime(centerShippingTime);
      bigStoragesRela.setCenterDistance(centerDistance);
      bigStoragesRela.setFlag(flag);

      if (id == null) {
        int count = bigStoragesRelaService.selectByCode(bigStoragesRela);
        if (count > 0) {
          result.setMessage("已存在，请核对！");
          return result;
        }
        count = bigStoragesRelaService.createBigStoragesRela(bigStoragesRela);
        if (count == 0) {
          result.setMessage("新增失败");
          return result;
        }
      } else {
        bigStoragesRela.setId(id);
        int count = bigStoragesRelaService.updateBigStoragesRela(bigStoragesRela);
        if (count == 0) {
          result.setMessage("更新失败！");
        }
      }
    } catch (Exception e) {
      logger.error("操作大家电多层级关系表发生异常", e);
      result.setMessage("维护信息时异常！");
    }
    return result;
  }

  @RequestMapping(value = { "/getStorages" }, method = { RequestMethod.GET })
  @ResponseBody
  public HttpJsonResult<List<Map<String, String>>> getStorages(@RequestParam(required = true) Integer type) {
    HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
    try {
      result.setData(storagesService.getInfoByType(type));
    } catch (Exception e) {
      if (type == 1) {
        logger.error("查询大家电库位发生异常",
            e);
        result.setMessage("查询大家电库位发生异常");
      } else {
        logger.error("查询小家电库位发生异常",
            e);
        result.setMessage("查询小家电库位发生异常");
      }

    }
    return result;
  }

  @RequestMapping(value = { "/loadStoragesRela" }, method = { RequestMethod.GET })
  public String loadStoragesRela() {
    return "stock/storagesRela";
  }

  /**
   * 小家电多层级关系查询
   * @param code
   * @param masterCode
   * @param centerCode
   * @param name
   * @param masterName
   * @param centerName
   * @param page
   * @param rows
   * @return
   */
  @RequestMapping(value = { "/getStoragesRela" })
  @ResponseBody
  public JSONObject getStoragesRela(@RequestParam(required = false) String code,
      @RequestParam(required = false) String masterCode,
      @RequestParam(required = false) String centerCode,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String masterName,
      @RequestParam(required = false) String centerName,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer rows) {
    PagerInfo pagerInfo = new PagerInfo(rows, page);
    JSONObject json = new JSONObject();
    try {
      if (pagerInfo.getPageIndex() > 0) {
        page = (page - 1) * rows;
      }
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("code", code);
      params.put("masterCode", masterCode);
      params.put("centerCode", centerCode);
      params.put("name", name);
      params.put("masterName", masterName);
      params.put("centerName", centerName);
      params.put("start", page);
      params.put("size", rows);

      int count = storagesRelaService.getListCountByParam(params);
      List<StoragesRela> list = storagesRelaService.getListByParam(params);

      json.put("rows", list);
      json.put("total", Long.valueOf(count));
    } catch (Exception e) {
      logger.error("查询【多层级关系数据】失败", e);
    }
    return json;
  }

  /**
   * 新增更新
   * @param id
   * @param centerStorage
   * @param masterStorage
   * @param storage
   * @param mulStore
   * @param isMaster
   * @return
   */
  @RequestMapping(value = { "/saveOrUpdate" }, method = { RequestMethod.POST})
  @ResponseBody
  public HttpJsonResult<Map<String, Object>> saveOrUpdate(
      @RequestParam(required = false) Integer id,
      @RequestParam(required = true) String centerStorage,
      @RequestParam(required = true) String masterStorage,
      @RequestParam(required = true) String storage,
      @RequestParam(required = true) String mulStore,
      @RequestParam(required = true) Integer isMaster) {
    HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
    try {

      StoragesRela storagesRela = new StoragesRela();
      storagesRela.setCenterCode(centerStorage);
      storagesRela.setMasterCode(masterStorage);
      storagesRela.setCode(storage);
      storagesRela.setMulStoreCode(mulStore);
      if (id == null) {
        int count = storagesRelaService.selectByCode(storagesRela);
        if (count > 0) {
          result.setMessage("已存在，请核对！");
          return result;
        }
        count = storagesRelaService.createStoragesRela(storagesRela);
        if (count == 0) {
          result.setMessage("新增失败");
          return result;
        }
      } else {
        storagesRela.setId(id);
        int count = storagesRelaService.updateStoragesRela(storagesRela);
        if (count == 0) {
          result.setMessage("更新失败！");
        }
      }
    } catch (Exception e) {
      logger.error("操作小家电多层级关系表发生异常", e);
      result.setMessage("维护信息时异常！");
    }
    return result;
  }
}