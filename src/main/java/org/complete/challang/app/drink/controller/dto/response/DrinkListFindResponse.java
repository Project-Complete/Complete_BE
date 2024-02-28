package org.complete.challang.app.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.drink.domain.entity.Drink;

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

    public static DrinkListFindResponse toDto(Drink drink,
                                              Long userId) {
        return DrinkListFindResponse.builder()
                .drinkId(drink.getId())
                .imageUrl(drink.getImageUrl())
                .manufacturerName(drink.getDrinkManufacturer().getManufacturerName())
                .drinkLike(drink.getDrinkLikes().stream()
                        .anyMatch(drinkLike -> drinkLike.getUser().getId().equals(userId)))
                .drinkName(drink.getName())
                .reviewRating(drink.getReviewSumRating() / drink.getReviewCount())
                .build();
    }
}
