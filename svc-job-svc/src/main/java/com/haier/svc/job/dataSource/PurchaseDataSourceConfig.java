package com.haier.svc.job.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.haier.svc.job.purchase", sqlSessionTemplateRef  = "purchaseSqlSessionTemplate")
public class PurchaseDataSourceConfig {

	@Value("${spring.datasource.base.testOnBorrow}")
    private boolean testOnBorrow;
	@Value("${spring.datasource.base.testOnReturn}")
    private boolean testOnReturn;
	@Value("${spring.datasource.base.testWhileIdle}")
    private boolean testWhileIdle;
	@Value("${spring.datasource.base.validation-query}")
    private String validationQuery;
	@Value("${spring.datasource.base.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.base.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
	@Value("${spring.datasource.base.max-active}")
	private Integer maxActive;
	@Value("${spring.datasource.base.max-idle}")
	private Integer maxIdle;
	
    @Bean(name = "purchaseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    
    public DataSource purchaseDataSource() {
    	org.apache.tomcat.jdbc.pool.DataSource dateSource = (org.apache.tomcat.jdbc.pool.DataSource)DataSourceBuilder.create().build();
    	dateSource.setTestOnBorrow(testOnBorrow);
    	dateSource.setTestOnReturn(testOnReturn);
    	dateSource.setTestWhileIdle(testWhileIdle);
    	dateSource.setValidationQuery(validationQuery);
    	dateSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    	dateSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    	dateSource.setMaxActive(maxActive);
    	dateSource.setMaxIdle(maxIdle);
        return dateSource;
    }

    @Bean(name = "purchaseSqlSessionFactory")
    public SqlSessionFactory purchaseSqlSessionFactory(@Qualifier("purchaseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource); 
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/purchase/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "purchaseTransactionManager")
    public DataSourceTransactionManager purchaseTransactionManager(@Qualifier("purchaseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "purchaseSqlSessionTemplate")
    public SqlSessionTemplate purchaseSqlSessionTemplate(@Qualifier("purchaseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
