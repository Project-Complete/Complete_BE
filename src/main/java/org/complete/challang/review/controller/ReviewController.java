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

    @GetMapping()
    public ResponseEntity<ReviewListFindResponse> findReviewList(@RequestParam(required = false, name = "drink-id") final Long drinkId,
                                                                 @RequestParam(required = false, name = "writer-id") final Long writerId,
                                                                 @RequestParam(required = false, name = "page", defaultValue = "0") final int page,
                                                                 @RequestParam(required = false, name = "sort", defaultValue = "latest") final String sort) {
        final ReviewListFindResponse reviewListFindResponse = reviewService.findReviewList(drinkId, writerId, page, sort);

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

    @PostMapping("/like/{review_id}")
    public ResponseEntity<SuccessCode> createReviewLike(@AuthenticationPrincipal final UserDetails user,
                                                        @PathVariable("review_id") final Long reviewId) {
        final Long userId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = reviewService.createReviewLike(userId, reviewId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(successCode);
    }

    @DeleteMapping("/like/{review_id}")
    public ResponseEntity<SuccessCode> deleteReviewLike(@AuthenticationPrincipal final UserDetails user,
                                                        @PathVariable("review_id") final Long reviewId) {
        final Long userId = Long.parseLong(user.getUsername());
        final SuccessCode successCode = reviewService.deleteReviewLike(userId, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(successCode);
    }
}
