package org.complete.challang.drink.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PackageFindResponse {

    private String type;

    private String volume;
}