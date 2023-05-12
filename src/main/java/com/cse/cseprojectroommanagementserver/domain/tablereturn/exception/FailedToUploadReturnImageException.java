package com.cse.cseprojectroommanagementserver.domain.tablereturn.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class FailedToUploadReturnImageException extends BusinessLogicException {
    public FailedToUploadReturnImageException() {
        super(ErrorCode.RETURN_FAIL_IMAGE_UPLOAD_FAIL);
    }
}
