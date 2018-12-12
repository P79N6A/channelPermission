package com.haier.logistics.services;

import com.haier.common.BusinessException;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.eis.model.VomShippingStatus;
import com.haier.eis.service.EisVomShippingStatusService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXParseException;

import java.util.Date;

/**
 * 解析rrs_statucback报文
 * Created by 钊 on 2014/7/28.
 */
@Service
public class RrsStatusBackVomContentHandler extends AbstractVomContentHandler {
	@Autowired
    private EisVomShippingStatusService vomShippingStatusDao;

    @Override
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
        }
        try {
            Document document = DocumentHelper.parseText(content);
            document.setXMLEncoding("utf-8");
            Element rootElement = document.getRootElement();
            VomShippingStatus vomShippingStatus = parseContent(rootElement);
            vomShippingStatus.setReceivedId(vomReceivedQueue.getId());
            String validMsg = valid(vomShippingStatus);
            if (!StringUtil.isEmpty(validMsg)) {
                setError(vomReceivedQueue.getId(), validMsg);
                return;
            }
            vomShippingStatus.setAddTime(new Date());
            vomShippingStatusDao.insert(vomShippingStatus);
        } catch (DocumentException e) {
            if (e.getNestedException() instanceof SAXParseException) {
                setError(vomReceivedQueue.getId(), "Xml报文格式错误，无法解析:" + e.getMessage());
                return;
            }
            throw new BusinessException("解析Xml报文出现错误：" + e.getMessage());
        }

    }

    private String valid(VomShippingStatus vomShippingStatus) {
        StringBuilder sb = new StringBuilder();

        if (StringUtil.isEmpty(vomShippingStatus.getStoreCode())) {
            sb.append("日日顺仓库编码[storecode]不能为空|");
        }
        if (StringUtil.isEmpty(vomShippingStatus.getOrderNo())) {
            sb.append("业务单号[orderno]不能为空|");
        }
        if (vomShippingStatus.getExpNo() == null) {
            vomShippingStatus.setExpNo("");
        }
        if (StringUtil.isEmpty(vomShippingStatus.getOperator())) {
            vomShippingStatus.setOperator("");
        }
        if (StringUtil.isEmpty(vomShippingStatus.getOperContact())) {
            vomShippingStatus.setOperContact("");
        }
        if (vomShippingStatus.getOperDate() == null) {
            sb.append("操作时间[operdate]无法解析|");
        }
        if (StringUtil.isEmpty(vomShippingStatus.getStatus())) {
            sb.append("状态[status]不能为空|");
        }
        if (vomShippingStatus.getContent() == null) {
            vomShippingStatus.setContent("");
        }
        if (vomShippingStatus.getRemark() == null) {
            vomShippingStatus.setRemark("");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private VomShippingStatus parseContent(Element rootElement) {
        VomShippingStatus vomShippingStatus = new VomShippingStatus();
        vomShippingStatus.setStoreCode(rootElement.elementTextTrim("storecode"));
        vomShippingStatus.setOrderNo(rootElement.elementTextTrim("orderno"));
        vomShippingStatus.setExpNo(rootElement.elementTextTrim("expno"));
        vomShippingStatus.setOperator(rootElement.elementTextTrim("operator"));
        vomShippingStatus.setOperContact(rootElement.elementTextTrim("opercontact"));
        vomShippingStatus.setOperDate(DateUtil.parse(rootElement.elementTextTrim("operdate"),
            "yyyy-MM-dd HH:mm:ss"));
        vomShippingStatus.setStatus(rootElement.elementTextTrim("status"));
        vomShippingStatus.setContent(rootElement.elementTextTrim("content"));
        vomShippingStatus.setRemark(rootElement.elementTextTrim("remark"));
        vomShippingStatus.setProcessStatus(0);
        return vomShippingStatus;
    }

	@Override
	public String getBuType() {
		// TODO Auto-generated method stub
		return null;
	}

}
