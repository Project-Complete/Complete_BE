package org.complete.challang.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class SuccessResponse {

    private String message;
    private HttpStatus httpStatus;

    public static SuccessResponse toSuccessResponse(SuccessCode successCode) {
        return SuccessResponse.builder()
                .message(successCode.getMessage())
                .httpStatus(successCode.getHttpStatus())
                .build();
    }
}
