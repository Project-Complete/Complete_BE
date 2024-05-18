package org.complete.challang.app.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rawMessage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValidationError> errors;

    private HttpStatus httpStatus;

    public static ErrorResponse toErrorResponse(ApiException ex) {
        return ErrorResponse.builder()
                .message(ex.getErrorCode().getMessage())
                .httpStatus(ex.getErrorCode().getHttpStatus())
                .build();
    }

    public static ErrorResponse toErrorResponse(ApiException ex,
                                                Exception e) {
        return ErrorResponse.builder()
                .message(ex.getErrorCode().getMessage())
                .rawMessage(e.getMessage())
                .httpStatus(ex.getErrorCode().getHttpStatus())
                .build();
    }

    public static ErrorResponse toErrorResponse(BindException ex) {
        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .toList();

        return ErrorResponse.builder()
                .message(ErrorCode.VALIDATION_FAILURE.getMessage())
                .errors(validationErrors)
                .httpStatus(ErrorCode.VALIDATION_FAILURE.getHttpStatus())
                .build();
    }

    public static ErrorResponse toErrorResponse(ConstraintViolationException ex) {
        List<ValidationError> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(ValidationError::of)
                .toList();

        return ErrorResponse.builder()
                .message(ErrorCode.VALIDATION_FAILURE.getMessage())
                .errors(validationErrors)
                .httpStatus(ErrorCode.VALIDATION_FAILURE.getHttpStatus())
                .build();
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }

        public static ValidationError of(final ConstraintViolation fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getPropertyPath().toString())
                    .message(fieldError.getMessageTemplate())
                    .build();
        }
    }
}
