package org.complete.challang.app.review.controller.dto.item;

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
public class ReviewDto {

    private Long id;
    private String imageUrl;
    private double reviewRating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private boolean reviewLike;
    private WriterDto writer;
    private DrinkDto drink;

    public static ReviewDto toDto(final Review review,
                                  final boolean reviewLike) {
        return ReviewDto.builder()
                .id(review.getId())
                .imageUrl(review.getImageUrl())
                .reviewRating(review.getRating())
                .createdDate(review.getCreatedDate())
                .reviewLike(reviewLike)
                .writer(WriterDto.toDto(review.getUser()))
                .drink(DrinkDto.toDto(review.getDrink()))
                .build();
    }
}
