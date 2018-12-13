package com.haier.shop.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ShopDataSourceReadConfig.PACKAGE, sqlSessionFactoryRef = "shopSqlSessionFactoryRead")
public class ShopDataSourceReadConfig {
    static final String PACKAGE = "com.haier.shop.dao.shopread";
    static final String MAPPER_LOCATION = "classpath:mapper/shopread/*.xml";

     @Value("${spring.datasource.shopread.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.shopread.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.shopread.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.shopread.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.shopread.time-between-eviction-runs-millis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.shopread.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.shopread.max-active}")
    private Integer maxActive;
    @Value("${spring.datasource.shopread.max-idle}")
    private Integer maxIdle;
    @Value("${spring.datasource.shopread.url}")
    private String url;
    @Value("${spring.datasource.shopread.username}")
    private String userName;
    @Value("${spring.datasource.shopread.password}")
    private String passWord;
    @Value("${spring.datasource.shopread.driver-class-name}")
    private String driverClassName;


    @Bean(name = "shopDataSourceRead")
    @Primary
    public DataSource shopDataSourceRead() {
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

    @Bean(name = "shopTransactionManagerRead")
    @Primary
    public DataSourceTransactionManager shopTransactionManagerRead() {
        return new DataSourceTransactionManager(shopDataSourceRead());
    }

    @Bean(name = "shopSqlSessionFactoryRead")
    @Primary
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("shopDataSourceRead") DataSource shopDataSourceRead)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(shopDataSourceRead);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ShopDataSourceReadConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
