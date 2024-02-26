package org.complete.challang.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.drink.domain.entity.Drink;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
public class DrinkListFindResponse {

    private Long drinkId;

    private String imageUrl;

    private String manufacturerName;

    private boolean drinkLike;

    private String drinkName;

    private double reviewRating;

    // private boolean drniklike;

    public static DrinkListFindResponse toDto(Drink drink){
        return DrinkListFindResponse.builder()
                .drinkId(drink.getId())
                .imageUrl(drink.getImageUrl())
                .manufacturerName(drink.getDrinkManufacturer().getManufacturerName())
                .drinkName(drink.getName())
                .reviewRating(drink.getReviewSumRating()/drink.getReviewCount())
                .build();
    }
}
