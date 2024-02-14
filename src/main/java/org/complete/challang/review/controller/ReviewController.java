package org.complete.challang.review.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.complete.challang.review.controller.dto.request.ReviewCreateRequest;
import org.complete.challang.review.controller.dto.response.ReviewCreateResponse;
import org.complete.challang.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
