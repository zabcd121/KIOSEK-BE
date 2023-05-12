package com.cse.cseprojectroommanagementserver.domain.member.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.member.api.AdminAuthApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.MemberApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.MemberAuthApiController;
import com.cse.cseprojectroommanagementserver.domain.member.api.SignupApiController;
import com.cse.cseprojectroommanagementserver.domain.member.exception.*;
import com.cse.cseprojectroommanagementserver.global.dto.ErrorResponse;
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

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notExistIdExHandler(UsernameNotFoundException ex) {
        log.error("[exceptionHandler] UsernameNotFoundException", ex);
        return ErrorResponse.toResponseEntity(LOGIN_FAIL_ID_NOT_EXIST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> invalidPWExHandler(InvalidPasswordException ex) {
        log.error("[exceptionHandler] InvalidPasswordException", ex);
        return ErrorResponse.toResponseEntity(LOGIN_FAIL_PASSWORD_INVALID);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> noAuthorityExHandler(NoAuthorityToLoginException ex) {
        log.error("[exceptionHandler] NoAuthorityToLoginException", ex);
        return ErrorResponse.toResponseEntity(ACCESS_FAIL_NO_AUTHORITY);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicatedLoginIdExHandler(LoginIdDuplicatedException ex) {
        log.error("[exceptionHandler] LoginIdDuplicatedException", ex);
        return ErrorResponse.toResponseEntity(LOGIN_ID_DUPLICATION_CHECK_FAIL_UNUSABLE);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicatedEmailExHandler(EmailDuplicatedException ex) {
        log.error("[exceptionHandler] EmailDuplicatedException", ex);
        return ErrorResponse.toResponseEntity(EMAIL_DUPLICATION_CHECK_FAIL_UNUSABLE);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notExistEqualRefreshTokenExHandler(NotExistsRefreshTokenException ex) {
        log.error("[exceptionHandler] NotExistsEqualRefreshTokenException", ex);
        return ErrorResponse.toResponseEntity(TOKEN_REISSUE_FAIL_REFRESH_TOKEN_NOT_EXIST_IN_STORE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notBearerTypeExHandler(TokenNotBearerTypeException ex) {
        log.error("[exceptionHandler] TokenNotBearerTypeException", ex);
        return ErrorResponse.toResponseEntity(BAD_REQUEST_TOKEN_NOT_BEARER);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> refreshTokenIsExpiredExHandler(RefreshTokenIsExpiredException ex) {
        log.error("[exceptionHandler] RefreshTokenIsExpiredException", ex);
        return ErrorResponse.toResponseEntity(TOKEN_REISSUE_FAIL_REFRESH_TOKEN_EXPIRED);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notCreatedAccountQRExHandler(AccountQRNotCreatedException ex) {
        log.error("[exceptionHandler] AccountQRNotCreatedException", ex);
        return ErrorResponse.toResponseEntity(ACCOUNT_QR_CREATE_FAIL);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> authCodeNotFoundExHandler(AuthCodeNotFoundException ex) {
        log.error("[exceptionHandler] AuthCodeNotFoundException", ex);
        return ErrorResponse.toResponseEntity(AUTH_CODE_VERIFY_FAIL_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> authCodeNotEqExHandler(AuthCodeNotEqException ex) {
        log.error("[exceptionHandler] AuthCodeNotEqException", ex);
        return ErrorResponse.toResponseEntity(AUTH_CODE_VERIFY_FAIL);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> authCodeNotVerifiedYetExHandler(AuthCodeNotVerifiedYetException ex) {
        log.error("[exceptionHandler] AuthCodeNotVerifiedException", ex);
        return ErrorResponse.toResponseEntity(AUTH_CODE_NOT_VERIFIED);
    }



}
