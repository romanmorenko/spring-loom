package ru.neoflex.springloom.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {

        String url = dataSourceProperties.determineUrl();
        String user = dataSourceProperties.determineUsername();
        String password = dataSourceProperties.determinePassword();
        String driverClassName = DatabaseDriver.fromJdbcUrl(url).getDriverClassName();
        return DataSourceBuilder.create()
                .type(SimpleDriverDataSource.class)
                .url(url)
                .username(user)
                .password(password).driverClassName(driverClassName).build();

    }

    @Bean
    @ConfigurationProperties("datasource")
    public DataSourceProperties dataSourceProperties() {

        return new DataSourceProperties();
    }

}
