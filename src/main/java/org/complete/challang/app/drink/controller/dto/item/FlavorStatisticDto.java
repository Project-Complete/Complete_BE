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
public class FlavorStatisticDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -2627406002565044983L;

    private Long flavorId;
    private String flavor;
    private long count;
}
