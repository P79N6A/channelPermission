package com.haier.svc.api.controller.purchase;


import com.haier.shop.model.ItemAttribute;
import com.haier.svc.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author: zhenshuoxin
 * @description:
 * @date:created in 2018/7/06 15:28
 * @modificed by:
 */
@RequestMapping(value = "/item")
@Controller
public class ItemAttributeManageController {

    @Resource
    private ItemService itemService;


    @GetMapping("toPage")
    public String toPage() {
        return "purchase/itemAttributeFilter";
    }

    /**
     * 分页查询
     */
    @GetMapping("queryPage")
    @ResponseBody
    public Object queryPage(ItemAttribute param) {
        return itemService.queryItemAttribute(param);
    }
}
