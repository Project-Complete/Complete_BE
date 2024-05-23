package org.complete.challang.app.drink.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ManufacturerDto {

    private Long drinkManufacturerId;

    @NotBlank(message = "주류 제조사 이름은 공백이 아니어야 합니다")
    private String manufacturerName;

    @NotBlank(message = "주류 제조사 지역은 공백이 아니어야 합니다")
    private String location;
}
