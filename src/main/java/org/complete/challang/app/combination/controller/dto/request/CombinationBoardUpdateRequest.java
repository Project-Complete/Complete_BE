package org.complete.challang.app.combination.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.complete.challang.app.combination.controller.dto.item.CombinationCreateUpdateDto;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationBoardUpdateRequest {

    private String imageUrl;
    private String title;
    private String description;
    private String content;
    private List<CombinationCreateUpdateDto> combinations;
}
