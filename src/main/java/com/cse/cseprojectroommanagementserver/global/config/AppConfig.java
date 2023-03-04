package com.cse.cseprojectroommanagementserver.global.config;

import com.cse.cseprojectroommanagementserver.global.config.formatter.LocalDateFormatter;
import com.cse.cseprojectroommanagementserver.global.config.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public LocalDateFormatter localDateFormatter() {
        return new LocalDateFormatter();
    }

    @Bean
    public LocalDateTimeFormatter localDateTimeFormatter() {
        return new LocalDateTimeFormatter();
    }
}