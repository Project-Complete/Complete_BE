package org.complete.challang.app.drink.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DrinkTypeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -2824491512685342748L;

    @NotBlank(message = "주류 타입은 공백이 아니어야 합니다")
    private String type;

    @NotBlank(message = "주류 상세 타입은 공백이 아니어야 합니다")
    private String detailType;
}
