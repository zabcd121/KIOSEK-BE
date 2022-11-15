package com.cse.cseprojectroommanagementserver.global.jwt.exception;

public class TokenNotBearerException extends RuntimeException{
    public TokenNotBearerException(String message) {
        super(message);
    }
}
