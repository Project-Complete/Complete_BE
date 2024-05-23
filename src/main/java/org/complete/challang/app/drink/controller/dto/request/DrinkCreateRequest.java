package org.complete.challang.app.drink.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "주류 이름은 공백이 아니어야 합니다")
    private String name;
    private String summary;
    private String description;
    private double abv;
    private String imageUrl;
    private String title;

    @Valid
    private DrinkTypeDto drinkType;

    @Valid
    private ManufacturerDto manufacturer;

    @Valid
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
