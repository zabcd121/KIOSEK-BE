package com.cse.cseprojectroommanagementserver.global.jwt;

import com.cse.cseprojectroommanagementserver.global.error.exception.NotFoundException;
import com.cse.cseprojectroommanagementserver.global.error.exception.TokenException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.*;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String resolvedToken = jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER));
            if (resolvedToken != null && jwtTokenProvider.validateToken(resolvedToken)) {
                String isLogout = (String) redisTemplate.opsForValue().get(resolvedToken);
                if (ObjectUtils.isEmpty(isLogout)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(resolvedToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (NotFoundException | TokenException | JwtException | IllegalArgumentException e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }
}
