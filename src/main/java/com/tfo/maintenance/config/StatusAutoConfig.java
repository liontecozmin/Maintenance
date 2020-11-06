package com.tfo.maintenance.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * By default, the persistence-multiple-db.properties file is read for
 * non auto configuration in PersistenceProductConfiguration.
 * <p>
 * If we need to use persistence-multiple-db-boot.properties and auto configuration
 * then uncomment the below @Configuration class and comment out PersistenceProductConfiguration.
 */
//@Configuration
@PropertySource({"classpath:persistence-multiple-db-boot.properties"})
@EnableJpaRepositories(basePackages = "com.tfo.maintenance.dao.statuses", entityManagerFactoryRef = "statusEntityManager", transactionManagerRef = "statusTransactionManager")
@Profile("!tc")
public class StatusAutoConfig {
    @Autowired
    private Environment env;

    public StatusAutoConfig() {
        super();
    }

    //

    @Bean
    public LocalContainerEntityManagerFactoryBean statusEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(statusDataSource());
        em.setPackagesToScan("com.tfo.maintenance.dao.statuses");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.statusdatasource")
    public DataSource statusDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager statusTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(statusEntityManager().getObject());
        return transactionManager;
    }

}