package com.cse.cseprojectroommanagementserver.global.jwt.exception;

public class TokenNotPassedException extends RuntimeException {
    public TokenNotPassedException(String message) {
        super(message);
    }
}
