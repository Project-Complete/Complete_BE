package org.complete.challang.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.drink.domain.entity.Drink;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
public class DrinkBannerListFindResponse {

    private Long drinkId;

    private String name;

    private String imageUrl;

    private ManufacturerFindResponse manufacturer;

    private String description;

    private List<FoodStatisticFindResponse> foodStatistics;

    private double abv;
}
