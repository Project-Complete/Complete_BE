package org.complete.challang.app.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.drink.controller.dto.item.FoodStatisticDto;
import org.complete.challang.app.drink.controller.dto.item.ManufacturerDto;

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

    private ManufacturerDto manufacturer;

    private String description;

    private List<FoodStatisticDto> foodStatistics;

    private double abv;
}
