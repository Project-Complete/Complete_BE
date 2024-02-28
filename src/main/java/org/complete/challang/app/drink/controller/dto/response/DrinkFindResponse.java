package org.complete.challang.app.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.drink.controller.dto.item.*;
import org.complete.challang.app.drink.domain.entity.SituationStatistic;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
public class DrinkFindResponse {

    private Long drinkId;

    private String name;

    private String imageUrl;

    private String summary;

    private boolean drinkLike;

    private ManufacturerDto manufacturer;

    private double reviewRating;

    private List<TagDto> tags;

    private long reviewCount;

    private List<FoodStatisticDto> foodStatistics;

    private TasteAverageStatisticDto tasteStatistic;

    private SituationStatistic situationStatistic;

    private List<FlavorStatisticDto> flavorStatistics;

    private String title;

    private String description;

    private List<PackageDto> packages;

    private double abv;

    private DrinkTypeDto type;

    public void updateStatistic(List<FoodStatisticDto> foodStatisticFindRespons,
                                List<FlavorStatisticDto> flavorStatisticFindRespons) {
        foodStatistics = foodStatisticFindRespons;
        flavorStatistics = flavorStatisticFindRespons;
    }

    public void updateDrinkLike(boolean likeStatus) {
        drinkLike = likeStatus;
    }
}
