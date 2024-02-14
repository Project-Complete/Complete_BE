package org.complete.challang.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DRINK_NOT_FOUND("음료 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FLAVOR_NOT_FOUND("향 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FOOD_NOT_FOUND("음식 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("유저 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
