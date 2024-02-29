package org.complete.challang.app.drink.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.drink.controller.dto.item.DrinkTypeDto;
import org.complete.challang.app.drink.controller.dto.item.ManufacturerDto;
import org.complete.challang.app.drink.controller.dto.item.PackageDto;
import org.complete.challang.app.drink.domain.entity.Drink;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DrinkCreateRequest {

    private String name;
    private String summary;
    private String description;
    private double abv;
    private String imageUrl;
    private String title;
    private DrinkTypeDto drinkType;
    private ManufacturerDto manufacturer;
    private List<PackageDto> packages;

    public Drink toEntity() {
        return Drink.builder()
                .name(name)
                .summary(summary)
                .description(description)
                .abv(abv)
                .imageUrl(imageUrl)
                .title(title)
                .build();
    }
}
