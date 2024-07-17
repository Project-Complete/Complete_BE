package org.complete.challang.app.drink.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PackageDto implements Serializable {

    @NotBlank(message = "포장 타입은 공백이 아니어야 합니다")
    private String type;

    @NotBlank(message = "포장 용량은 공백이 아니어야 합니다")
    private String volume;
}
