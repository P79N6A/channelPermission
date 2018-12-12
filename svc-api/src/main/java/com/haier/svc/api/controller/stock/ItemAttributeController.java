package com.haier.svc.api.controller.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haier.svc.api.controller.stock.mode.ItemAttributeModel;
import com.haier.svc.api.controller.util.HttpJsonResult;

@Controller
@RequestMapping("itemAttribute")
public class ItemAttributeController {
	@Autowired
    private ItemAttributeModel itemAttributeModel;

    /**
     * 查询所有品类
     * @return
     */
    @RequestMapping(value = { "/getAllProductTypes" }, method = { RequestMethod.GET })
    public @ResponseBody
    HttpJsonResult<List<String>> getProductTypes() {
        HttpJsonResult<List<String>> result = new HttpJsonResult<List<String>>();
        List<String> productTypes = itemAttributeModel.getProductTypes();
        if (productTypes == null) {
            productTypes = new ArrayList<String>();
        }
        productTypes.add(0, "全部");
        result.setData(productTypes);
        return result;
    }
}
