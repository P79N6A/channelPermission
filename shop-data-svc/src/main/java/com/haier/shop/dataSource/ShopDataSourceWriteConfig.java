package com.haier.shop.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ShopDataSourceWriteConfig.PACKAGE, sqlSessionFactoryRef = "shopSqlSessionFactoryWrite")
public class ShopDataSourceWriteConfig {
    static final String PACKAGE = "com.haier.shop.dao.shopwrite";
    static final String MAPPER_LOCATION = "classpath:mapper/shopwrite/*.xml";

    @Value("${spring.datasource.shopwrite.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.shopwrite.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.shopwrite.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.shopwrite.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.shopwrite.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.shopwrite.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.shopwrite.max-active}")
    private Integer maxActive;
    @Value("${spring.datasource.shopwrite.max-idle}")
    private Integer maxIdle;
    @Value("${spring.datasource.shopwrite.url}")
    private String url;
    @Value("${spring.datasource.shopwrite.username}")
    private String userName;
    @Value("${spring.datasource.shopwrite.password}")
    private String passWord;
    @Value("${spring.datasource.shopwrite.driver-class-name}")
    private String driverClassName;


    @Bean(name = "shopDataSourceWrite")
    public DataSource shopDataSourceWrite() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxIdle(maxIdle);
        return dataSource;
    }

    @Bean(name = "shopTransactionManagerWrite")
    public DataSourceTransactionManager shopTransactionManagerWrite() {
        return new DataSourceTransactionManager(shopDataSourceWrite());
    }

    @Bean(name = "shopSqlSessionFactoryWrite")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("shopDataSourceWrite") DataSource shopDataSourceWrite)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(shopDataSourceWrite);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ShopDataSourceWriteConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
