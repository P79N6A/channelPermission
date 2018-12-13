package com.haier.shop;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@ImportResource("classpath:/spring/framework-dubbo-provider.xml")
public class ShopDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDataApplication.class, args);
    }

    @Bean(name = "shopReadJdbcTemplate")
    public JdbcTemplate shopReadJdbcTemplate(
            @Qualifier("shopDataSourceRead") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "shopWriteJdbcTemplate")
    public JdbcTemplate shopWriteJdbcTemplate(
            @Qualifier("shopDataSourceWrite") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
