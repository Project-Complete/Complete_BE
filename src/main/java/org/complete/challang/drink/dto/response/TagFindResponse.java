package org.complete.challang.drink.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagFindResponse {

    private Long tagId;

    private String tag;
}
