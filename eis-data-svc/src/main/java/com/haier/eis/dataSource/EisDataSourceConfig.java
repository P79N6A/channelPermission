package com.haier.eis.dataSource;

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
 * Created by hanhaoyang@ehaier.com on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.haier.eis.dao.eis", sqlSessionTemplateRef  = "eisSqlSessionTemplate")
public class EisDataSourceConfig {

    @Value("${spring.datasource.eis.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.eis.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.eis.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.eis.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.eis.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.eis.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.eis.max-active}")
    private Integer maxActive;
    @Value("${spring.datasource.eis.max-idle}")
    private Integer maxIdle;

    @Bean(name = "eisDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.eis")
    @Primary
    public DataSource eisDataSource() {
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

    @Bean(name = "eisSqlSessionFactory")
    @Primary
    public SqlSessionFactory eisSqlSessionFactory(@Qualifier("eisDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/eis/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "eisTransactionManager")
    @Primary
    public DataSourceTransactionManager eisTransactionManager(@Qualifier("eisDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "eisSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate eisSqlSessionTemplate(@Qualifier("eisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

