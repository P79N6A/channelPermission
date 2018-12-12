package com.haier.svc.job;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@MapperScan("com.haier.svc.job.dao")
@ImportResource("classpath:/spring/framework-dubbo-provider.xml")
public class SvcJobApplication { 
	
	public static void main(String[] args) {
		SpringApplication.run(SvcJobApplication.class, args);
	}
	
	@Bean(name = "purchaseJdbcTemplate")
    public JdbcTemplate purchaseJdbcTemplate(
            @Qualifier("purchaseDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
