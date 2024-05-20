package org.complete.challang.app.combination.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.combination.controller.dto.item.CombinationFindDto;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationBoardFindResponse {

    private Long combinationBoardId;
    private String combinationImageUrl;
    private String title;
    private String nickname;
    private String profileImageUrl;
    private LocalDateTime createdDate;
    private String description;
    private boolean combinationLike;
    private int combinationLikeCount;
    private boolean combinationBookmark;
    private int combinationBookmarkCount;
    private String content;
    private List<CombinationFindDto> combinations;

    public static CombinationBoardFindResponse toDto(final CombinationBoard combinationBoard,
                                                     final Long userId) {
        return CombinationBoardFindResponse.builder()
                .combinationBoardId(combinationBoard.getId())
                .combinationImageUrl(combinationBoard.getImageUrl())
                .title(combinationBoard.getTitle())
                .nickname(combinationBoard.getUser().getNickname())
                .profileImageUrl(combinationBoard.getUser().getProfileImageUrl())
                .createdDate(combinationBoard.getCreatedDate())
                .description(combinationBoard.getDescription())
                .content(combinationBoard.getContent())
                .combinations(
                        combinationBoard.getCombinations()
                                .stream()
                                .map(combination -> CombinationFindDto.toDto(combination, userId))
                                .toList()
                )
                .build();
    }
}
