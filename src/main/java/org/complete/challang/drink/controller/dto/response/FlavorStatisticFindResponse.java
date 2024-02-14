package org.complete.challang.drink.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlavorStatisticFindResponse {

    private Long flavorId;

    private String flavor;

    private long count;
}
