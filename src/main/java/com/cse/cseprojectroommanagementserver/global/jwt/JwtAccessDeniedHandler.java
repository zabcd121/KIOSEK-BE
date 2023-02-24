package com.cse.cseprojectroommanagementserver.global.jwt;

import com.cse.cseprojectroommanagementserver.global.common.ResConditionCode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{
        setResponse(response, ResConditionCode.ACCESS_DENIED);
    }

    private void setResponse(HttpServletResponse response, ResConditionCode exceptionCode) throws IOException {
        log.info("403 handling ....");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        JSONObject responseJson = new JSONObject();

        responseJson.put("code", exceptionCode.getCode());
        responseJson.put("message", exceptionCode.getMessage());

        response.getWriter().print(responseJson);
    }
}
