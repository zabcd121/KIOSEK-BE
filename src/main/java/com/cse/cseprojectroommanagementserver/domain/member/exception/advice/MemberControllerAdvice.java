package com.cse.cseprojectroommanagementserver.domain.member.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.member.api.AdminAuthApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.MemberApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.MemberAuthApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.SignupApiController;
import com.cse.cseprojectroommanagementserver.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static com.cse.cseprojectroommanagementserver.global.error.ErrorCode.*;

@RestControllerAdvice(assignableTypes = {MemberAuthApiController.class, AdminAuthApiController.class, SignupApiController.class, MemberApiController.class})
@Slf4j
public class MemberControllerAdvice {
    
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentExHandler(MethodArgumentNotValidException ex) {
        return makeErrorResponse(ex.getBindingResult());
    }

    private ResponseEntity<ErrorResponse> makeErrorResponse(BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> responseError = null;

        if (bindingResult.hasErrors()) {
            String bindResultField = bindingResult.getFieldError().getField();
            boolean isNotBlank = StringUtils.hasText(bindingResult.getFieldError().getRejectedValue().toString());
            log.error("bindResultField: {}", bindResultField);

            if(isNotBlank) {
                switch(bindResultField) {
                    case "loginId":
                        responseError = ErrorResponse.toResponseEntity(INVALID_VALUE_LOGIN_ID);
                        break;
                    case "password":
                        responseError = ErrorResponse.toResponseEntity(INVALID_VALUE_PASSWORD);
                        break;
                    case "email":
                        responseError = ErrorResponse.toResponseEntity(INVALID_VALUE_EMAIL);
                        break;
                    case "name":
                        responseError = ErrorResponse.toResponseEntity(INVALID_VALUE_NAME);
                        break;
                }
            } else {
                switch (bindResultField) {
                    case "loginId":
                        responseError = ErrorResponse.toResponseEntity(INPUT_NULL_LOGIN_ID);
                        break;
                    case "password":
                        responseError = ErrorResponse.toResponseEntity(INPUT_NULL_PASSWORD);
                        break;
                    case "email":
                        responseError = ErrorResponse.toResponseEntity(INPUT_NULL_EMAIL);
                        break;
                    case "name":
                        responseError = ErrorResponse.toResponseEntity(INPUT_NULL_NAME);
                        break;
                }
            }
        }
        return responseError;
    }

}
