package org.complete.challang.drink.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodStatisticFindResponse {

    private Long foodId;

    private String category;

    private String imageUrl;

    private long count;
}
