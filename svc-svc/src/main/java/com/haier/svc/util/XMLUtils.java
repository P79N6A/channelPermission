package com.haier.svc.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

public class XMLUtils<T> {
	
	/**
	 * 把对象转换为xml类型的字符串
	 * @param t
	 * @return
	 */
	public String beanToXML(T t, Class<T> cla) {
    	String xml = "";
        try {  
            JAXBContext context = JAXBContext.newInstance(cla);
            Marshaller marshaller = context.createMarshaller();
            OutputStream out = new ByteArrayOutputStream();
            marshaller.marshal(t, out);   
            xml = out.toString();
        } catch (JAXBException e) {
            e.printStackTrace();  
        }  
        return xml;
    }  
	
	/**
	 * 把xml格式的字符串转换为对应的数据对象
	 * @param xml
	 * @param cla
	 * @return
	 */
	public Object XMLStringToBean(String xml, Class<T> cla){
		Object o = new Object();
	    try {  
		    JAXBContext context = JAXBContext.newInstance(cla);
		    Unmarshaller unmarshaller = context.createUnmarshaller();
			T response = (T)unmarshaller.unmarshal(new StringReader(xml));
		    o = response;
		} catch (JAXBException e) {
		    e.printStackTrace();  
		}  
		return o;
	}  

}
