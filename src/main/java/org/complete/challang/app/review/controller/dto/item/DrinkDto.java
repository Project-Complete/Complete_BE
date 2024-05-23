package org.complete.challang.app.review.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.drink.domain.entity.Drink;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DrinkDto {

    private Long id;
    private String name;

    public static DrinkDto toDto(final Drink drink) {
        return DrinkDto.builder()
                .id(drink.getId())
                .name(drink.getName())
                .build();
    }
}
