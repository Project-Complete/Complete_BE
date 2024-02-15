package org.complete.challang.review.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.complete.challang.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.review.controller.dto.response.ReviewDetailResponse;
import org.complete.challang.review.controller.dto.response.ReviewListFindResponse;
import org.complete.challang.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<ReviewCreateResponse> createReview(@RequestBody @Valid final ReviewCreateRequest reviewCreateRequest) {
        // 사용자 정보 필요
        final ReviewCreateResponse reviewCreateResponse = reviewService.createReview(reviewCreateRequest);

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
}
