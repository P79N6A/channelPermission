package com.haier.stock;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ImportResource("classpath:/spring/framework-dubbo-provider.xml")
public class StockDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockDataApplication.class, args);
	}
	
    @Bean(name = "stockJdbcTemplate")
    public JdbcTemplate stockJdbcTemplate(
            @Qualifier("stockDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
