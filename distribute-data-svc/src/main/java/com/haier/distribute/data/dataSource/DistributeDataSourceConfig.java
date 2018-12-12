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
@MapperScan(basePackages = "com.haier.distribute.data.dao.distribute", sqlSessionTemplateRef  = "distributeSqlSessionTemplate")
public class DistributeDataSourceConfig {

	@Value("${spring.datasource.distribute.testOnBorrow}")
    private boolean testOnBorrow;
	@Value("${spring.datasource.distribute.testOnReturn}")
    private boolean testOnReturn;
	@Value("${spring.datasource.distribute.testWhileIdle}")
    private boolean testWhileIdle;
	@Value("${spring.datasource.distribute.validation-query}")
    private String validationQuery;
	@Value("${spring.datasource.distribute.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.distribute.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
	@Value("${spring.datasource.distribute.max-active}")
	private Integer maxActive;
	@Value("${spring.datasource.distribute.max-idle}")
	private Integer maxIdle;
	
    @Bean(name = "distributeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.distribute")
    @Primary
    public DataSource distributeDataSource() {
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

    @Bean(name = "distributeSqlSessionFactory")
    @Primary
    public SqlSessionFactory distributeSqlSessionFactory(@Qualifier("distributeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/distribute/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "distributeTransactionManager")
    @Primary
    public DataSourceTransactionManager distributeTransactionManager(@Qualifier("distributeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "distributeSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate distributeSqlSessionTemplate(@Qualifier("distributeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
