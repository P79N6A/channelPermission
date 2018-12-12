package com.haier.svc.api.controller.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertiesUtil {
    private static Logger LOGGER = LogManager.getLogger(PropertiesUtil.class);

    public static String getProperties(String name) {

        Object value = CustomizedPropertyPlaceholderConfigurer.getContextProperties(name);
        if (value == null) {
            LOGGER.warn("没有找到在资源文件中找到对应的key[" + name + "]");
            return "";
        }
        return value.toString();
    }
}
