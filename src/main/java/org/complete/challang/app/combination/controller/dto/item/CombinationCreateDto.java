package org.complete.challang.app.combination.controller.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.complete.challang.app.combination.domain.entity.Combination;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;
import org.complete.challang.app.drink.domain.entity.Drink;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationCreateDto {

    private Long drinkId;
    private String name;
    private String volume;

    @JsonProperty("xcoordinate")
    private int xCoordinate;

    @JsonProperty("ycoordinate")
    private int yCoordinate;

    public Combination toEntity(CombinationBoard combinationBoard,
                                Drink drink) {
        return Combination.builder()
                .name(drink == null ? this.name : drink.getName())
                .volume(this.volume)
                .xCoordinate(this.xCoordinate)
                .yCoordinate(this.yCoordinate)
                .combinationBoard(combinationBoard)
                .drink(drink)
                .build();
    }
}
