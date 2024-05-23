package org.complete.challang.app.drink.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PackageDto {

    @NotBlank(message = "포장 타입은 공백이 아니어야 합니다")
    private String type;

    @NotBlank(message = "포장 용량은 공백이 아니어야 합니다")
    private String volume;
}
