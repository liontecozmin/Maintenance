//package com.tfo.maintenance.config;
//
//import lombok.extern.slf4j.Slf4j;
//import lombok.extern.slf4j.XSlf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//import static com.ibm.java.diagnostics.utils.Context.logger;
//
//@Configuration
//@Slf4j
//public class DataSourceProperties {
//
//    @Primary
//    @Bean(name = "datasource")
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource primaryDataSource() {
//        logger.info("first datasource loaded");
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "statusdatasource")
//    @ConfigurationProperties(prefix="spring.statusdatasource")
//    public DataSource secondaryDataSource() {
//        logger.info("second datasource loaded");
//        return DataSourceBuilder.create().build();
//    }
//}
