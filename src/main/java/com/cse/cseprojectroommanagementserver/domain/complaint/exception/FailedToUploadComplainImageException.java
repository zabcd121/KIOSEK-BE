package com.cse.cseprojectroommanagementserver.domain.complaint.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class FailedToUploadComplainImageException extends BusinessLogicException {
    public FailedToUploadComplainImageException() {
        super(ErrorCode.COMPLAIN_FAIL_IMAGE_UPLOAD_FAIL);
    }
}
