package com.haier.svc.api.controller.util.intercept;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
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
        interceptorRegistration.excludePathPatterns("/vom/receive**");
        interceptorRegistration.excludePathPatterns("/hp/accept_time**");
        interceptorRegistration.excludePathPatterns("/hp/hpallot_netpoint**");
        interceptorRegistration.excludePathPatterns("/hp/reservationGoodsDate**");
        interceptorRegistration.excludePathPatterns("/purchase/t2OrderAudit**");
        interceptorRegistration.excludePathPatterns("/cbs/allocationDefective**");
        interceptorRegistration.excludePathPatterns("/push/receive**");
        interceptorRegistration.excludePathPatterns("/operationArea/ReturnInformation**");
        interceptorRegistration.excludePathPatterns("/operationArea/HPReturnUnhealthyImpl**");
        interceptorRegistration.excludePathPatterns("/operationArea/analysisXml**");
        interceptorRegistration.excludePathPatterns("/cbs/selectByCode**");
        interceptorRegistration.excludePathPatterns("/workOrderDHZX/test");
        interceptorRegistration.excludePathPatterns("/workOrderDHZX/getReviewPoolForDhzxInsert");
        interceptorRegistration.excludePathPatterns("/workOrderDHZX/getReviewMiddleFromHP");
        interceptorRegistration.excludePathPatterns("/workOrderDHZX/getReviewPoolForDhzxUpdate");
        interceptorRegistration.excludePathPatterns("/transfer/on-audit");
    }

//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false).
//                favorParameter(false).
//                ignoreAcceptHeader(false).
//                useJaf(false).
//                defaultContentType(MediaType.TEXT_HTML).
//                mediaType("json", MediaType.APPLICATION_JSON);
//    }

}
