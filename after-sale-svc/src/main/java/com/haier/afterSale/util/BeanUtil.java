package com.haier.afterSale.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {
    private static org.apache.log4j.Logger log     = org.apache.log4j.LogManager
                                                       .getLogger(BeanUtil.class);

    private static ApplicationContext      context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("BeanUtil成功设置了ApplicationContext");
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        if (context == null)
            return null;
        return context.getBean(beanName);
    }

}