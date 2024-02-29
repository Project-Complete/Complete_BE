package org.complete.challang.app.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.complete.challang.app.common.exception.SuccessCode;
import org.complete.challang.app.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.app.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.app.review.controller.dto.response.ReviewDetailResponse;
import org.complete.challang.app.review.controller.dto.response.ReviewListFindResponse;
import org.complete.challang.app.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reviews",
        description = "리뷰 관련 API - 생성, 조회, 삭제, 좋아요")
@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "주류 리뷰 생성",
            description = "주류 리뷰 생성 및 생성된 리뷰의 미리보기 정보 반환")
    @PostMapping()
    public ResponseEntity<ReviewCreateResponse> createReview(@AuthenticationPrincipal final UserDetails user,
                                                             @RequestBody @Valid final ReviewCreateRequest reviewCreateRequest) {
        final Long userId = Long.parseLong(user.getUsername());
        final ReviewCreateResponse reviewCreateResponse = reviewService.createReview(reviewCreateRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewCreateResponse);
    }

    @Operation(summary = "리뷰 리스트 반환",
            description = "옵션(주류, 작성자, 좋아요 유무, 정렬 기준)에 따른 리뷰 리스트 반환")
    @GetMapping()
    public ResponseEntity<ReviewListFindResponse> findReviewList(@RequestParam(required = false, name = "drink-id") final Long drinkId,
                                                                 @RequestParam(required = false, name = "writer-id") final Long writerId,
                                                                 @RequestParam(required = false, name = "page", defaultValue = "0") final int page,
                                                                 @RequestParam(required = false, name = "sort", defaultValue = "latest") final String sort) {
        final ReviewListFindResponse reviewListFindResponse = reviewService.findReviewList(drinkId, writerId, page, sort);

        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewListFindResponse);
    }

    @Operation(summary = "리뷰 상세 정보 반환",
            description = "특정한 1개의 리뷰의 상세 정보를 반환")
    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDetailResponse> findReviewDetail(@PathVariable("review_id") final Long reviewId) {
        final ReviewDetailResponse reviewDetailResponse = reviewService.findReviewDetail(reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewDetailResponse);
    }

    @Operation(summary = "리뷰 삭제",
            description = "사용자가 자신이 작성한 특정 리뷰를 삭제")
    @DeleteMapping("/{review_id}")
    public ResponseEntity<SuccessCode> deleteReview(@AuthenticationPrincipal final UserDetails user,
                                                    @PathVariable("review_id") final Long reviewId) {
        final Long userId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = reviewService.deleteReview(userId, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(successCode);
    }

    @Operation(summary = "리뷰 좋아요",
            description = "사용자가 특정 1개의 리뷰를 좋아요")
    @PostMapping("/like/{review_id}")
    public ResponseEntity<SuccessCode> createReviewLike(@AuthenticationPrincipal final UserDetails user,
                                                        @PathVariable("review_id") final Long reviewId) {
        final Long userId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = reviewService.createReviewLike(userId, reviewId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(successCode);
    }

    @Operation(summary = "리뷰 좋아요 취소",
            description = "사용자가 좋아요 한 특정 1개의 리뷰를 좋아요 취소")
    @DeleteMapping("/like/{review_id}")
    public ResponseEntity<SuccessCode> deleteReviewLike(@AuthenticationPrincipal final UserDetails user,
                                                        @PathVariable("review_id") final Long reviewId) {
        final Long userId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = reviewService.deleteReviewLike(userId, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(successCode);
    }
}
