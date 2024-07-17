package org.complete.challang.app.drink.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FoodStatisticDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3358434131357359824L;

    private Long foodId;
    private String category;
    private String imageUrl;
    private long count;
}
