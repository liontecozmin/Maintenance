//package com.tfo.maintenance.config;
//
//import com.tfo.maintenance.entity.Alerts;
//import com.tfo.maintenance.entity.VesselStatus;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//
//public class EntityManager {
//
//    /*Primary Entity manager*/
//    @Primary
//    @Bean(name = "alertEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean alertEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(alertDataSource())
//                .packages(Alerts.class)
//                .build();
//    }
//
//    /*Secondary Entity Managers*/
//    @Bean(name = "statusEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean statusEntityManagerFactory(
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(statusDataSource())
//                .packages(VesselStatus.class)
//                .build();
//    }
//
//}
