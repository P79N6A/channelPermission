package com.haier.afterSale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ImportResource({ "classpath:spring/framework-dubbo-provider.xml","classpath:/spring/framework-dubbo-consumer.xml" })
public class AfterSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfterSaleApplication.class, args);
	}
}
