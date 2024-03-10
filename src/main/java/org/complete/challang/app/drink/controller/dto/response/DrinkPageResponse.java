package org.complete.challang.app.drink.controller.dto.response;

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
public class DrinkPageResponse<T> {

    private List<T> drinks;
    private PageInfoDto pageInfo;

    public static <T, U> DrinkPageResponse<T> toDto(final List<T> contents,
                                                    final Page<U> page,
                                                    final String sort) {
        return DrinkPageResponse.<T>builder()
                .drinks(contents)
                .pageInfo(PageInfoDto.toDto(page.getNumber() + 1, page.getSize(), page.getTotalElements(), sort))
                .build();
    }
}
