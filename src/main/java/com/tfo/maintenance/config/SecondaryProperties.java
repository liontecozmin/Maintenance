//package com.tfo.maintenance.config;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//
//import javax.sql.DataSource;
//
//public class SecondaryProperties {
//
//    @Bean
//    @ConfigurationProperties("app.datasource.status")
//    public DataSourceProperties statusDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//    @Bean
//    @ConfigurationProperties("app.datasource.status.configuration")
//    public DataSource statusDataSource() {
//        return statusDataSourceProperties().initializeDataSourceBuilder()
//                .type(BasicDataSource.class).build();
//    }
//    /*card data source*/
////    @Bean
////    @ConfigurationProperties("app.datasource.status")
////    public DataSourceProperties cardDataSourceProperties() {
////        return new DataSourceProperties();
////    }
////    @Bean
////    @ConfigurationProperties("app.datasource.status.configuration")
////    public DataSource cardDataSource() {
////        return cardDataSourceProperties().initializeDataSourceBuilder()
////                .type(BasicDataSource.class).build();
////    }
//}
