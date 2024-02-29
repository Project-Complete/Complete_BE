package org.complete.challang.app.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.drink.domain.entity.Drink;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DrinkCreateResponse {

    private Long drinkId;
    private String name;
    private String summary;
    private String description;
    private double abv;
    private String imageUrl;
    private String title;

    public static DrinkCreateResponse toDto(final Drink drink) {
        return DrinkCreateResponse.builder()
                .drinkId(drink.getId())
                .name(drink.getName())
                .summary(drink.getSummary())
                .description(drink.getDescription())
                .abv(drink.getAbv())
                .imageUrl(drink.getImageUrl())
                .title(drink.getTitle())
                .build();
    }
}
