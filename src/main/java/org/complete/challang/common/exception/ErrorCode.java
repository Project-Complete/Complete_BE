package org.complete.challang.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DRINK_RATE_PARAM_INCORRECT("평가 조회 파라미터가 잘못되었습니다", HttpStatus.BAD_REQUEST),
    UNHANDLED_EXCEPTION("API exception에 처리되지 않은 예외입니다", HttpStatus.BAD_REQUEST),

    UNAUTHORIZED("인증되지 않은 사용자입니다", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_EXPIRED("액세스 토큰이 만료되었습니다", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("유효하지 않은 토큰입니다", HttpStatus.UNAUTHORIZED),
    OAUTH2_LOGIN_FAILURE("OAuth2 로그인에 실패하였습니다", HttpStatus.UNAUTHORIZED),

    REVIEW_REMOVE_FORBIDDEN("리뷰 삭제 접근 권한이 없습니다", HttpStatus.FORBIDDEN),
    FORBIDDEN("권한이 없는 사용자입니다", HttpStatus.FORBIDDEN),

    DRINK_NOT_FOUND("음료 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FLAVOR_NOT_FOUND("향 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FOOD_NOT_FOUND("음식 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND("리뷰 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("유저 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    API_NOT_FOUND("해당 API 요청이 존재하지 않습니다", HttpStatus.NOT_FOUND),

    NICKNAME_CONFLICT("해당 닉네임이 이미 존재합니다", HttpStatus.CONFLICT),
    EMAIL_CONFLICT("해당 이메일이 이미 존재합니다", HttpStatus.CONFLICT)
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
