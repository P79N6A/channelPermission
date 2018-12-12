package com.haier.dbconfig.dataSource;

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
@MapperScan(basePackages = "com.haier.dbconfig.dao", sqlSessionTemplateRef  = "dbConfigSqlSessionTemplate")
public class DBconfigDataSourceConfig {

	@Value("${spring.datasource.dbConfig.testOnBorrow}")
    private boolean testOnBorrow;
	@Value("${spring.datasource.dbConfig.testOnReturn}")
    private boolean testOnReturn;
	@Value("${spring.datasource.dbConfig.testWhileIdle}")
    private boolean testWhileIdle;
	@Value("${spring.datasource.dbConfig.validation-query}")
    private String validationQuery;
	@Value("${spring.datasource.dbConfig.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.dbConfig.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
	@Value("${spring.datasource.dbConfig.max-active}")
	private Integer maxActive;
	@Value("${spring.datasource.dbConfig.max-idle}")
	private Integer maxIdle;
	
    @Bean(name = "dbConfigDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dbConfig")
    @Primary
    public DataSource dbConfigDataSource() {
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

    @Bean(name = "dbConfigSqlSessionFactory")
    @Primary
    public SqlSessionFactory dbConfigSqlSessionFactory(@Qualifier("dbConfigDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "dbConfigTransactionManager")
    @Primary
    public DataSourceTransactionManager dbConfigTransactionManager(@Qualifier("dbConfigDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dbConfigSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate dbConfigSqlSessionTemplate(@Qualifier("dbConfigSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
