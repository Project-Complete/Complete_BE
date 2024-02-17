package org.complete.challang.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.complete.challang.common.exception.SuccessCode;
import org.complete.challang.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.review.controller.dto.response.ReviewDetailResponse;
import org.complete.challang.review.controller.dto.response.ReviewListFindResponse;
import org.complete.challang.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<ReviewCreateResponse> createReview(@AuthenticationPrincipal final UserDetails user,
                                                             @RequestBody @Valid final ReviewCreateRequest reviewCreateRequest) {
        final Long userId = Long.parseLong(user.getUsername());
        final ReviewCreateResponse reviewCreateResponse = reviewService.createReview(reviewCreateRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewCreateResponse);
    }

    @GetMapping("?page={page_index}&sort={sort}")
    public ResponseEntity<ReviewListFindResponse> findReviewList(@PathVariable("page_index") final int page,
                                                                 @PathVariable("sort") final String sort) {
        final ReviewListFindResponse reviewListFindResponse = reviewService.findReviewList(page, sort);

        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewListFindResponse);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDetailResponse> findReviewDetail(@PathVariable("review_id") final Long reviewId) {
        final ReviewDetailResponse reviewDetailResponse = reviewService.findReviewDetail(reviewId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewDetailResponse);
    }

    @DeleteMapping("/{review_id}")
    public ResponseEntity<SuccessCode> deleteReview(@AuthenticationPrincipal final UserDetails user,
                                                    @PathVariable("review_id") final Long reviewId) {
        final Long userId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = reviewService.deleteReview(userId, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(successCode);
    }
}
