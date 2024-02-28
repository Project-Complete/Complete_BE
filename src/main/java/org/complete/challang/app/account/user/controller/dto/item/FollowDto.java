package org.complete.challang.app.account.user.controller.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.account.user.domain.entity.User;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FollowDto {

    private Long id;
    private String nickname;
    private String profileImageUrl;
    private boolean isFollow;

    public static FollowDto toDto(final User user,
                                  final boolean isFollow) {
        return FollowDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .isFollow(isFollow)
                .build();
    }
}
