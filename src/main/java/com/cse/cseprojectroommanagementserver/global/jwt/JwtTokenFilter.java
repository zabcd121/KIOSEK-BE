package com.cse.cseprojectroommanagementserver.global.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;

    /**
     * JWT를 검증하는 필터
     * HttpServletRequest 의 Authorization 헤더에서 JWT token을 찾고 그것이 맞는지 확인
     * UsernamePasswordAuthenticationFilter 앞에서 작동
     * (JwtTokenFilterConfigurer 참고)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("####doFilterInternal진입");
        String jwt = resolveToken(request, AUTHORIZATION_HEADER);

        try{
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                String isLogout = (String) redisTemplate.opsForValue().get(jwt);

                if (ObjectUtils.isEmpty(isLogout)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("set Authentication to security context for '{}', uri: {}", authentication.getName(), request.getRequestURI());
                }
            }

        } /*catch(TokenNotPassedException e) {
            request.setAttribute("exception", e);
            log.info("catch token not passed exception");
        } catch (TokenNotBearerException e) {
            request.setAttribute("exception", e);
        }*/
        catch (SignatureException e) {
            request.setAttribute("exception", e);
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", e);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", e);
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", e);
        } catch (JwtException | IllegalArgumentException e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request, String header) {

            /*String bearerToken = request.getHeader(header);

            if(bearerToken == null || bearerToken.equals("")) {
                log.info("bearer token not passed");
                throw new TokenNotPassedException(TOKEN_NOT_PASSED.getMessage());
            }

            if(!bearerToken.startsWith(BEARER)) {
                throw new TokenNotBearerException(TOKEN_NOT_BEARER.getMessage());
            }

            return bearerToken.substring(7);*/

        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;

    }
}
