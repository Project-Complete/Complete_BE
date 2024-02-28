package org.complete.challang.app.review.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.review.domain.entity.Review;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewCreateResponse {

    private Long reviewId;
    private String imageUrl;
    private String content;
    private float rating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reviewCreatedDate;

    public static ReviewCreateResponse toDto(final Review review) {
        return ReviewCreateResponse.builder()
                .reviewId(review.getId())
                .imageUrl(review.getImageUrl())
                .content(review.getContent())
                .rating(review.getRating())
                .reviewCreatedDate(review.getCreatedDate())
                .build();
    }
}
