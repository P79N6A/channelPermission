package com.haier.order.util;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 方便转换xml实体类与字符串互相转换.
 * 工具类
 * @author
 */
public class XmlUtil {

    //xml字符串转化成对象
    public static Object jAXBunmarshalString(String xml, Class<?> type) throws JAXBException {
    	StringReader stringReader = new StringReader(xml);
    	Object unmarshal = null;
    	try {
    		unmarshal = JAXB.unmarshal(stringReader, type);
		} finally {
			if(stringReader!=null){
				stringReader.close();
			}
		}
        return unmarshal;
    }

    //对象转化成xml字符串
    public static String jAXBmarshalString(Object obj) {
        StringWriter stringWriter = new StringWriter();
        try{
        	JAXB.marshal(obj, stringWriter);
        	return stringWriter.toString();
        }finally{
        	try {
        		if(stringWriter!=null){
        			stringWriter.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    //xml文件转化成对象
    public static Object jAXBunmarshal(File xmlFile, Class<?> type) throws JAXBException {
        return JAXB.unmarshal(xmlFile, type);
    }

    //对象转化成xml文件，可以指定成outputstream,writer,string，可以参考API   
    //	public static void jAXBmarshal(Object obj, File fRootDir) {
    //		if (!fRootDir.exists()) {
    //			fRootDir.mkdirs();
    //		}
    //		JAXB.marshal(obj, new File(fRootDir, "test.xml"));
    //	}

}
