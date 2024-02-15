package org.complete.challang.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FoodStatisticFindResponse {

    private Long foodId;

    private String category;

    private String imageUrl;

    private long count;
}
