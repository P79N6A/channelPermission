package com.haier.svc.api.controller.util.intercept;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 李超 on 2018/2/24.
 */
@Component
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/**").excludePathPatterns("/login/toLogin");
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new InterceptorConfig());
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/login/toLogin");
        interceptorRegistration.excludePathPatterns("/pageload/accordion");
//        interceptorRegistration.excludePathPatterns("/pageload/model");
//        interceptorRegistration.excludePathPatterns("/pageload/getMenuById");
        interceptorRegistration.excludePathPatterns("/vomInformationReceiver/receive**");
        interceptorRegistration.excludePathPatterns("/acceptTimeController/accept_time**");
        interceptorRegistration.excludePathPatterns("/hp/hpallot_netpoint**");
        interceptorRegistration.excludePathPatterns("/purchase/t2OrderAudit**");
        interceptorRegistration.excludePathPatterns("/test/zhangbo**");

        InterceptorRegistration interceptorChannel = registry.addInterceptor(new InterceptorChannelConfig());
        interceptorChannel.addPathPatterns("/**");

    }



}
