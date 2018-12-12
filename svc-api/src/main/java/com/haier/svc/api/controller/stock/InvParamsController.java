package com.haier.svc.api.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvParam;
import com.haier.svc.api.controller.stock.mode.InvParamsModel;
import com.haier.svc.api.controller.util.HttpJsonResult;

@Controller
@RequestMapping("invParams")
public class InvParamsController {
	@Autowired
    InvParamsModel invParamsModel;

    @RequestMapping(value = { "/getParams" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<InvParam>> getParams(HttpServletRequest request) {
        HttpJsonResult<List<InvParam>> result = new HttpJsonResult<List<InvParam>>();
        String group = request.getParameter("group");
        if (StringUtil.isEmpty(group)) {
            return result;
        }
        List<InvParam> groupChannelAge = invParamsModel.queryParams(group);
        result.setData(groupChannelAge);
        return result;
    }
}
