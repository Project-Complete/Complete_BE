package org.complete.challang.app.combination.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationBoardListFindResponse {

    private Long combinationBoardId;
    private String imageUrl;
    private String title;
    private String nickname;
    private boolean combinationLike;
    private boolean combinationBookmark;

    public CombinationBoardListFindResponse(Long combinationBoardId,
                                            String imageUrl,
                                            String title,
                                            String nickname) {
        this.combinationBoardId = combinationBoardId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.nickname = nickname;
    }
}
