package com.haier.distribute.data;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@ImportResource("classpath:/spring/framework-dubbo-provider.xml")
public class DistributeDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributeDataApplication.class, args);
	}
	
	@Bean(name = "distributeJdbcTemplate")
    public JdbcTemplate distributeJdbcTemplate(
            @Qualifier("distributeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
