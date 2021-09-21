package ru.neoflex.springloom.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class DbConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {

        String url = dataSourceProperties.determineUrl();
        String user = dataSourceProperties.determineUsername();
        String password = dataSourceProperties.determinePassword();
        String driverClassName = DatabaseDriver.fromJdbcUrl(url).getDriverClassName();
        HikariDataSource hikariDataSource = new HikariDataSource();
        if (Boolean.TRUE.equals(Boolean.valueOf(System.getProperty("connection.pool.threadfactory.virtual")))) {
            hikariDataSource.setThreadFactory(Thread.ofVirtual().factory());
        }
        DataSource dataSource = DataSourceBuilder.create()
                .type(DriverManagerDataSource.class)
                .url(url)
                .username(user)
                .password(password).driverClassName(driverClassName).build();
        hikariDataSource.setDataSource(dataSource);
        return hikariDataSource;

    }

    @Bean
    @ConfigurationProperties("datasource")
    public DataSourceProperties dataSourceProperties() {

        return new DataSourceProperties();
    }

}
