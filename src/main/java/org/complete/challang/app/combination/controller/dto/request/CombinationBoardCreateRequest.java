package org.complete.challang.app.combination.controller.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.combination.controller.dto.item.CombinationCreateDto;
import org.complete.challang.app.combination.domain.entity.CombinationBoard;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationBoardCreateRequest {

    private String imageUrl;
    private String title;
    private String description;
    private String content;
    private List<CombinationCreateDto> combinations;

    public CombinationBoard toEntity(User user) {
        return CombinationBoard.builder()
                .title(this.title)
                .content(this.title)
                .imageUrl(this.imageUrl)
                .description(this.description)
                .user(user)
                .build();
    }
}
