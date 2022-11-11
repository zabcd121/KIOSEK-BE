package com.cse.cseprojectroommanagementserver.domain.operationpolicy.domain.model;

public interface Verifiable<T> {

    void verify(T value);
}
