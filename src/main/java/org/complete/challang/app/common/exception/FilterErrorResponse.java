package org.complete.challang.app.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class FilterErrorResponse {

    private final ObjectMapper objectMapper;

    public void toJson(final HttpServletResponse response,
                       final Exception e,
                       final ErrorCode errorCode) throws IOException {
        final ErrorResponse errorResponse = ErrorResponse.toErrorResponse(new ApiException(errorCode), e);
        final String responseBody = objectMapper.writeValueAsString(errorResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
