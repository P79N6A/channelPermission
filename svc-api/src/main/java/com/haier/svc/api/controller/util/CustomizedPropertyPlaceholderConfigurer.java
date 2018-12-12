package com.haier.svc.api.controller.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {
    private static Map<String, Object> ctxPropertiesMap;
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                     Properties props) throws BeansException {
        // TODO Auto-generated method stub
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);

        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }
    public static Object getContextProperties(String name) {
        if (ctxPropertiesMap == null) {
            return null;
        }
        return ctxPropertiesMap.get(name);
    }
}
