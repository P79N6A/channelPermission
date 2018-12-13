package com.haier.svc.api.controller.purchase;

import com.haier.stock.model.StorageLocMgt;
import com.haier.svc.api.controller.util.excel.ExcelHandler;
import com.haier.svc.api.controller.util.excel.StockExcelHandler;
import com.haier.svc.service.StorageLocMgtService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/storageLocMgtModel")
@Controller
public class StorageLocMgtController {

    @GetMapping("toPage")
    public String toPage() {
        return "purchase/storageWareHouse";
    }
    @Resource
    private StorageLocMgtService storageLocMgtService;
    /**
     * 分页查询
     */
    @GetMapping("queryPage")
    @ResponseBody
    public Object queryPage(StorageLocMgt param) {
        return storageLocMgtService.queryStorageLocMgt(param);
    }

    /**
     * 导出
     */
    @GetMapping("export")
    @ResponseBody
    public void export(StorageLocMgt param, HttpServletResponse response) {
        List<StorageLocMgt> data = storageLocMgtService.queryStorageLocMgtExcel(param);
        StockExcelHandler eh = new StockExcelHandler(StorageLocMgt.class);
        eh.setData(data);
        eh.exportxls(response);
    }
}
