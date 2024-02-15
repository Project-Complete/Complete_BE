package org.complete.challang.review.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.complete.challang.review.controller.dto.item.SituationDto;
import org.complete.challang.review.controller.dto.item.TasteDto;
import org.complete.challang.review.domain.entity.Review;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewDetailResponse {

    private String content;
    private String imageUrl;
    private float rating;
    private SituationDto situation;
    private TasteDto taste;
    private List<String> flavors;
    private List<String> foods;

    public static ReviewDetailResponse toEntity(final Review review,
                                                final SituationDto situationDto,
                                                final TasteDto tasteDto,
                                                final List<String> flavors,
                                                final List<String> foods) {
        return ReviewDetailResponse.builder()
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .situation(situationDto)
                .taste(tasteDto)
                .flavors(flavors)
                .foods(foods)
                .build();
    }
}
