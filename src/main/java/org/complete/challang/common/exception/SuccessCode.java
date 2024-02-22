package org.complete.challang.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    FOLLOW_CREATE_SUCCESS("팔로우 생성을 성공했습니다.", HttpStatus.CREATED),

    FOLLOW_DELETE_SUCCESS("팔로우 삭제를 성공했습니다.", HttpStatus.NO_CONTENT),
    REVIEW_DELETE_SUCCESS("리뷰 삭제를 성공했습니다", HttpStatus.NO_CONTENT),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
