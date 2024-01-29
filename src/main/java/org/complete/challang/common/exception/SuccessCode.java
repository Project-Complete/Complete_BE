package org.complete.challang.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    REVIEW_DELETE_SUCCESS("리뷰 삭제를 성공했습니다", HttpStatus.OK);

    private final String message;
    private final HttpStatus httpStatus;
}
