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
    private WriterDto writer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public static ReviewDto toDto(final Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .imageUrl(review.getImageUrl())
                .reviewRating(review.getRating())
                .writer(WriterDto.toDto(review.getUser()))
                .createdDate(review.getCreatedDate())
                .build();
    }
}
