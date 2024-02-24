package org.complete.challang.account.user.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.complete.challang.account.user.controller.dto.common.FollowDto;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FollowsFindResponse {

    private List<FollowDto> followers;
    private List<FollowDto> followings;

    public static FollowsFindResponse toDto(final List<FollowDto> followers,
                                            final List<FollowDto> followings) {
        return FollowsFindResponse.builder()
                .followers(followers)
                .followings(followings)
                .build();
    }
}
