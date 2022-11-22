package com.cse.cseprojectroommanagementserver.domain.member.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.member.api.MemberApiController;
import com.cse.cseprojectroommanagementserver.domain.member.exception.EmailDuplicatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.LoginIdDuplicatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.NotExistsEqualRefreshToken;
import com.cse.cseprojectroommanagementserver.domain.member.exception.TokenNotBearerTypeException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestControllerAdvice(assignableTypes = {MemberApiController.class})
@Slf4j
public class MemberControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError emptyLoginArgument(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String field = "";
        String message = "";
        for (ObjectError e : allErrors) {
            FieldError fe = (FieldError) e;
            field = fe.getField();
            message = fe.getDefaultMessage();
            break;
        }
        log.info("emptyLoginArgument ex {}", ex);
        log.info("field, {}", field);
        if(field.equals("loginId")) return new ResponseError(LOGIN_ID_NULL, message);
        else return new ResponseError(PASSWORD_NULL, message);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseError notExistIdExHandler(UsernameNotFoundException ex) {
        log.error("[exceptionHandler] member", ex);
        return new ResponseError(LOGIN_ID_NOT_EXIST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseError invalidPWExHandler(BadCredentialsException ex) {
        log.error("[exceptionHandler] member", ex);
        return new ResponseError(PASSWORD_INVALID, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError duplicatedLoginIdExHandler(LoginIdDuplicatedException ex) {
        log.error("[exceptionHandler] member", ex);
        return new ResponseError(LOGIN_ID_DUPLICATED);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError duplicatedEmailExHandler(EmailDuplicatedException ex) {
        log.error("[exceptionHandler] member", ex);
        return new ResponseError(EMAIL_DUPLICATED);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError notExistEqualRefreshTokenExHandler(NotExistsEqualRefreshToken ex) {
        log.error("[exceptionHandler] member", ex);
        return new ResponseError(REFRESH_TOKEN_NOT_EXIST_IN_STORE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError notBearerTokenException(TokenNotBearerTypeException ex) {
        log.error("[exceptionHandler] member", ex);
        return new ResponseError(TOKEN_NOT_BEARER);
    }
}
