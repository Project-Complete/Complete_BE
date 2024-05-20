package org.complete.challang.app.combination.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.combination.domain.entity.Combination;
import org.complete.challang.app.drink.domain.entity.Drink;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationFindDto {

    private Long drinkId;
    private String imageUrl;
    private String manufacturerName;
    private boolean drinkLike;
    private String name;
    private String volume;
    private int xCoordinate;
    private int yCoordinate;

    public static CombinationFindDto toDto(Combination combination,
                                           Long userId) {
        Drink drink = combination.getDrink();

        if (drink == null) {
            return CombinationFindDto.builder()
                    .name(drink != null ? drink.getName() : combination.getName())
                    .volume(combination.getVolume())
                    .xCoordinate(combination.getXCoordinate())
                    .yCoordinate(combination.getYCoordinate())
                    .build();
        }

        return CombinationFindDto.builder()
                .drinkId(combination.getDrink().getId())
                .imageUrl(combination.getDrink().getImageUrl())
                .manufacturerName(
                        combination.getDrink()
                                .getDrinkManufacturer()
                                .getManufacturerName()
                )
                .drinkLike(
                        combination.getDrink().getDrinkLikes()
                                .stream()
                                .anyMatch(dl -> dl.getUser().getId().equals(userId))
                )
                .name(combination.getName())
                .volume(combination.getVolume())
                .xCoordinate(combination.getXCoordinate())
                .yCoordinate(combination.getYCoordinate())
                .build();
    }
}
