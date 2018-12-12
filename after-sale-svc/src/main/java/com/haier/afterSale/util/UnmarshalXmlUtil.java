package com.haier.afterSale.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.alibaba.dubbo.remoting.exchange.Response;



public class UnmarshalXmlUtil {

    @SuppressWarnings("unchecked")
    public static <E> E unmarshalXml(String xml, Class<?> cla) throws Exception {
        JAXBContext context = JAXBContext.newInstance(cla);
        Unmarshaller unMarshaller = context.createUnmarshaller();
        StringReader stringReader = new StringReader(xml);
        Object unmarshal = null;
        try {
        	unmarshal = unMarshaller.unmarshal(stringReader);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(stringReader!=null){
				stringReader.close();
			}
		}
        return (E) unmarshal;
    }

    public static Response analyzeEInvoiceResponse(String xml) throws Exception {
        return unmarshalXml(xml, Response.class);

    }
}
