package org.complete.challang.app.common.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
