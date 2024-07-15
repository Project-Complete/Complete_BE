package org.complete.challang.app.combination.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.common.dto.PageInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CombinationCommentPageResponse<T> {

    private List<T> combinationComments;
    private PageInfoDto pageInfo;

    public static <T, U> CombinationCommentPageResponse<T> toDto(final List<T> contents,
                                                                 final Page<U> page,
                                                                 final String sort) {
        return CombinationCommentPageResponse.<T>builder()
                .combinationComments(contents)
                .pageInfo(
                        PageInfoDto.toDto(
                                page.getNumber() + 1,
                                page.getSize(),
                                page.getTotalElements(),
                                page.getTotalElements() / page.getSize() + 1,
                                sort
                        )
                )
                .build();
    }
}
