package com.haier.stock.util;

import java.text.MessageFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MessageFormater {
    private static Logger LOGGER = LogManager.getLogger(MessageFormater.class);

    public static String formate(String key) {

        Object value = CustomizedPropertyPlaceholderConfigurer.getContextProperties(key);
        if (value == null) {
            LOGGER.warn("没有在资源文件中找到对应的key[" + key + "]");
            return "";
        }
        return value.toString();
    }

    public static String formate(String key, Object... args) {
        Object value = CustomizedPropertyPlaceholderConfigurer.getContextProperties(key);
        if (value == null) {
            LOGGER.warn("没有在资源文件中找到对应的key[" + key + "]");
            return "";
        }
        return MessageFormat.format(value.toString(), args);
    }
}
