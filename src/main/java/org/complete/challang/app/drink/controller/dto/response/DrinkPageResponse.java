package org.complete.challang.app.drink.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.complete.challang.app.common.dto.PageInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
public class DrinkPageResponse<T> {

    private List<T> drinks;
    private PageInfoDto pageInfo;

    public static DrinkPageResponse toDto(List contents,
                                          Page page,
                                          String sort) {
        return DrinkPageResponse.builder()
                .drinks(contents)
                .pageInfo(PageInfoDto.toDto(page.getNumber() + 1, page.getSize(), page.getTotalElements(), sort))
                .build();
    }
}
