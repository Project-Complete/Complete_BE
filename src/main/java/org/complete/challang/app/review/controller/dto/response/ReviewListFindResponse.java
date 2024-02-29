package org.complete.challang.app.review.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.review.controller.dto.item.ReviewDto;
import org.complete.challang.app.common.dto.PageInfoDto;

import java.util.List;

@Getter
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
