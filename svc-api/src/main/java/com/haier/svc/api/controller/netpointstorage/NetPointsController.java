package com.haier.svc.api.controller.netpointstorage;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.shop.dto.RegionsDTO;
import com.haier.shop.model.NetPoints;
import com.haier.shop.service.AddRessDataService;
import com.haier.shop.service.NetPointsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Author:JinXueqian
 * @Date: 2018/7/16 16:01
 */
@Controller
@RequestMapping("netpoint/")
public class NetPointsController {

    private static final Logger logger = LogManager.getLogger(NetPointsController.class);

    @Autowired
    NetPointsService netPointsService;

    @Autowired
    private AddRessDataService addRessDataService;
    /**
     * 网点列表页面跳转
     *
     * @return
     */
    @RequestMapping(value = {"loadNetPointListPage.html"}, method = {RequestMethod.GET})
    public String loadNetPointListPage() {
        return "netpointstorage/netpointList";
    }


    /**
     * 分页查询
     * @param response
     * @param netPointN8
     * @param netPointCode
     * @param netPointName
     * @param rows
     * @param page
     */
    @RequestMapping(value = {"getNetPointsList.html"}, method = {RequestMethod.POST})
    public void getNetPointsList(HttpServletResponse response,
                                 @RequestParam(required = false) String netPointN8,
                                 @RequestParam(required = false) String netPointCode,
                                 @RequestParam(required = false) String netPointName,
                                 @RequestParam(required = false) Integer rows,
                                 @RequestParam(required = false) Integer page) {

        try {
            if (rows == null){
                rows = 50;
            }
            if (page == null){
                page = 1;
            }
            Map<String, Object> params = new HashMap();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("netPointN8", netPointN8);
            params.put("netPointCode", netPointCode);
            params.put("netPointName", netPointName);

            Map<String, Object> retMap = netPointsService.getNetPointsList(params);
            response.setCharacterEncoding("UTF-8");
            String json = JSON.toJSONString(retMap);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("网点列表失败", e);
        }
    }

    /**
     * 添加网点页面跳转
     * @return
     */
    @RequestMapping(value = {"netPointsAdd"})
    public String netPointsAdd() {
        return "netpointstorage/netPointsAdd";
    }

    /**
     * 保存网点
     * @param request
     * @return
     */
    @RequestMapping(value = {"saveNetPointInfo"},method = {RequestMethod.POST})
    @ResponseBody
    public String onSaveOrderInfo(HttpServletRequest request, HttpServletResponse response,NetPoints netPoints,  @RequestParam(required = false,defaultValue = "")String jingDu
                                                        ,@RequestParam(required = false,defaultValue = "")String weiDu){

        JSONObject json = new JSONObject();

        try {
            HttpSession session = request.getSession();
            //设置添加时间
            netPoints.setAddTime((int)(System.currentTimeMillis()/1000));
            netPoints.setSiteId(1);
            netPoints.setSalt("");
            netPoints.setCoordinate(jingDu+"|"+weiDu);
            Object password = session.getAttribute("password");
            if( password != null){
                netPoints.setPassword(password.toString());
            }else {
                netPoints.setPassword("");
            }

            if(netPoints.getContactMobile() == null){
                netPoints.setContactMobile("");
            }
            if(netPoints.getPhoneNumber() == null){
                netPoints.setPhoneNumber("");
            }
            if(netPoints.getContactEmail() == null){
                netPoints.setContactEmail("");
            }
            if(netPoints.getAreaCode() == null){
                netPoints.setAreaCode(0);
            }

            NetPoints netPointsbyNetPointByCode = netPointsService.getByNetPointByCode(netPoints.getNetPointCode());
            NetPoints existNetPoints = netPointsService.getByNetPointN8(netPoints.getNetPointN8());

            if(netPointsbyNetPointByCode != null){
                //网点代码已经存在
                json.put("flag",5);
                return json.toString();
            }
            if( existNetPoints != null){
                //8码已经存在
                json.put("flag",4);
                return json.toString();
            }
            //验证网点代码和8码都不存在,可以保存网单.
            Integer  result  = netPointsService.insert(netPoints);
            if (result > 0 ){
            json.put("flag",1);
            return json.toString();
        }else {
            json.put("flag",2);
            return json.toString();
        }
        }catch (Exception e){
         e.printStackTrace();
         logger.error("添加失败");
        }
        json.put("flag",3);
        return json.toString();
    }


    /**
     * 查询区域
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"getRegions"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getRegions2(String parentId ,HttpServletRequest request, HttpServletResponse response){
        List<RegionsDTO> list;
        if(StringUtils.isEmpty(parentId)){
            parentId = "";
            list = addRessDataService.getRegionsParentId(parentId);
        }else{
            list = addRessDataService.getRegionsParentId(parentId);
        }

        JSONObject json=new JSONObject();
        json.put("rows", list);
        return json;
    }
    /**
     * 删除网点
     * @param
     * @return
     */
    @RequestMapping(value = "deleteNetPoints" ,method = {RequestMethod.GET})
    @ResponseBody
    public String deleteCostPool (@RequestParam(required = false)Integer id){
        JSONObject json = new JSONObject();
        try {
            int rows = netPointsService.deleteByPrimaryKey(id);
            if(rows > 0){
                json.put("flag",1);
                    return json.toString();
            }else {
                json.put("flag",2);
                return json.toString();
            }

        } catch (Exception e) {
            logger.error("删除失败:网点id"+id+e.getMessage());
        }
        json.put("flag",3);
        return json.toString();
    }
}
