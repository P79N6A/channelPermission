package com.haier.eop.data.dataSource;

import javax.sql.DataSource;

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

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.haier.eop.data.dao", sqlSessionTemplateRef  = "eopSqlSessionTemplate")
public class EopDataSourceConfig {

	@Value("${spring.datasource.eop.testOnBorrow}")
    private boolean testOnBorrow;
	@Value("${spring.datasource.eop.testOnReturn}")
    private boolean testOnReturn;
	@Value("${spring.datasource.eop.testWhileIdle}")
    private boolean testWhileIdle;
	@Value("${spring.datasource.eop.validation-query}")
    private String validationQuery;
	@Value("${spring.datasource.eop.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.eop.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
	@Value("${spring.datasource.eop.max-active}")
	private Integer maxActive;
	@Value("${spring.datasource.eop.max-idle}")
	private Integer maxIdle;
	
    @Bean(name = "eopDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.eop")
    @Primary
    public DataSource eopDataSource() {
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

    @Bean(name = "eopSqlSessionFactory")
    @Primary
    public SqlSessionFactory eopSqlSessionFactory(@Qualifier("eopDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "eopTransactionManager")
    @Primary
    public DataSourceTransactionManager eopTransactionManager(@Qualifier("eopDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "eopSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate eopSqlSessionTemplate(@Qualifier("eopSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
