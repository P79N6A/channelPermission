package com.haier.vehicle.util;

/**
 * <p>Description: </p>
 * ClassName:awsdf
 * Created on 2016/4/26
 *
 * @author wsh
 * @version 1.0
 *          Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>Description: [对象类互相转换工具类]</p>
 * Created on 2015年10月10日
 *
 * @author mgl
 * @version 1.0
 *          Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
public class ConversionClassUtil {

	private static Logger log = LoggerFactory.getLogger(ConversionClassUtil.class);

    /**
     * <p>Discription:[复制源对象和目标对象的属性值]</p>
     * Created on 2015年10月10日
     *
     * @param source 源对象
     * @param target 目标对象
     * @author:[mgl]
     */
    public static void conversion(Object source, Object target) {
        try {
            Class sourceClass = source.getClass();//得到对象的Class
            Class targetClass = target.getClass();//得到对象的Class

            Field[] sourceFields = sourceClass.getDeclaredFields();//得到Class对象的所有属性
            Field[] targetFields = targetClass.getDeclaredFields();//得到Class对象的所有属性

            for (Field sourceField : sourceFields) {

                String name = sourceField.getName();//属性名
                Class type = sourceField.getType();//属性类型
                if ("serialVersionUID".equals(name)) {
                    continue;
                }
                String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);

                Method getMethod = sourceClass.getMethod("get" + methodName);//得到属性对应get方法

                Object value = getMethod.invoke(source);//执行源对象的get方法得到属性值

                for (Field targetField : targetFields) {
                    String targetName = targetField.getName();//目标对象的属性名

                    if (targetName.equals(name)) {
                        Method setMethod = targetClass.getMethod("set" + methodName, type);//属性对应的set方法

                        setMethod.invoke(target, value);//执行目标对象的set方法
                    }
                }
            }
        } catch (Exception e) {
        	log.error(e.getLocalizedMessage(), e);
        }
    }
}

