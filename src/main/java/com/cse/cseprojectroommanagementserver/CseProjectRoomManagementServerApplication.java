package com.cse.cseprojectroommanagementserver;

import com.cse.cseprojectroommanagementserver.domain.reservation.repository.NamedLockRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManager;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableCaching
public class CseProjectRoomManagementServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CseProjectRoomManagementServerApplication.class, args);
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("reservelock.datasource.hikari")
    public HikariDataSource reserveLockDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public NamedLockRepository userLevelLockFinal() {
        return new NamedLockRepository(reserveLockDataSource());
    }


    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
