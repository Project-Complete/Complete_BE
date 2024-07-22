package org.complete.challang.app.combination.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;
import org.complete.challang.app.combination.domain.entity.CombinationComment;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationCommentCreateRequest {

    @Schema(description = "최상위 댓글은 'null'을 입력해야 합니다", example = "null")
    private Long parentCombinationCommentId;
    private String content;

    public CombinationComment toEntity(final CombinationBoard combinationBoard,
                                       final User user) {
        return CombinationComment.builder()
                .content(this.content)
                .combinationBoard(combinationBoard)
                .user(user)
                .build();
    }
}
