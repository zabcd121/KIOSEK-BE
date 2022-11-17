package com.cse.cseprojectroommanagementserver.domain.member.exception;

public class DuplicatedLoginIdException extends RuntimeException{
    public DuplicatedLoginIdException(String message) {
        super(message);
    }
}
