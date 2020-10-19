//package com.tfo.maintenance.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//public class PrimaryProperties {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("app.datasource.alert")
//    public DataSourceProperties alertDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("app.datasource.alert.configuration")
//    public DataSource alertDataSource() {
//        return alertDataSourceProperties().initializeDataSourceBuilder()
//                .type(HikariDataSource.class).build();
//
//    }
//}