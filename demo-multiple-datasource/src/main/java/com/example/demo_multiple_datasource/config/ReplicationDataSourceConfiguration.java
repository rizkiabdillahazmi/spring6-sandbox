package com.example.demo_multiple_datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ReplicationDataSourceConfiguration {
    private final ReplicationDataSourceProperties replicationDataSourceProperties;
    private final JpaProperties jpaProperties;

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder = createEntityManagerFactoryBuilder();
        return entityManagerFactoryBuilder.dataSource(routingDataSource()).packages("com.example.*.entity").build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder() {
        AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
    }

    @Bean
    public DataSource routingDataSource() {
        ReplicationRoutingDatasource replicationRoutingDataSource = new ReplicationRoutingDatasource();

        ReplicationDataSourceProperties.Write write = replicationDataSourceProperties.write();
        DataSource writeDataSource = createDataSource(write.getUrl(), "Write Datasource Pool",5,false);

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(write.getName(), writeDataSource);

        List<ReplicationDataSourceProperties.Read> reads = replicationDataSourceProperties.reads();
        for (ReplicationDataSourceProperties.Read read : reads) {
            dataSourceMap.put(read.getName(), createDataSource(read.getUrl(),"Read Datasource Pool",10,true));
        }

        replicationRoutingDataSource.setDefaultTargetDataSource(writeDataSource);
        replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
        replicationRoutingDataSource.afterPropertiesSet();

        return new LazyConnectionDataSourceProxy(replicationRoutingDataSource);
    }

    private DataSource createDataSource(String url,String poolName,Integer poolSize,Boolean readOnly) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(replicationDataSourceProperties.driverClassName());
        hikariDataSource.setUsername(replicationDataSourceProperties.username());
        hikariDataSource.setPassword(replicationDataSourceProperties.password());
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setReadOnly(readOnly);
        hikariDataSource.setPoolName(poolName);
        hikariDataSource.setMaximumPoolSize(poolSize);

        return hikariDataSource;
    }
}
