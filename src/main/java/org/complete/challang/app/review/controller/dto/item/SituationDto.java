package org.complete.challang.app.review.controller.dto.item;

import lombok.*;
import org.complete.challang.app.review.domain.entity.Situation;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SituationDto {

    @Builder.Default
    private boolean adult = false;

    @Builder.Default
    private boolean alone = false;

    @Builder.Default
    private boolean business = false;

    @Builder.Default
    private boolean friend = false;

    @Builder.Default
    private boolean partner = false;

    public Situation toEntity() {
        return Situation.builder()
                .adult(adult)
                .alone(alone)
                .business(business)
                .friend(friend)
                .partner(partner)
                .build();
    }

    public static SituationDto toDto(Situation situation) {
        return SituationDto.builder()
                .adult(situation.isAdult())
                .alone(situation.isAlone())
                .business(situation.isBusiness())
                .friend(situation.isFriend())
                .partner(situation.isPartner())
                .build();
    }
}
