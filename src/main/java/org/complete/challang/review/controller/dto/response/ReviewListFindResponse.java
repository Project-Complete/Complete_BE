package org.complete.challang.review.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.complete.challang.common.dto.PageInfoDto;
import org.complete.challang.review.controller.dto.item.ReviewDto;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewListFindResponse {

    private List<ReviewDto> reviews;
    private PageInfoDto pageInfo;

    public static ReviewListFindResponse toDto(final List<ReviewDto> reviews,
                                               PageInfoDto pageInfo) {
        return ReviewListFindResponse.builder()
                .reviews(reviews)
                .pageInfo(pageInfo)
                .build();
    }
}