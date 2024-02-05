package org.complete.challang.common.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
