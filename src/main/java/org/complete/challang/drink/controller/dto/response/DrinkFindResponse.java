package org.complete.challang.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.drink.domain.entity.SituationStatistic;

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

    private ManufacturerFindResponse manufacturer;

    private double reviewRating;

    private List<TagFindResponse> tags;

    private long reviewCount;

    private List<FoodStatisticFindResponse> foodStatistics;

    private TasteAverageStatistic tasteStatistic;

    private SituationStatistic situationStatistic;

    private List<FlavorStatisticFindResponse> flavorStatistics;

    private String title;

    private String description;

    private List<PackageFindResponse> packages;

    private double abv;

    private DrinkTypeFindResponse type;

    public void updateStatistic(List<FoodStatisticFindResponse> foodStatisticFindResponses,
                                List<FlavorStatisticFindResponse> flavorStatisticFindResponses) {
        foodStatistics = foodStatisticFindResponses;
        flavorStatistics = flavorStatisticFindResponses;
    }

    public void updateDrinkLike(boolean likeStatus) {
        drinkLike = likeStatus;
    }
}
