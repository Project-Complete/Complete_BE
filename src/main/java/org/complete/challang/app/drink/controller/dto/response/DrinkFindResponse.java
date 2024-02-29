package org.complete.challang.app.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.drink.controller.dto.item.*;
import org.complete.challang.app.drink.domain.entity.Drink;
import org.complete.challang.app.drink.domain.entity.SituationStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DrinkFindResponse {

    private Long drinkId;
    private String name;
    private String imageUrl;
    private String summary;
    private boolean drinkLike;
    private ManufacturerDto manufacturer;
    private double reviewRating;
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

    public static DrinkFindResponse toDto(final Drink drink) {
        return DrinkFindResponse.builder()
                .drinkId(drink.getId())
                .name(drink.getName())
                .imageUrl(drink.getImageUrl())
                .summary(drink.getSummary())
                .manufacturer(ManufacturerDto.builder()
                        .drinkManufacturerId(drink.getDrinkManufacturer().getId())
                        .manufacturerName(drink.getDrinkManufacturer().getManufacturerName())
                        .location(drink.getDrinkManufacturer().getLocation().getLocation())
                        .build())
                .reviewRating(drink.getReviewSumRating() / drink.getReviewCount())
                .reviewCount(drink.getReviewCount())
                .tasteStatistic(TasteAverageStatisticDto.builder()
                        .sweetRating(drink.getTasteStatistic().getSweetSumRating() / drink.getReviewCount())
                        .sourRating(drink.getTasteStatistic().getSourSumRating() / drink.getReviewCount())
                        .bitterRating(drink.getTasteStatistic().getBitterSumRating() / drink.getReviewCount())
                        .bodyRating(drink.getTasteStatistic().getBodySumRating() / drink.getReviewCount())
                        .refreshRating(drink.getTasteStatistic().getRefreshSumRating() / drink.getReviewCount())
                        .build())
                .situationStatistic(drink.getSituationStatistic())
                .title(drink.getTitle())
                .description(drink.getDescription())
                .packages(drink.getDrinkPackages().stream()
                        .map(drinkPackage ->
                                PackageDto.builder()
                                        .type(drinkPackage.getPackages().getType())
                                        .volume(drinkPackage.getPackages().getVolume())
                                        .build())
                        .collect(Collectors.toList()))
                .abv(drink.getAbv())
                .type(DrinkTypeDto.builder()
                        .type(drink.getDrinkDetailType().getDrinkType().getDrinkType())
                        .detailType(drink.getDrinkDetailType().getDetailType())
                        .build())
                .build();
    }

    public void updateStatistic(final List<FoodStatisticDto> foodStatisticFindRespons,
                                final List<FlavorStatisticDto> flavorStatisticFindRespons) {
        foodStatistics = foodStatisticFindRespons;
        flavorStatistics = flavorStatisticFindRespons;
    }

    public void updateDrinkLike(final boolean likeStatus) {
        drinkLike = likeStatus;
    }
}
