package org.complete.challang.app.combination.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;
import org.complete.challang.app.combination.domain.entity.CombinationComment;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationCommentCreateRequest {

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
