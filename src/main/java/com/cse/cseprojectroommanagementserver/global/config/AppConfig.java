package com.cse.cseprojectroommanagementserver.global.config;

import com.cse.cseprojectroommanagementserver.global.formatter.LocalDateFormatter;
import com.cse.cseprojectroommanagementserver.global.formatter.LocalDateTimeFormatter;
import com.cse.cseprojectroommanagementserver.global.interceptor.VisitorRecordInterceptor;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final VisitorRecordInterceptor visitorRecordInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitorRecordInterceptor)
                .addPathPatterns("/api/**");
    }

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