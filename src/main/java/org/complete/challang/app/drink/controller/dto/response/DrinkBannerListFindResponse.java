package org.complete.challang.app.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.drink.controller.dto.item.FoodStatisticDto;
import org.complete.challang.app.drink.controller.dto.item.ManufacturerDto;
import org.complete.challang.app.drink.domain.entity.SituationStatistic;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DrinkBannerListFindResponse {

    private Long drinkId;
    private String name;
    private String imageUrl;
    private double reviewRating;
    private ManufacturerDto manufacturer;
    private String description;
    private List<FoodStatisticDto> foodStatistics;
    private SituationStatistic situationStatistics;
    private double abv;
}
