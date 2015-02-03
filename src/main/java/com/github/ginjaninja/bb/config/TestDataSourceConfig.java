package com.github.ginjaninja.bb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Test DataSource config. Uses properties from persisence.properties file.
 *
 */
@Configuration
class TestDataSourceConfig implements DataSourceConfig {

    @Value("${testDataSource.driverClassName}")
    private String driver;
    @Value("${testDataSource.url}")
    private String url;
    @Value("${testDataSource.username}")
    private String username;
    @Value("${testDataSource.password}")
    private String password;

    @Bean
    @Override
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
