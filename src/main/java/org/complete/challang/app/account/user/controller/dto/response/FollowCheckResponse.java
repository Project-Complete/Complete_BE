package org.complete.challang.app.account.user.controller.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FollowCheckResponse {

    private boolean isFollow;

    public static FollowCheckResponse toDto(boolean isFollow) {
        return FollowCheckResponse.builder()
                .isFollow(isFollow)
                .build();
    }
}
