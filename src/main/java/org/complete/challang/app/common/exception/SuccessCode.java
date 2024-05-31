package org.complete.challang.app.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    DRINK_LIKE_SUCCESS("주류 좋아요를 성공했습니다", HttpStatus.CREATED),
    FOLLOW_CREATE_SUCCESS("팔로우 생성을 성공했습니다", HttpStatus.CREATED),
    REVIEW_LIKE_SUCCESS("리뷰 좋아요를 성공했습니다", HttpStatus.CREATED),
    COMBINATION_BOARD_LIKE_SUCCESS("주류 조합 좋아요를 성공했습니다", HttpStatus.CREATED),
    COMBINATION_BOARD_BOOKMARK_SUCCESS("주류 조합 북마크를 성공했습니다", HttpStatus.CREATED),

    DRINK_LIKE_DELETE_SUCCESS("주류 좋아요 삭제를 성공했습니다", HttpStatus.NO_CONTENT),
    FOLLOW_DELETE_SUCCESS("팔로우 삭제를 성공했습니다", HttpStatus.NO_CONTENT),
    REVIEW_DELETE_SUCCESS("리뷰 삭제를 성공했습니다", HttpStatus.NO_CONTENT),
    REVIEW_LIKE_DELETE_SUCCESS("주류 리뷰 좋아요 삭제를 성공했습니다", HttpStatus.NO_CONTENT),
    COMBINATION_BOARD_DELETE_SUCCESS("주류 리뷰 좋아요 삭제를 성공했습니다", HttpStatus.NO_CONTENT),
    COMBINATION_BOARD_LIKE_DELETE_SUCCESS("주류 조합 좋아요 삭제를 성공했습니다", HttpStatus.NO_CONTENT),
    COMBINATION_BOARD_BOOKMARK_DELETE_SUCCESS("주류 조합 북마크 삭제를 성공했습니다", HttpStatus.NO_CONTENT);

    private final String message;
    private final HttpStatus httpStatus;
}
