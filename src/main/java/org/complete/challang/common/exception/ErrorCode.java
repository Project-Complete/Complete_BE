package org.complete.challang.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DRINK_RATE_PARAM_INCORRECT("평가 조회 파라미터가 잘못되었습니다", HttpStatus.BAD_REQUEST),
    INVALID_FOLLOW_REQUEST("사용자 자신을 팔로우 할 수 없습니다.", HttpStatus.BAD_REQUEST),

    UNAUTHORIZED("인증되지 않은 사용자입니다", HttpStatus.UNAUTHORIZED),

    REVIEW_REMOVE_FORBIDDEN("리뷰 삭제 접근 권한이 없습니다", HttpStatus.FORBIDDEN),
    FORBIDDEN("권한이 없는 사용자입니다", HttpStatus.FORBIDDEN),

    DRINK_NOT_FOUND("음료 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FLAVOR_NOT_FOUND("향 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FOOD_NOT_FOUND("음식 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND("리뷰 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("유저 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),

    NICKNAME_CONFLICT("해당 닉네임이 이미 존재합니다.", HttpStatus.CONFLICT),
    EMAIL_CONFLICT("해당 이메일이 이미 존재합니다.", HttpStatus.CONFLICT),
    FOLLOW_CONFLICT("해당 팔로우가 이미 존재합니다.", HttpStatus.CONFLICT),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
