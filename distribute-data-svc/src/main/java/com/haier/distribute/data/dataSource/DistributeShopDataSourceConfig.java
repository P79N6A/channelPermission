package com.haier.distribute.data.dataSource;

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
@MapperScan(basePackages = "com.haier.distribute.data.dao.shop", sqlSessionTemplateRef  = "distributeShopSqlSessionTemplate")
public class DistributeShopDataSourceConfig {

	@Value("${spring.datasource.shop.testOnBorrow}")
    private boolean testOnBorrow;
	@Value("${spring.datasource.shop.testOnReturn}")
    private boolean testOnReturn;
	@Value("${spring.datasource.shop.testWhileIdle}")
    private boolean testWhileIdle;
	@Value("${spring.datasource.shop.validation-query}")
    private String validationQuery;
	@Value("${spring.datasource.shop.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.shop.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
	@Value("${spring.datasource.shop.max-active}")
	private Integer maxActive;
	@Value("${spring.datasource.shop.max-idle}")
	private Integer maxIdle;
	
    @Bean(name = "distributeShopDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.shop")
    public DataSource distributeShopDataSource() {
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

    @Bean(name = "distributeShopSqlSessionFactory")
    public SqlSessionFactory distributeShopSqlSessionFactory(@Qualifier("distributeShopDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/distribute/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "distributeShopTransactionManager")
    public DataSourceTransactionManager distributeShopTransactionManager(@Qualifier("distributeShopDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "distributeShopSqlSessionTemplate")
    public SqlSessionTemplate distributeShopSqlSessionTemplate(@Qualifier("distributeShopSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
