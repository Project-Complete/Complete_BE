package org.complete.challang.app.combination.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.combination.controller.dto.item.CombinationFindDto;
import org.complete.challang.app.combination.controller.dto.item.EtcCombinationFindDto;
import org.complete.challang.app.combination.domain.entity.Combination;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Builder.Default
    private List<CombinationFindDto> combinations = new ArrayList<>();

    @Builder.Default
    private List<EtcCombinationFindDto> etcCombinations = new ArrayList<>();

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
                .combinationLike(
                        combinationBoard.getCombinationBoardLikes()
                                .stream()
                                .anyMatch(combinationBoardLike -> combinationBoardLike.getUser().getId().equals(userId))
                )
                .combinationLikeCount(combinationBoard.getCombinationBoardLikes().size())
                .combinationBookmark(
                        combinationBoard.getCombinationBoardBookmarks()
                                .stream()
                                .anyMatch(combinationBoardBookmark -> combinationBoardBookmark.getUser().getId().equals(userId))
                )
                .combinationBookmarkCount(combinationBoard.getCombinationBoardBookmarks().size())
                .build();
    }

    public void updateCombinations(final Set<Combination> combinations,
                                   final Long userId) {
        combinations.forEach(combination -> {
            if (combination.getDrink() == null) {
                this.etcCombinations.add(EtcCombinationFindDto.toDto(combination));
            } else {
                this.combinations.add(CombinationFindDto.toDto(combination, userId));
            }
        });
    }
}
