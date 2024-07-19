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
public class TasteAverageStatisticDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5001464916124860027L;

    private double sweetRating;
    private double sourRating;
    private double bitterRating;
    private double bodyRating;
    private double refreshRating;
}
