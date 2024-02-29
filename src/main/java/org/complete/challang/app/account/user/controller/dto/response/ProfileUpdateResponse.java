package org.complete.challang.app.account.user.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.account.user.domain.entity.User;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfileUpdateResponse {

    private String profileImageUrl;
    private String nickname;
    private String email;

    public static ProfileUpdateResponse toDto(final User user) {
        return ProfileUpdateResponse.builder()
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }
}
