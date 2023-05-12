package com.cse.cseprojectroommanagementserver.domain.member.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.member.api.AdminAuthApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.MemberApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.MemberAuthApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.SignupApiController;
import com.cse.cseprojectroommanagementserver.domain.member.exception.*;
import com.cse.cseprojectroommanagementserver.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_LOGIN_ID_INVALID_TYPE);
                        break;
                    case "password":
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_PASSWORD_INVALID_TYPE);
                        break;
                    case "email":
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_EMAIL_INVALID_TYPE);
                        break;
                    case "name":
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_NAME_INVALID_TYPE);
                        break;
                }
            } else {
                switch (bindResultField) {
                    case "loginId":
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_LOGIN_ID_NULL);
                        break;
                    case "password":
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_PASSWORD_NULL);
                        break;
                    case "email":
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_EMAIL_NULL);
                        break;
                    case "name":
                        responseError = ErrorResponse.toResponseEntity(BAD_REQUEST_NAME_NULL);
                        break;
                }
            }
        }
        return responseError;
    }

}
