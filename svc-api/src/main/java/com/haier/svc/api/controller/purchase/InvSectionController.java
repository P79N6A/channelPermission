package com.haier.svc.api.controller.purchase;

import com.haier.stock.model.InvSection;
import com.haier.svc.api.controller.util.excel.StockExcelHandler;
import com.haier.svc.service.InvSectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/invSection")
@Controller
public class InvSectionController {
    @Resource
    private InvSectionService invSectionService;

    @GetMapping("toPage")
    public String toPage() {
        return "purchase/invSection";
    }

    /**
     * 分页查询
     */
    @GetMapping("queryPage")
    @ResponseBody
    public Object queryPage(InvSection param) {
        return invSectionService.getInvSectionList(param);
    }

    /**
     * 导出
     */
    @GetMapping("export")
    @ResponseBody
    public void export(InvSection param, HttpServletResponse response) {
        List<InvSection> data = invSectionService.queryInvSectionExcel(param);
        StockExcelHandler eh = new StockExcelHandler(InvSection.class);
        eh.setData(data);
        eh.exportxls(response);
    }
}
