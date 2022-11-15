package com.cse.cseprojectroommanagementserver.global.jwt;

import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;


@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());

        Exception exception = (Exception) request.getAttribute("exception");

        log.error("exception: {}", exception.getMessage());

        if(exception == null) {
            log.error("null token");
            setResponse(response, ACCESS_DENIED);
        }
        else if(exception instanceof ExpiredJwtException) {
            log.error("jwt 만료");
            setResponse(response, TOKEN_EXPIRED);
        }
        else {
            log.error("else");
            setResponse(response, TOKEN_WRONG_TYPE);
        }

    }

    private void setResponse(HttpServletResponse response, ResponseConditionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("code", exceptionCode.getCode());
        responseJson.put("message", exceptionCode.getMessage());


        response.getWriter().print(responseJson);
    }
}
