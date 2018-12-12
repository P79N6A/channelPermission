package com.haier.logistics.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.VomInOutStoreItem;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.eis.service.EisVomInOutStoreItemService;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.shop.service.ShopOrderRepairLogsService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 解析并保存VOM出入库记录
 * Created by 钊 on 2014/7/21.
 */
@Service
public class RRSOutInStoreVomContentHandler extends AbstractVomContentHandler {
	@Autowired
    private EisVomInOutStoreOrderService vomInOutStoreOrderDao;
	@Autowired
    private EisVomInOutStoreItemService  vomInOutStoreItemDao;
	
	@Autowired
	private ShopOrderRepairLogsService shopOrderRepairLogsService;//退货日志

//    public RRSOutInStoreVomContentHandler(String buType) {
//        super(buType);
//    }
	@Transactional
    protected void processContent(VomReceivedQueue vomReceivedQueue) throws BusinessException {
        String type = vomReceivedQueue.getType();
        String content = vomReceivedQueue.getContent();

        if (StringUtil.isEmpty(type)
            || (!"xml".equalsIgnoreCase(type) && !"Json".equalsIgnoreCase(type))) {
            setError(vomReceivedQueue.getId(), "不支持的报文格式" + type + "，支持的的格式为 xml|Json");
            return;
        }
        if (!"xml".equalsIgnoreCase(type)) {
            throw new BusinessException("暂时只支持xml格式的报文");
        } else {
            try {
                Document document = DocumentHelper.parseText(content);
                document.setXMLEncoding("utf-8");
                Element rootElement = document.getRootElement();
                Element itemsElement = rootElement.element("InOutItems");
                if (itemsElement == null) {
                    setError(vomReceivedQueue.getId(), "不存在<InOutItems>标签，数据错误");
                    return;
                }

                Date addTime = new Date();

                VomInOutStoreOrder vomInOutStoreOrder = parseContentOrder(rootElement);
                vomInOutStoreOrder.setReceivedId(vomReceivedQueue.getId());
                vomInOutStoreOrder.setProcessStatus(0);
                vomInOutStoreOrder.setDelay(0);
                vomInOutStoreOrder.setAddTime(addTime);
//                vomInOutStoreOrder.setId(shopOrderRepairLogsService.getNextValId());

                List<VomInOutStoreItem> vomInOutStoreItems = parseContentItems(itemsElement);
                String validFailedMsg;
                validFailedMsg = validOrder(vomInOutStoreOrder);
                if (!StringUtil.isEmpty(validFailedMsg)) {
                    setError(vomReceivedQueue.getId(), validFailedMsg);
                    return;
                }
                validFailedMsg = validItems(vomInOutStoreItems);
                if (!StringUtil.isEmpty(validFailedMsg)) {
                    setError(vomReceivedQueue.getId(), validFailedMsg);
                    return;
                }

                ServiceResult<VomInOutStoreOrder> resultOrder =  vomInOutStoreOrderDao.insert(vomInOutStoreOrder);
                for (VomInOutStoreItem vomInOutStoreItem : vomInOutStoreItems) {
                    vomInOutStoreItem.setOrderId(resultOrder.getResult().getId());
                    vomInOutStoreItem.setProcessStatus(0);
                    vomInOutStoreItem.setAddTIme(addTime);
                    vomInOutStoreItemDao.insert(vomInOutStoreItem);
                }
            } catch (DocumentException e) {
                if (e.getNestedException() instanceof SAXParseException) {
                    setError(vomReceivedQueue.getId(), "Xml报文格式错误，无法解析:" + e.getMessage());
                    return;
                }
                throw new BusinessException("解析Xml报文出现错误：" + e.getMessage());
            }

        }
    }

    private String validOrder(VomInOutStoreOrder vomInOutStoreOrder) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtil.isEmpty(vomInOutStoreOrder.getOrderNo())) {
            stringBuilder.append("业务订单号[orderno]不能为空|");
        }
        if (StringUtil.isEmpty(vomInOutStoreOrder.getExpNo())) {
            stringBuilder.append("运单号[expno]不能为空|");
        }
        if (vomInOutStoreOrder.getBusType() == null
            || (vomInOutStoreOrder.getBusType() != 1 && vomInOutStoreOrder.getBusType() != 2)) {
            stringBuilder.append("业务类型[bustype]不合法，传入值 '").append(vomInOutStoreOrder.getBusType())
                .append("',合法值‘1-出库,2-入库'|");
        }
        if (vomInOutStoreOrder.getOrderType() == null) {
            stringBuilder.append("订单类型为空，或不为数字|");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private String validItems(List<VomInOutStoreItem> vomInOutStoreItems) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (VomInOutStoreItem vomInOutStoreItem : vomInOutStoreItems) {
            index++;
            if (StringUtil.isEmpty(vomInOutStoreItem.getItemNo())) {
                sb.append("第").append(index).append("个Item：行号[itemno]不能为空|");
            }
            if (StringUtil.isEmpty(vomInOutStoreItem.getStorageType())) {
                sb.append("第").append(index).append("个Item：批次[storagetype]不能为空|");
            }
            if (StringUtil.isEmpty(vomInOutStoreItem.getHrCode())) {
                sb.append("第").append(index).append("个Item：海尔产品编码[hrcode]不能为空|");
            }
            if (vomInOutStoreItem.getNumber() == null || vomInOutStoreItem.getNumber() <= 0) {
                sb.append("第").append(index).append("个Item：数量[number]不合法，必须是大于0的数字|");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    private VomInOutStoreOrder parseContentOrder(Element rootElement) {
        VomInOutStoreOrder vomInOutStoreOrder = new VomInOutStoreOrder();
        vomInOutStoreOrder.setOrderNo(rootElement.elementTextTrim("orderno"));
        vomInOutStoreOrder.setExpNo(rootElement.elementTextTrim("expno"));
        vomInOutStoreOrder.setBusType(ConvertUtil.toInt(rootElement.elementTextTrim("bustype"),
            null));
        vomInOutStoreOrder.setOrderType(ConvertUtil.toInt(rootElement.elementTextTrim("ordertype"),
            0));
        vomInOutStoreOrder.setOutInDate(DateUtil.parse(rootElement.elementText("outindate"),
            "yyyy-MM-dd HH:mm:ss"));
        vomInOutStoreOrder.setStoreCode(rootElement.elementTextTrim("storecode"));
        vomInOutStoreOrder.setIsComplete(ConvertUtil.toInt(
            rootElement.elementTextTrim("iscomplete"), 1));
        vomInOutStoreOrder.setRemark(rootElement.elementTextTrim("remark"));
        vomInOutStoreOrder.setRemark1(rootElement.elementTextTrim("remark1"));
        vomInOutStoreOrder.setRemark2(rootElement.elementTextTrim("remark2"));
        vomInOutStoreOrder.setRemark3(rootElement.elementTextTrim("remark3"));
        vomInOutStoreOrder.setCertification(rootElement.element("attributes") == null ? null
            : rootElement.element("attributes").elementTextTrim("certification"));
        return vomInOutStoreOrder;
    }

    private List<VomInOutStoreItem> parseContentItems(Element itemsElement) {
        List<VomInOutStoreItem> items = new ArrayList<VomInOutStoreItem>();
        Iterator iterator = itemsElement.elementIterator();
        while (iterator.hasNext()) {
            Element itemElement = (Element) iterator.next();
            VomInOutStoreItem item = new VomInOutStoreItem();
            item.setItemNo(itemElement.elementTextTrim("itemno"));
            item.setStorageType(itemElement.elementTextTrim("storagetype"));
            item.setProductCode(itemElement.elementTextTrim("productcode"));
            item.setHrCode(itemElement.elementTextTrim("hrcode"));
            item.setProDes(itemElement.elementTextTrim("prodes"));
            item.setNumber(ConvertUtil.toInt(itemElement.elementTextTrim("number"), null));
            item.setIsComplete(ConvertUtil.toInt(itemElement.elementTextTrim("iscomplete"), 0));
            item.setVolume(itemElement.elementTextTrim("volume"));
            item.setWeight(itemElement.elementTextTrim("weight"));
            item.setRemark(itemElement.elementTextTrim("remark"));
            item.setRemark1(itemElement.elementTextTrim("remark1"));
            item.setRemark2(itemElement.elementTextTrim("remark2"));
            items.add(item);
        }
        return items;
    }

	@Override
	public String getBuType() {
		// TODO Auto-generated method stub
		return null;
	}

}
