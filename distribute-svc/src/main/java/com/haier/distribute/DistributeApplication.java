package com.haier.distribute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ImportResource({ "classpath:spring/framework-dubbo-provider.xml","classpath:/spring/framework-dubbo-consumer.xml" })
public class DistributeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributeApplication.class, args);
	}
}
