package com.haier.traderate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ImportResource({"classpath:/spring/framework-dubbo-consumer.xml", "classpath:spring/framework-dubbo-provider.xml"})
public class TraderateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraderateApplication.class, args);
    }
}
