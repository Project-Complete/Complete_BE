package org.complete.challang.drink.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DrinkTypeFindResponse {

    private String type;

    private String detailType;
}
