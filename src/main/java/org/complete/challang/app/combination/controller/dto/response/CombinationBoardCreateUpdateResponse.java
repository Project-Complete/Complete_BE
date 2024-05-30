package org.complete.challang.app.combination.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationBoardCreateUpdateResponse {

    private Long combinationBoardId;
    private String title;
    private String content;
    private String imageUrl;
    private String description;

    public static CombinationBoardCreateUpdateResponse toDto(CombinationBoard combinationBoard) {
        return CombinationBoardCreateUpdateResponse.builder()
                .combinationBoardId(combinationBoard.getId())
                .title(combinationBoard.getTitle())
                .content(combinationBoard.getContent())
                .imageUrl(combinationBoard.getImageUrl())
                .description(combinationBoard.getDescription())
                .build();
    }
}
