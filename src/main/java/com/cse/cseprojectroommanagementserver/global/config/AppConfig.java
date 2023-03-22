package com.cse.cseprojectroommanagementserver.global.config;

import com.cse.cseprojectroommanagementserver.global.config.formatter.LocalDateFormatter;
import com.cse.cseprojectroommanagementserver.global.config.formatter.LocalDateTimeFormatter;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
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

    @Bean
    public CountedAspect countedAspect(MeterRegistry meterRegistry) { return new CountedAspect(meterRegistry); }

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) { return new TimedAspect(meterRegistry); }
}