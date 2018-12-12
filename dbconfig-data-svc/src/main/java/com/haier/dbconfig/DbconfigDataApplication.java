package com.haier.dbconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@ImportResource("classpath:/spring/framework-dubbo-provider.xml")
public class DbconfigDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbconfigDataApplication.class, args);
	}
	
	@Bean(name = "dbConfigJdbcTemplate")
    public JdbcTemplate dbConfigJdbcTemplate(
            @Qualifier("dbConfigDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
