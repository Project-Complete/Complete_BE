package org.complete.challang.drink.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManufacturerFindResponse {

    private Long drinkManufacturerId;

    private String manufacturerName;

    private String location;
}
