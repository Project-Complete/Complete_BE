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
    private String combinationImageUrl;
    private String title;
    private String profileImageUrl;
    private String nickname;
    private boolean combinationLike;
    private boolean combinationBookmark;

    public CombinationBoardListFindResponse(Long combinationBoardId,
                                            String combinationImageUrl,
                                            String title,
                                            String profileImageUrl,
                                            String nickname) {
        this.combinationBoardId = combinationBoardId;
        this.combinationImageUrl = combinationImageUrl;
        this.title = title;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }
}
