package com.cse.cseprojectroommanagementserver.global.config;

import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenFilter;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilterConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, new RedisTemplate());
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
