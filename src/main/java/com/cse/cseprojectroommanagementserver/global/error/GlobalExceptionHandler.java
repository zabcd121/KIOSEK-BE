package com.cse.cseprojectroommanagementserver.global.error;

import com.cse.cseprojectroommanagementserver.global.error.exception.AbstractErrorException;
import com.cse.cseprojectroommanagementserver.global.error.exception.AbstractBusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(". ");
        }
        builder.deleteCharAt(builder.length() - 1);

        final ErrorResponse response = new ErrorResponse(errorCode.getCode(), builder.toString());

        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(AbstractBusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogicException(AbstractBusinessLogicException e){
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(AbstractErrorException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogicException(AbstractErrorException e){
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

//    @ExceptionHandler(InvalidInputException.class)
//    public ResponseEntity<ErrorResponse> handleBusinessLogicException(InvalidInputException e){
//        ErrorCode errorCode = e.getErrorCode();
//        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
//        return new ResponseEntity<>(response, errorCode.getHttpStatus());
//    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        final ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }
}
