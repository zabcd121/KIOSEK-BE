package com.cse.cseprojectroommanagementserver.domain.member.exception;

public class DuplicatedEmailException extends RuntimeException{
    public DuplicatedEmailException(String message) {
        super(message);
    }
}
