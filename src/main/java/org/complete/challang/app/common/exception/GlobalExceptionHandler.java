package org.complete.challang.app.common.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final ApiException ex) {
        log.error(ex.getErrorCode().getMessage(), ex);
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(ex);

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final Exception ex) {
        log.error(ex.getMessage(), ex);
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(new ApiException(ErrorCode.UNHANDLED_EXCEPTION), ex);

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatusCode status,
                                                                         WebRequest request) {
        log.error(ex.getMessage(), ex);
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(new ApiException(ErrorCode.HTTP_REQUEST_NOT_SUPPORTED), ex);

        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {
        log.error(ex.getMessage(), ex);
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(new ApiException(ErrorCode.API_NOT_FOUND), ex);

        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.error(ex.getMessage(), ex);
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(ex);

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(ex);

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
