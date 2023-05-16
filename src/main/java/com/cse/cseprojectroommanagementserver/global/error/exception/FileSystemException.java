package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class FileSystemException extends AbstractBusinessLogicException {
    public FileSystemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
