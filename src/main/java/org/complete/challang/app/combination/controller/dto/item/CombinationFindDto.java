package org.complete.challang.app.combination.controller.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.combination.domain.entity.Combination;

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

    @JsonProperty("xcoordinate")
    private int xCoordinate;

    @JsonProperty("ycoordinate")
    private int yCoordinate;

    public static CombinationFindDto toDto(Combination combination,
                                           Long userId) {
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
