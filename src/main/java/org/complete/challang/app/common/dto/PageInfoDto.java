package org.complete.challang.app.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageInfoDto {

    private int page;
    private int size;
    private long totalElements;
    private long totalPages;
    private String sort;

    public static PageInfoDto toDto(int page,
                                    int size,
                                    long totalElements,
                                    String sort) {
        return PageInfoDto.builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .sort(sort)
                .build();
    }

    public static PageInfoDto toDto(int page,
                                    int size,
                                    long totalElements,
                                    long totalPages,
                                    String sort) {
        return PageInfoDto.builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .sort(sort)
                .build();
    }
}
