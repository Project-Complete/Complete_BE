package org.complete.challang.app.review.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.review.controller.dto.item.SituationDto;
import org.complete.challang.app.review.controller.dto.item.TasteDto;
import org.complete.challang.app.review.controller.dto.item.WriterDto;
import org.complete.challang.app.review.domain.entity.Review;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewDetailResponse {

    private String content;
    private String imageUrl;
    private float rating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private boolean reviewLike;
    private WriterDto writerDto;
    private SituationDto situation;
    private TasteDto taste;
    private List<String> flavors;
    private List<String> foods;

    public static ReviewDetailResponse toDto(final Review review,
                                             final boolean reviewLike,
                                             final List<String> flavors,
                                             final List<String> foods) {
        return ReviewDetailResponse.builder()
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .rating(review.getRating())
                .createdDate(review.getCreatedDate())
                .reviewLike(reviewLike)
                .writerDto(WriterDto.toDto(review.getUser()))
                .situation(SituationDto.toDto(review.getSituation()))
                .taste(TasteDto.toDto(review.getTaste()))
                .flavors(flavors)
                .foods(foods)
                .build();
    }
}
