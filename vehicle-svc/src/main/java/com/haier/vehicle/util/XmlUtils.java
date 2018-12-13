package com.haier.vehicle.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.google.common.collect.Lists;

@SuppressWarnings("all")
public class XmlUtils {
	private static final Logger log = LogManager.getLogger(XmlUtils.class);
	private static final String ENCODEING = "UTF-8";

	/**
	 * 字符串转Document对象 字符串先编码为UTF-8，在转换为Dom4j的Document对象
	 * @param xmlStr
	 * @return
	 */
	public static Document parseDocument(String xmlStr) {
		Document document = null;
		try { 
			document = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			log.info("XmlUtils类 字符串转Document异常.");
			e.printStackTrace();
		}
		return document;
	}

	public static String bean2XML(Object obj) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext ctx = JAXBContext.newInstance(obj.getClass());
			Marshaller mar = ctx.createMarshaller();
			mar.marshal(obj, os);
			os.flush();
			String xml = new String(os.toByteArray(), "UTF-8");
			os.close();
			return xml;
		} catch (JAXBException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
 
	/**
	 * xml字符串转换成bean对象
	 * @param xmlStr  xml字符串
	 * @param clazz  待转换的class
	 * @return 转换后的对象
	 */
	public static Object xmlStrToBean(String xmlStr, Class clazz) {
		Object obj = null;
		try {
			// 将xml格式的数据转换成Map对象
			Map<String, Object> map = xmlStrToMap(xmlStr);
			// 将map对象的数据转换成Bean对象
			obj = mapToBean(map, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 将xml格式的字符串转换成Map对象
	 * @param xmlStr  xml格式的字符串
	 * @return Map对象
	 * @throws Exception 异常
	 */
	public static Map<String, Object> xmlStrToMap(String xmlStr)
			throws Exception {
		if (xmlStr == null || "".equals(xmlStr)) {
			return null;
		} 
		Map<String, Object> map = new HashMap<String, Object>();
		// 将xml格式的字符串转换成Document对象
		Document doc = DocumentHelper.parseText(xmlStr);
		// 获取根节点
		Element root = doc.getRootElement();
		// 获取根节点下的所有元素
		List children = root.elements();
		// 循环所有子元素
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				// 判断是否有子节点
				if (child.elementIterator().hasNext()) {
					map.put(child.getName(), child.asXML());
				} else {
					map.put(child.getName(), child.getTextTrim());
				}
			}
		}
		return map;
	}

	/**
	 * 将Map对象通过反射机制转换成Bean对象
	 * @param map  存放数据的map对象
	 * @param clazz 待转换的class
	 * @return 转换后的Bean对象
	 * @throws Exception 异常
	 */ 
	public static Object mapToBean(Map<String, Object> map, Class clazz) throws Exception {  
        Object obj = clazz.newInstance();  
        if(map != null && map.size() > 0) {  
            for(Map.Entry<String, Object> entry : map.entrySet()) {  
                String propertyName = entry.getKey();  
                Object value = entry.getValue();  
                String setMethodName = "set"  
                        + propertyName.substring(0, 1).toUpperCase()  
                        + propertyName.substring(1);  
                Field field = getClassField(clazz, propertyName);  
                if(field==null){continue;}
                Class fieldTypeClass = field.getType();
                value = convertValType(value, fieldTypeClass); 
                //基本类型集合
                List<String> classTypes=Lists.newArrayList("java.lang.String","java.lang.Integer",
                		"java.lang.Double","java.util.Date","java.lang.Float","java.lang.Boolean"); 
                if(classTypes.contains(field.getType().getName())){
                	clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
                }else  if(field.getType()==List.class){ 
                 	ParameterizedType pt = (ParameterizedType)field.getGenericType();  
                	Document doc = DocumentHelper.parseText(value.toString()); 
                	List list=new ArrayList();  
                	for (Element el : (List<Element>)doc.selectNodes("//*//"+propertyName.substring(0,propertyName.length()-1))) { 
                		list.add(xmlStrToBean(el.asXML(),(Class)pt.getActualTypeArguments()[0]));
					}  
                	clazz.getMethod(setMethodName, field.getType()).invoke(obj,list);
                }else{ 
                	clazz.getMethod(setMethodName, field.getType()).invoke(obj, xmlStrToBean(value.toString(), fieldTypeClass));
                } 
            }  
        }  
        return obj;  
    }
	/**
	 * 将Object类型的值，转换成bean对象属性里对应的类型值
	 * @param value  Object对象值
	 * @param fieldTypeClass  属性的类型
	 * @return 转换后的值
	 */
	private static Object convertValType(Object value, Class fieldTypeClass) {
		Object retVal = null;
		if (Long.class.getName().equals(fieldTypeClass.getName())
				|| long.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Long.parseLong(value.toString());
		} else if (Integer.class.getName().equals(fieldTypeClass.getName())
				|| int.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Integer.parseInt(value.toString());
		} else if (Float.class.getName().equals(fieldTypeClass.getName())
				|| float.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Float.parseFloat(value.toString());
		} else if (Double.class.getName().equals(fieldTypeClass.getName())
				|| double.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Double.parseDouble(value.toString());
		} else {
			retVal = value;
		}
		return retVal;
	}

	/**
	 * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
	 * @param clazz  指定的class
	 * @param fieldName   字段名称
	 * @return Field对象
	 */
	private static Field getClassField(Class clazz, String fieldName) {
		if (Object.class.getName().equals(clazz.getName())) {
			return null;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		Class superClass = clazz.getSuperclass();
		if (superClass != null) {// 简单的递归一下
			return getClassField(superClass, fieldName);
		}
		return null;
	}
	
	/**
	 * 将list的xml转为list
	 * @param xml
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object xmlStrToList(String xml, Class cls) throws Exception {
		List<Object> lists = new ArrayList<>();
		Document doc = DocumentHelper.parseText(xml);
		Element et = doc.getRootElement();
		String root = et.getName();
		// 查看返回码是否为真.
		
		Iterator<Element> it = et.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            Object obj = xmlStrToBean(e.asXML(), cls);
            // 对子节点进行遍历  
            lists.add(obj);
        }
		return lists;
	}

	/**
	 * 格式化XML文档,并解决中文问题
	 * @param filename
	 * @return
	 */
	public int formatXMLFile(String filename, String encoding) {
		int returnValue = 0;
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(filename));
			XMLWriter writer = null;
			/** 格式化输出,类型IE浏览一样 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML编码 */
			format.setEncoding(encoding);
			writer = new XMLWriter(new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
			/** 执行成功,需返回1 */
			returnValue = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}
}
