package org.complete.challang.review.controller.dto.item;

import lombok.Builder;
import lombok.Getter;
import org.complete.challang.account.user.domain.entity.User;

@Getter
@Builder
public class WriterDto {

    private Long id;
    private String nickname;

    public static WriterDto toDto(final User writer) {
        return WriterDto.builder()
                .id(writer.getId())
                .nickname(writer.getNickname())
                .build();
    }
}
