package org.complete.challang.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {

    private String message;
    private HttpStatus httpStatus;

    public static ErrorResponse toErrorResponse(ApiException ex) {
        return ErrorResponse.builder()
                .message(ex.getErrorCode().getMessage())
                .httpStatus(ex.getErrorCode().getHttpStatus())
                .build();
    }

    public static ErrorResponse toErrorResponse(Exception e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();
    }
}
