package org.complete.challang.account.user.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.account.user.domain.entity.User;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileFindResponse {

    private Long userId;
    private String profileImageUrl;
    private String nickname;
    private String email;
    private Long followers;
    private Long followings;

    public static UserProfileFindResponse toDto(final User user,
                                                final String email) {
        return UserProfileFindResponse.builder()
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .email(email)
                .followers(user.getFollowers())
                .followings(user.getFollowings())
                .build();
    }
}
