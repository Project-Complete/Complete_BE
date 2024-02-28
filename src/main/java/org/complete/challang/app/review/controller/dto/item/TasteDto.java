package org.complete.challang.app.review.controller.dto.item;

import lombok.*;
import org.complete.challang.app.review.domain.entity.Taste;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
