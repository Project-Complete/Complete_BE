package org.complete.challang.app.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DRINK_RATE_PARAM_INCORRECT("평가 조회 파라미터가 잘못되었습니다", HttpStatus.BAD_REQUEST),
    INVALID_EXTENSION("파일 확장자가 잘못되었습니다", HttpStatus.BAD_REQUEST),
    INVALID_FILENAME("파일 이름이 잘못되었습니다", HttpStatus.BAD_REQUEST),
    INVALID_FOLLOW_REQUEST("사용자 자신을 팔로우 할 수 없습니다.", HttpStatus.BAD_REQUEST),
    UNHANDLED_EXCEPTION("API exception에 처리되지 않은 예외입니다", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_EXTENSION("지원하지 않는 확장자입니다", HttpStatus.BAD_REQUEST),
    VALIDATION_FAILURE("파라미터 검증에 실패 하였습니다", HttpStatus.BAD_REQUEST),
    REPLY_COMMENT_DEPTH_EXCEPTION("답글의 깊이는 1을 초과할 수 없습니다.", HttpStatus.BAD_REQUEST),

    ACCESS_TOKEN_EXPIRED("액세스 토큰이 만료되었습니다", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("유효하지 않은 토큰입니다", HttpStatus.UNAUTHORIZED),
    OAUTH2_LOGIN_FAILURE("OAuth2 로그인에 실패하였습니다", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("인증되지 않은 사용자입니다", HttpStatus.UNAUTHORIZED),

    FORBIDDEN("권한이 없는 사용자입니다", HttpStatus.FORBIDDEN),
    REVIEW_REMOVE_FORBIDDEN("리뷰 삭제 접근 권한이 없습니다", HttpStatus.FORBIDDEN),
    COMBINATION_USER_FORBIDDEN("리뷰 조합 접근 권한이 없습니다", HttpStatus.FORBIDDEN),
    COMBINATION_COMMENT_USER_FORBIDDEN("리뷰 조합 댓글 접근 권한이 없습니다", HttpStatus.FORBIDDEN),

    API_NOT_FOUND("해당 API 요청이 존재하지 않습니다", HttpStatus.NOT_FOUND),
    DRINK_LIKE_NOT_FOUND("해당 주류의 좋아요가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    DRINK_NOT_FOUND("음료 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FLAVOR_NOT_FOUND("향 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FOLLOW_NOT_FOUND("팔로우 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    FOOD_NOT_FOUND("음식 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    REVIEW_LIKE_NOT_FOUND("해당 리뷰의 좋아요가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND("리뷰 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("유저 정보가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    DRINK_TYPE_NOT_FOUND("세부 주류 타입이 존재하지 않습니다", HttpStatus.NOT_FOUND),
    DRINK_MANUFACTURER_NOT_FOUND("제조사가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    DRINK_PACKAGE_NOT_FOUND("주류 패키지가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    COMBINATION_BOARD_NOT_FOUND("주류 조합 게시글이 존재하지 않습니다", HttpStatus.NOT_FOUND),
    COMBINATION_BOARD_LIKE_NOT_FOUND("해당 주류 조합의 좋아요가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    COMBINATION_BOARD_BOOKMARK_NOT_FOUND("해당 주류 조합의 좋아요가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    COMBINATION_COMMENT_NOT_FOUND("해당 댓글이 존재하지 않습니다", HttpStatus.NOT_FOUND),

    HTTP_REQUEST_NOT_SUPPORTED("지원하지 않는 HTTP METHOD 입니다", HttpStatus.METHOD_NOT_ALLOWED),

    DRINK_LIKE_CONFLICT("해당 주류에 이미 좋아요를 하였습니다", HttpStatus.CONFLICT),
    EMAIL_CONFLICT("해당 이메일이 이미 존재합니다", HttpStatus.CONFLICT),
    FOLLOW_CONFLICT("해당 팔로우가 이미 존재합니다", HttpStatus.CONFLICT),
    NICKNAME_CONFLICT("해당 닉네임이 이미 존재합니다", HttpStatus.CONFLICT),
    REVIEW_LIKE_CONFLICT("해당 이메일에 이미 좋아요를 하였습니다", HttpStatus.CONFLICT),
    DRINK_PACKAGE_CONFLICT("해당 주류에 이미 패키지가 등록되었습니다", HttpStatus.CONFLICT),
    COMBINATION_BOARD_LIKE_CONFLICT("해당 주류 조합에 이미 좋아요를 하였습니다", HttpStatus.CONFLICT),
    COMBINATION_BOARD_BOOKMARK_CONFLICT("해당 주류 조합에 이미 북마크를 하였습니다", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
