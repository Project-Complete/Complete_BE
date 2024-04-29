package org.complete.challang.app.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final ApiException ex) {
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(ex);

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final Exception ex) {
        log.error(ex.getMessage(), ex);
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(new ApiException(ErrorCode.UNHANDLED_EXCEPTION), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(new ApiException(ErrorCode.API_NOT_FOUND), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
