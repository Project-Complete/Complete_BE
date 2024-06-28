package org.complete.challang.app.combination.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.combination.domain.entity.CombinationComment;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationCommentResponse {

    private Long combinationCommentId;
    private Long userId;
    private String profileImageUrl;
    private String nickname;
    private String content;
    private long replyCount;
    private LocalDateTime createdDate;

    public static CombinationCommentResponse toDto(CombinationComment combinationComment) {
        return CombinationCommentResponse.builder()
                .combinationCommentId(combinationComment.getId())
                .userId(combinationComment.getUser() != null ? combinationComment.getUser().getId() : null)
                .profileImageUrl(combinationComment.getUser().getProfileImageUrl())
                .nickname(combinationComment.getUser() != null ? combinationComment.getUser().getNickname() : null)
                .content(combinationComment.getContent())
                .replyCount(combinationComment.getChildren().size())
                .createdDate(combinationComment.getCreatedDate())
                .build();
    }
}
