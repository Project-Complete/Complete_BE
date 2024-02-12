package org.complete.challang.drink.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlavorStatisticFindResponse {

    private Long flavorId;

    private String flavor;

    private long count;
}
