package org.complete.challang.review.controller.dto.item;

import lombok.Builder;
import lombok.Getter;
import org.complete.challang.review.domain.entity.Taste;

@Getter
@Builder
public class TasteDto {

    @Builder.Default
    private float sweet = 0;

    @Builder.Default
    private float sour = 0;

    @Builder.Default
    private float bitter = 0;

    @Builder.Default
    private float body = 0;

    @Builder.Default
    private float refresh = 0;

    public Taste toEntity() {
        return Taste.builder()
                .sweet(sweet)
                .sour(sour)
                .bitter(bitter)
                .body(body)
                .refresh(refresh)
                .build();
    }

    public static TasteDto toDto(Taste taste) {
        return TasteDto.builder()
                .sweet(taste.getSweet())
                .sour(taste.getSour())
                .bitter(taste.getBitter())
                .body(taste.getBody())
                .refresh(taste.getRefresh())
                .build();
    }
}
