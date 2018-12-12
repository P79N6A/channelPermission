
package com.haier.svc.api.webService.WwwHpRecords.config;


import java.net.InetAddress;

import javax.xml.ws.Endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.haier.svc.api.webService.WwwHpRecords.ServiceImpl.HPReturnIdentificationResultsImpl;


/**
 * Created by zhangbo on 2017/11/20.
 */

//@Configuration
public class CxfConfig {
/** JAX-WS **/

//  @Bean
//    public Endpoint xmlString()throws Exception{
//        String ip = "http://"+InetAddress.getLocalHost().getHostAddress()+":9095/HPReturn";//获得本机IP
//        System.out.println(ip);
//        HPReturnIdentificationResultsImpl commonService = new HPReturnIdentificationResultsImpl();
//        Endpoint endpoint = Endpoint.publish(ip, commonService);
//        System.out.println("发布成功");
//        return endpoint;
//    }
}

