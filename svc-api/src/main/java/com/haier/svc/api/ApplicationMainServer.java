package com.haier.svc.api;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;
import org.apache.velocity.app.VelocityEngine;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableJSONDoc
@ImportResource({ "classpath:application-b.xml","classpath:/spring/svc-web-dubbo-consumer.xml" })
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ApplicationMainServer {  
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(
				ApplicationMainServer.class, args);
	} 

	@Bean
	public FilterRegistrationBean getCharacterEncodingFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();

		bean.setFilter(new CharacterEncodingFilter());
		bean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		bean.addInitParameter("encoding", "UTF-8");
		bean.addInitParameter("forceEncoding", "true");
		bean.addUrlPatterns("/*");

		return bean;
	}

	@Bean
	VelocityEngine velocityEngine() throws IOException {
		Properties properties = new Properties();
		return new VelocityEngine(properties);
	}

	@Bean
	public ObjectMapper ObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}

}
