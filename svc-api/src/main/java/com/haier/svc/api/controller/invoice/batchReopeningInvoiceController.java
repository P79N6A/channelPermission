package com.haier.svc.api.controller.invoice;

import com.haier.afterSale.model.Ustring;
import com.haier.common.ServiceResult;
import com.haier.eop.data.model.OrderProducts;
import com.haier.shop.model.InvoiceQueue;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.service.InvoiceQueueService;
import com.haier.shop.service.ShopOperationAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "invoice/")
public class batchReopeningInvoiceController {

    @Autowired
    private ShopOperationAreaService shopOperationAreaService;
    @Autowired
    private InvoiceQueueService invoiceQueueService;
    @ResponseBody
    @RequestMapping("batchReopeningInvoice")
    public ModelAndView batchReopeningInvoice() {
        ModelAndView mv  = new ModelAndView();
        mv.setViewName("invoice/batchReopeningInvoice");
        return mv;
    }
    @ResponseBody
    @PostMapping("doBatchReopeningInvoice")
    public ServiceResult<Map<String, Object>> doDatchReopeningInvoice(String cOrderSns, HttpServletRequest request, HttpServletResponse response){
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        String[] split = cOrderSns.replace(" ", "").split("\n");
        List<String> lists = java.util.Arrays.asList(split);
        StringBuffer sb = new StringBuffer();
        List<Integer> orderProductIds = new ArrayList<>();
        for (String s : lists) {
           // 先确认有没有这个网单 然后找出订单id
            OrderProductsVo orderProductsVo = shopOperationAreaService.selectIdAndIsMakeReceiptBycOrderSn(s);
            if (orderProductsVo != null){
                int id = orderProductsVo.getId();
                int invoiceQueueExistFlag = invoiceQueueService.getInvoiceQueueExistFlag(id);
                if (invoiceQueueExistFlag == 0){
                    sb.append("网单号:" + s + "更新失败<br>");
                }else {
                    if (orderProductsVo.getIsMakeReceipt() == 3 || orderProductsVo.getIsMakeReceipt() == 4) {
                        List<InvoiceQueue> byOrderProductId = invoiceQueueService.getByOrderProductId(id);
                        Integer success = byOrderProductId.get(0).getSuccess();
                        if (success == 0) {
                            sb.append("网单号:" + s + "已经重新开票了<br>");
                        } else {
                            orderProductIds.add(id);
                        }
                    }else {
                        if (orderProductsVo.getIsMakeReceipt() == 1){
                            sb.append("网单号:" + s + "开票状态为:未开票<br>");
                        }else if (orderProductsVo.getIsMakeReceipt() == 9){
                            sb.append("网单号:" + s + "开票状态为:待开票<br>");
                        }else if (orderProductsVo.getIsMakeReceipt() == 5){
                            sb.append("网单号:" + s + "开票状态为:开票中<br>");
                        }else if (orderProductsVo.getIsMakeReceipt() == 6){
                            sb.append("网单号:" + s + "开票状态为:开票失败<br>");
                        }else if (orderProductsVo.getIsMakeReceipt() == 2){
                            sb.append("网单号:" + s + "开票状态为:已开票<br>");
                        }else if (orderProductsVo.getIsMakeReceipt() == 10){
                            sb.append("网单号:" + s + "开票状态为:取消开票<br>");
                        }else if (orderProductsVo.getIsMakeReceipt() == 20){
                            sb.append("网单号:" + s + "开票状态为:期初数据封存<br>");
                        }else {
                            sb.append("网单号:" + s + "开票状态为:未知<br>");
                        }
                    }
                }
            }else {
                sb.append("网单号:" + s + "不存在<br>");
            }
        }
        if (orderProductIds.size() != 0) {
            Integer integer = invoiceQueueService.updateInvoiceQueueSuccessByOrderProductIds(orderProductIds);
            result.setMessage("批量重新开票成功共有:"+ integer + "条<br>" + "批量重新开票失败共有:" + (lists.size() - integer) + "条<br>" +
                    sb.toString());
        }else {
            result.setMessage("批量重新开票成功共有:"+ 0 + "条<br>" + "批量重新开票失败共有:" + (lists.size() - 0) + "条<br>" +
                    sb.toString());
        }
        return result;
    }
}
